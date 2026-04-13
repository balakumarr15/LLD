package Projects.tictactoe.service.winnerCheckStrategy;

import Projects.tictactoe.model.Board;
import Projects.tictactoe.model.Move;
import Projects.tictactoe.model.Player;

public interface WinnerCheckStrategy {
    Player checkWinner(Board board, Move lastPlayedMove);
}
