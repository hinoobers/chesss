package org.hinoob.chess.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.game.ChessPlayer;
import org.hinoob.chess.playerdata.PlayerData;

public class LeaveListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerData data = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(event.getPlayer().getUniqueId());

        if(data.activeGame != null) {
            boolean playingWithBot = data.player.isWhite() && !data.activeGame.getBlack().isHuman() || !data.player.isWhite() && !data.activeGame.getWhite().isHuman();
            if(playingWithBot) {
                // Cancel the game
                data.activeGame.cleanGame();
            }
        }
    }
}
