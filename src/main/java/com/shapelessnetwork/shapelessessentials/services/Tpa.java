package com.shapelessnetwork.shapelessessentials.services;

import com.shapelessnetwork.shapelessessentials.Config;
import com.shapelessnetwork.shapelessessentials.ShapelessEssentials;
import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import com.shapelessnetwork.shapelessessentials.exceptions.commands.tpa.TpaCooldownException;
import com.shapelessnetwork.shapelessessentials.exceptions.commands.tpa.TpaCooldownNoItemException;
import com.shapelessnetwork.shapelessessentials.utils.PermissionUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


public class Tpa extends Service {

    protected static final Map<UUID, List<Request>> requests = new HashMap<>();
    protected static final Map<UUID, LocalDateTime> lastUsages = new HashMap<>();

    public static class Request {
        protected UUID sender;
        protected UUID receiver;
        protected boolean tpaHere;
        protected UUID uuid;

        public UUID getSender() {
            return sender;
        }

        public UUID getReceiver() {
            return receiver;
        }

        public boolean isTpaHere() {
            return tpaHere;
        }

        public Request(UUID sender, UUID receiver) {
            this(sender, receiver, false);
        }

        public Request(UUID sender, UUID receiver, boolean tpaHere) {
            this.sender = sender;
            this.receiver = receiver;
            this.tpaHere = tpaHere;
            this.uuid = UUID.randomUUID();
            new CancelTpaTask(this).runTaskLater(ShapelessEssentials.getInstance(), Config.tpaTimeout * 20L);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Request request = (Request) o;
            return tpaHere == request.tpaHere && Objects.equals(sender, request.sender) && Objects.equals(receiver, request.receiver) && Objects.equals(uuid, request.uuid);
        }

        public void accepted() throws GeneralException {
            if (isTpaHere()) this.getReceiverPlayer().teleportAsync(getSenderPlayer().getLocation());
            else this.getSenderPlayer().teleportAsync(getReceiverPlayer().getLocation());
            getRequests(getReceiverPlayer()).remove(this);
            sendAcceptedMessages();
        }

        public void sendRequestMessages() throws GeneralException {
            Player sender = getSenderPlayer();
            Player receiver = getReceiverPlayer();
            if (isTpaHere()) {
                sender.sendMessage(Component.text("Sent Tpa Here request to ").color(NamedTextColor.GREEN).append(receiver.displayName()));
                receiver.sendMessage(Component.text("Received Tpa Here request from ").color(NamedTextColor.GOLD).append(sender.displayName()).append(Component.text(" /tpaccept").color(NamedTextColor.GRAY)));
            } else {
                sender.sendMessage(Component.text("Sent request to ").color(NamedTextColor.GREEN).append(receiver.displayName()));
                receiver.sendMessage(Component.text("Received Tpa request from ").color(NamedTextColor.GOLD).append(sender.displayName()));
            }
        }

        public void sendAcceptedMessages() throws GeneralException {
            Player sender = getSenderPlayer();
            Player receiver = getReceiverPlayer();
            receiver.sendMessage(Component.text("Accepted teleport request from ").color(NamedTextColor.GREEN).append(sender.displayName()));
            sender.sendMessage(sender.displayName().append(Component.text(" accepted your teleport request.").color(NamedTextColor.GREEN)));
            if (isTpaHere()) {
                receiver.sendMessage(Component.text("Teleporting to ").color(NamedTextColor.GOLD).append(sender.displayName()));
            } else {
                sender.sendMessage(Component.text("Teleporting to ").color(NamedTextColor.GOLD).append(receiver.displayName()));
            }
        }

        public void sendCancelledMessages() throws GeneralException {
            getSenderPlayer().sendMessage(Component.text("Teleport request to ").color(NamedTextColor.RED).append(getReceiverPlayer().displayName()).append(Component.text(" has been cancelled")));
            getReceiverPlayer().sendMessage(Component.text("Teleport request from ").color(NamedTextColor.RED).append(getSenderPlayer().displayName()).append(Component.text(" has been cancelled")));

        }

        public Player getSenderPlayer() throws GeneralException {
            return getOnlinePlayer(sender);
        }

        public Player getReceiverPlayer() throws GeneralException {
            return getOnlinePlayer(receiver);
        }
    }

    protected static class CancelTpaTask extends BukkitRunnable {
        protected final Request request;

        public CancelTpaTask(Request request) {
            this.request = request;
        }

        @Override
        public void run() {
            try {
                List<Request> requests = getRequests(request.getReceiverPlayer());
                if (requests.remove(request)) {
                    request.sendCancelledMessages();
                }
            } catch (GeneralException ignored) {
            }
        }
    }

    public static void sendTpaRequest(Player sender, String receiver) throws GeneralException {
        sendTpaRequest(sender, getOnlinePlayer(receiver, sender), false);
    }

    public static void sendTpaHereRequest(Player sender, String receiver) throws GeneralException {
        sendTpaRequest(sender, getOnlinePlayer(receiver, sender), true);
    }

    public static void sendTpaRequest(Player sender, Player receiver, boolean tpaHere) throws GeneralException {
        if (!getRequests(receiver, sender).isEmpty())
            throw new GeneralException("You have already sent a request to that player.");
        if (sender.getName().equals(receiver.getName()))
            throw new GeneralException("You can't tpa to yourself.");
        tpaCheck(sender);
        Request request = new Request(sender.getUniqueId(), receiver.getUniqueId(), tpaHere);
        request.sendRequestMessages();
        getRequests(receiver).add(request);
    }

    public static void acceptTpaRequest(Player player) throws GeneralException {
        List<Request> requests = getRequests(player);
        if (requests.isEmpty()) throw new GeneralException("You do not have any active tpa requests.");
        Request request = requests.get(requests.size() - 1);
        acceptTpaRequest(request);
    }

    public static void acceptTpaRequest(Player acceptor, String requester) throws GeneralException {
        Player requesterPlayer = getOnlinePlayer(requester);
        if (!getRequests(acceptor, requesterPlayer).isEmpty()) {
            acceptTpaRequest(acceptor, requesterPlayer);
            return;
        }
        acceptTpaRequest(acceptor, getOnlinePlayer(requester, acceptor));
    }

    public static void acceptTpaRequest(Player acceptor, Player requester) throws GeneralException {
        List<Request> requests = getRequests(acceptor, requester);
        if (requests.isEmpty()) throw new GeneralException("You do not have tpa requests from this player.");
        Request request = requests.get(requests.size() - 1);
        acceptTpaRequest(request);
    }

    public static void acceptTpaRequest(Request request) throws GeneralException {
        try {
            tpaCheck(request.getSenderPlayer());
        } catch (GeneralException e) {
            request.getSenderPlayer().sendMessage(e.getComponent());
            throw new GeneralException(Component.text("Tpa failed because ").color(NamedTextColor.RED)
                    .append(request.getSenderPlayer().displayName())
                    .append(Component.text("is on cooldown.").color(NamedTextColor.RED)));
        }
        if (onCooldown(request.getSenderPlayer())) request.getSenderPlayer().getInventory().removeItem(Config.tpaItem);
        else lastUsages.put(request.getSenderPlayer().getUniqueId(), LocalDateTime.now());
        request.accepted();
    }

    public static List<Request> getRequests(Player player, Player sender) {
        return getRequests(player).stream().filter(request -> request.getSender() == sender.getUniqueId()).collect(Collectors.toList());
    }

    public static List<Request> getRequests(Player player) {
        requests.putIfAbsent(player.getUniqueId(), new ArrayList<>());
        return requests.get(player.getUniqueId());
    }

    public static void tpaCheck(Player player) throws GeneralException {
        if (onCooldown(player)) {
            Duration duration = getRemainingDuration(player);
            if (Config.tpaItem != null) {
                if (player.getInventory().containsAtLeast(Config.tpaItem, Config.tpaItem.getAmount())) return;
                throw new TpaCooldownNoItemException(duration, Config.tpaItem);
            }
            throw new TpaCooldownException(duration);
        }
    }

    public static Duration getRemainingDuration(Player player) {
        return Duration.between(LocalDateTime.now(), lastUsages.get(player.getUniqueId()).plusSeconds(Config.tpaCooldown));
    }

    public static boolean onCooldown(Player player) {
        if (PermissionUtils.hasPermission(player, "shapeless.tpa.cooldown.bypass")) return false;
        LocalDateTime last = lastUsages.get(player.getUniqueId());
        return last != null && Duration.between(last, LocalDateTime.now()).getSeconds() < Config.tpaCooldown;
    }
}
