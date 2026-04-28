package Projects.tictactoe.service.state;

import Projects.tictactoe.model.GameStatus;
import Projects.tictactoe.model.Player;

public class YetToStartState implements GameState {
    @Override
    public void next(GameContext context, Player player, boolean hasWon, boolean isDraw) {
        context.setState(new InProgressState());
    }

    @Override
    public boolean isGameOver() { return false; }

    @Override
    public GameStatus getStatus() { return GameStatus.YET_TO_START; }
}
