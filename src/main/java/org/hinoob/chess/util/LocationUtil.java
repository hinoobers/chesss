package org.hinoob.chess.util;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.hinoob.chess.ChessPlugin;

public class LocationUtil {

    public static void ensureSafeTeleport(Location loc) {
        Location b = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        if (b.clone().subtract(0, 1, 0).getBlock().isEmpty()) {
            b.clone().subtract(0, 1, 0).getBlock().setType(org.bukkit.Material.STONE);
        }
    }

    public static Location parseLocationFromConfig(String path) {
        ConfigurationSection section = ChessPlugin.getInstance().getConfig().getConfigurationSection(path);
        if(section == null) return null;
        return new Location(ChessPlugin.getInstance().getServer().getWorld(section.getString("world")), section.getDouble("x"), section.getDouble("y"), section.getDouble("z"), (float) section.getDouble("yaw"), (float) section.getDouble("pitch"));
    }
}
