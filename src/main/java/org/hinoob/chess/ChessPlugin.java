package org.hinoob.chess;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.hinoob.chess.arena.ArenaManager;
import org.hinoob.chess.command.CommandManager;
import org.hinoob.chess.game.GameManager;
import org.hinoob.chess.listener.JoinListener;
import org.hinoob.chess.listener.MobListener;
import org.hinoob.chess.listener.PieceMoveListener;
import org.hinoob.chess.playerdata.PlayerDataManager;
import org.hinoob.chess.util.FileUtil;

import java.io.File;

@Getter
public class ChessPlugin extends JavaPlugin {

    @Getter @Setter private static ChessPlugin instance;

    private PlayerDataManager playerDataManager = new PlayerDataManager();
    private ArenaManager arenaManager = new ArenaManager();
    private GameManager gameManager = new GameManager();

    @Override
    public void onEnable() {
        instance = this;
        CommandManager.register(this);
        arenaManager.load(this);

        for(File file : Bukkit.getWorldContainer().listFiles()) {
            if(file.isDirectory() && file.getName().endsWith("_achess")) {
                FileUtil.delete(file);
            }
        }

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new PieceMoveListener(), this);
        getServer().getPluginManager().registerEvents(new MobListener(), this);
    }

    @Override
    public void onDisable() {
        arenaManager.save();
    }

}
