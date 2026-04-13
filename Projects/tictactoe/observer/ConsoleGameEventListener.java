package Projects.tictactoe.observer;

import Projects.tictactoe.model.Move;
import Projects.tictactoe.model.Player;
import Projects.tictactoe.service.state.GameState;

public class ConsoleGameEventListener implements GameEventListener {
    @Override
    public void onMoveMade(Move move) {
        System.out.printf("Move: %s played %c at (%d, %d)%n",
                move.getPlayer().getName(),
                move.getPlayer().getSymbol(),
                move.getCell().getRow(),
                move.getCell().getCol());
    }

    @Override
    public void onGameStateChanged(GameState newState, Player winner) {
        System.out.println("Game state changed to: " + newState.getStatus()
                + (winner != null ? " (winner: " + winner.getName() + ")" : ""));
    }
}
