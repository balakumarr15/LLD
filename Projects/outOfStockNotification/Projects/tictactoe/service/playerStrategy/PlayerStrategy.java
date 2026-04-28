package Projects.tictactoe.service.playerStrategy;

import Projects.tictactoe.model.Board;
import Projects.tictactoe.model.Position;

public interface PlayerStrategy {
    Position makeMove(Board board);
}
