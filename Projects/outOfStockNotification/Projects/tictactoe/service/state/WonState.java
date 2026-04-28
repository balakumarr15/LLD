package Projects.tictactoe.service.state;

import Projects.tictactoe.model.GameStatus;
import Projects.tictactoe.model.Player;

public class WonState implements GameState {
    @Override
    public void next(GameContext context, Player player, boolean hasWon, boolean isDraw) {
        // terminal
    }

    @Override
    public boolean isGameOver() { return true; }

    @Override
    public GameStatus getStatus() { return GameStatus.WIN; }
}
