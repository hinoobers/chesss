package org.hinoob.chess.command;

import org.bukkit.entity.Player;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.game.ChessGame;
import org.hinoob.chess.game.ChessPlayer;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;

@Command("chessgame")
public class GameCommand {

    @Subcommand("test")
    public void test(Player player) {
        ChessGame game = ChessPlugin.getInstance().getGameManager().createDummyGame(player);
        game.start();
    }
}
