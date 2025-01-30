package org.hinoob.chess.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class Arena {

    private String name;
    private String worldName;
    private Location one, two; // borders

    public Arena(ConfigurationSection section) {
        this.name = section.getName();
        this.worldName = section.getString("world");
        this.one = new Location(null, section.getDouble("pos1.x"), section.getDouble("pos1.y"), section.getDouble("pos1.z"));
        this.two = new Location(null, section.getDouble("pos2.x"), section.getDouble("pos2.y"), section.getDouble("pos2.z"));
    }

    public String getName() {
        return name;
    }

    public String getWorldName() {
        return worldName;
    }

    public Location getOne() {
        return one;
    }

    public Location getTwo() {
        return two;
    }




}
