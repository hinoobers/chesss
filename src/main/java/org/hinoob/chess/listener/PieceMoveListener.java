package org.hinoob.chess.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.engine.Move;
import org.hinoob.chess.playerdata.PlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PieceMoveListener implements Listener {

    @EventHandler
    public void onClick(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            PlayerData data = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(event.getDamager().getUniqueId());
            if(data.activeGame == null) return;

//            if(data.activeGame.getBoard().isWhiteTurn() && !data.player.isWhite()) {
//                event.getDamager().sendMessage(ChatColor.RED + "It's not your turn!");
//                return;
//            } else if(!data.activeGame.getBoard().isWhiteTurn() && data.player.isWhite()) {
//                event.getDamager().sendMessage(ChatColor.RED + "It's not your turn!");
//                return;
//            }

            ChessPiece piece = data.activeGame.entityToPiece(event.getEntity());
//            if(piece.isWhite() != data.player.isWhite()) {
//                event.getDamager().sendMessage(ChatColor.RED + "It's not your piece!");
//                return;
//            }

            data.selectedPiece = piece;
            event.getDamager().sendMessage("You selected that piece! Now hit where you want it to move to!");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        PlayerData data = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(event.getPlayer().getUniqueId());
        if(data.activeGame == null) return;
        event.setCancelled(true);

        //if(data.selectedPiece == null || (data.selectedPiece.isWhite() && !data.player.isWhite()) || (!data.selectedPiece.isWhite() && data.player.isWhite())) return;
        boolean allow = false;
        if(data.activeGame.getBoard().isWhiteTurn() && data.player.isWhite()) {
            allow = true;
        } else if(!data.activeGame.getBoard().isWhiteTurn() && !data.player.isWhite()) {
            allow = true;
        }

        if(!allow) {
            //return;
        }

        Location loc = event.getBlock().getLocation().clone().add(0,1,0).subtract(data.activeGame.getArena().getOnePos());
        List<Move> possibleMoves = new ArrayList<>();
        data.selectedPiece.getPossibleMoves(possibleMoves);

        Optional<Move> opt = possibleMoves.stream().filter(s -> s.getNewX() == loc.getX() && s.getNewY() == loc.getZ()).findFirst();
        if(opt.isPresent()) {
            data.activeGame.handleMove(data.selectedPiece, opt.get(), data.player);
            data.selectedPiece = null;
        } else {
            event.getPlayer().sendMessage(ChatColor.RED + "That's an illegal move!");
        }
    }
}
