package com.shapelessnetwork.shapelessessentials.utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.permissions.Permissible;

public class PermissionUtils {

    public static boolean hasPermission(Permissible permissible, String node) {
        String check = node;
        int index;
        for (int i = 0; i < StringUtils.countMatches(node, ".") + 1; i++) {
            if (permissible.isPermissionSet(check)) return permissible.hasPermission(check);
            index = node.lastIndexOf(".");
            if (index < 1) {
                break;
            }
            node = node.substring(0, index);
            check = node + ".*";
        }
        return permissible.hasPermission("*");
    }
}
