package org.hinoob.chess.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.game.ChessGame;
import org.hinoob.chess.game.ChessPlayer;
import org.hinoob.chess.playerdata.PlayerData;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Subcommand;

import java.util.UUID;

@Command("chessgame")
public class GameCommand {

    @Subcommand("test")
    public void test(Player player) {
        ChessGame game = ChessPlugin.getInstance().getGameManager().createDummyGame(player);
        game.start();
    }

    @Subcommand("invite")
    public void invite(Player player, Player target) {
        if(player.equals(target)) {
            player.sendMessage(ChatColor.RED + "You can't invite yourself!");
            return;
        }

        PlayerData targetData = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(target.getUniqueId());
        targetData.invites.add(player.getUniqueId());

        target.sendMessage(ChatColor.GREEN + "You have been invited for a chess game! Type /chessgame accept to join");
        player.sendMessage(ChatColor.GREEN + "Invite sent");
    }

    @Subcommand("accept")
    public void accept(Player player, @Optional Player target) {
        PlayerData targetData = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(player.getUniqueId());
        if(targetData.invites.isEmpty()) {
            player.sendMessage(ChatColor.RED + "You have not been invited!");
            return;
        }

        Player trgt = null;
        if(targetData.invites.size() == 1) {
            if(Bukkit.getPlayer(targetData.invites.get(0)).isOnline()) {
                trgt = Bukkit.getPlayer(targetData.invites.get(0));
            } else {
                player.sendMessage(ChatColor.RED + "Player is no longer online, invalidating invite..");
                targetData.invites.clear();
                return;
            }
        } else {
            if(target != null) {
                UUID u = target.getUniqueId();
                if(targetData.invites.contains(u)) {
                    trgt = target;
                }
            } else {
                player.sendMessage(ChatColor.RED + "You have multiple invites, please specify which one you'd like to accept!");
                return;
            }
        }

        ChessGame game = ChessPlugin.getInstance().getGameManager().createGame(player, trgt);
        game.start();
    }
}
