package Projects.tictactoe.service.state;

import Projects.tictactoe.model.GameStatus;
import Projects.tictactoe.model.Player;

public class InProgressState implements GameState {
    @Override
    public void next(GameContext context, Player player, boolean hasWon, boolean isDraw) {
        if (hasWon) {
            context.setWinner(player);
            context.setState(new WonState());
        } else if (isDraw) {
            context.setState(new DrawState());
        }
    }

    @Override
    public boolean isGameOver() { return false; }

    @Override
    public GameStatus getStatus() { return GameStatus.IN_PROGRESS; }
}
