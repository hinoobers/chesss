package org.hinoob.chess.util;

import org.bukkit.Location;

public class LocationUtil {

    public static void ensureSafeTeleport(Location loc) {
        Location b = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        if (b.clone().subtract(0, 1, 0).getBlock().isEmpty()) {
            b.clone().subtract(0, 1, 0).getBlock().setType(org.bukkit.Material.STONE);
        }
    }
}
