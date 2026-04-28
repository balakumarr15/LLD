package Projects.tictactoe.service.state;

import Projects.tictactoe.model.GameStatus;
import Projects.tictactoe.model.Player;

public interface GameState {
    void next(GameContext context, Player player, boolean hasWon, boolean isDraw);
    boolean isGameOver();
    GameStatus getStatus();
}
