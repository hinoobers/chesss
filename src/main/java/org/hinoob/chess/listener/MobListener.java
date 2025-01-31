package org.hinoob.chess.listener;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.game.ChessGame;

public class MobListener implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM || event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.COMMAND) {
            for(ChessGame game : ChessPlugin.getInstance().getGameManager().getGames()) {
                World w = game.getArena().getOnePos().getWorld();
                if(w.equals(event.getLocation().getWorld())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
