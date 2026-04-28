package Projects.tictactoe.observer;

import Projects.tictactoe.model.Move;
import Projects.tictactoe.model.Player;
import Projects.tictactoe.service.state.GameState;

public interface GameEventListener {
    void onMoveMade(Move move);
    void onGameStateChanged(GameState newState, Player winner);
}
