package org.hinoob.chess.command;

import org.bukkit.Bukkit;
import org.hinoob.chess.ChessPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.command.CommandActor;

public class CommandManager {

    public static void register(ChessPlugin plugin) {
        Lamp<BukkitCommandActor> wrapper = BukkitLamp.builder(plugin).build();
        wrapper.register(new ArenaCommand());
        wrapper.register(new GameCommand());
        wrapper.register(new SetSpawnCommand());

    }
}
