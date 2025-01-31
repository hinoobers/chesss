package org.hinoob.chess.listener;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.game.ChessGame;

public class WorldProtectionListener implements Listener {

    private boolean isArenaWorld(World ww) {
        for(ChessGame game : ChessPlugin.getInstance().getGameManager().getGames()) {
            World w = game.getArena().getOnePos().getWorld();
            if(w.equals(ww)) {
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM || event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.COMMAND) {
            if(isArenaWorld(event.getLocation().getWorld())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        if(isArenaWorld(event.getWorld())) {
            event.setCancelled(true);
            event.getWorld().setStorm(false);
            event.getWorld().setThundering(false);
            event.getWorld().setWeatherDuration(0);
            event.getWorld().setThunderDuration(0);
            event.getWorld().setFullTime(0);
        }
    }
}
