package org.hinoob.chess.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hinoob.chess.ChessPlugin;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ChessPlugin.getInstance().getPlayerDataManager().createPlayerData(event.getPlayer().getUniqueId());
    }
}
