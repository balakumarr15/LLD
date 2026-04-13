package Projects.tictactoe.service.state;

import Projects.tictactoe.model.GameStatus;
import Projects.tictactoe.model.Player;

public class GameContext {
    private GameState currentState;
    private Player winner;

    public GameContext() {
        this.currentState = new YetToStartState();
    }

    public void setState(GameState state) { this.currentState = state; }
    public GameState getCurrentState() { return currentState; }

    public void next(Player player, boolean hasWon, boolean isDraw) {
        currentState.next(this, player, hasWon, isDraw);
    }

    public boolean isGameOver() { return currentState.isGameOver(); }
    public GameStatus getStatus() { return currentState.getStatus(); }

    public Player getWinner() { return winner; }
    public void setWinner(Player winner) { this.winner = winner; }
}
