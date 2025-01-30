package org.hinoob.chess.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.hinoob.chess.util.FileUtil;

import java.io.File;
import java.util.UUID;

public class ActiveArena {

    private Arena arena;
    private World world;
    private String worldName;

    public ActiveArena(Arena arena) {
        this.arena = arena;
    }

    public void createWorld() {
        worldName = arena.getName() + "_" + UUID.randomUUID().toString().split("-")[0] + "_ac";
        FileUtil.copy(new File(Bukkit.getWorldContainer(), arena.getWorldName()), new File(Bukkit.getWorldContainer(), worldName));

        // Will cause issues if these are not deleted
        new File(new File(Bukkit.getWorldContainer(), worldName), "session.lock").delete();
        new File(new File(Bukkit.getWorldContainer(), worldName), "uid.dat").delete();

        this.world = new WorldCreator(worldName).createWorld();
    }

    public void refreshBoard() {
        // create chess board
        int size = arena.getTwo().getBlockX() - arena.getOne().getBlockX();
        int y = arena.getOne().getBlockY();

        for(int x = arena.getOne().getBlockX(); x <= arena.getTwo().getBlockX(); x++) {
            for(int z = arena.getOne().getBlockZ(); z <= arena.getTwo().getBlockZ(); z++) {
                Block block = world.getBlockAt(x, y, z);

                if((x + z) % 2 == 0) {
                    block.setType(org.bukkit.Material.WHITE_WOOL);
                } else {
                    block.setType(org.bukkit.Material.BLACK_WOOL);
                }
            }
        }
    }

    public Location getOnePos() {
        Location loc = arena.getOne().clone();
        loc.setWorld(world);
        return loc;
    }

    public Location getTwoPos() {
        Location loc = arena.getTwo().clone();
        loc.setWorld(world);
        return loc;
    }
}
