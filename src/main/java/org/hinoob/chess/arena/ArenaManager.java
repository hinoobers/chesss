package org.hinoob.chess.arena;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.units.qual.A;
import org.hinoob.chess.ChessPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private File file;
    private FileConfiguration config;
    private List<Arena> arenas = new ArrayList<>();


    public void load(ChessPlugin plugin) {
        this.file = new File(plugin.getDataFolder(), "arenas.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection arenas = config.getConfigurationSection("arenas");
        if(arenas == null) return;

        arenas.getKeys(false).forEach(key -> {
            Arena arena = new Arena(arenas.getConfigurationSection(key));
            this.arenas.add(arena);
            plugin.getLogger().info("Loaded arena!");
        });
    }

    public void createArena(String name, Location one, Location two) {
        config.set("arenas." + name + ".pos1.x", one.getBlockX());
        config.set("arenas." + name + ".pos1.y", one.getBlockY());
        config.set("arenas." + name + ".pos1.z", one.getBlockZ());
        config.set("arenas." + name + ".pos2.x", two.getBlockX());
        config.set("arenas." + name + ".pos2.y", two.getBlockY());
        config.set("arenas." + name + ".pos2.z", two.getBlockZ());
        config.set("arenas." + name + ".world", one.getWorld().getName());
        save();

        this.arenas.add(new Arena(config.getConfigurationSection("arenas." + name)));
    }

    public Arena getRandomArena() {
        return arenas.get((int) (Math.random() * arenas.size()));
    }


    public void save() {
        try {
            config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
