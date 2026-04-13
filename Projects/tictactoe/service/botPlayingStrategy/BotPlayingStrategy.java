package Projects.tictactoe.service.botPlayingStrategy;

import Projects.tictactoe.model.Board;
import Projects.tictactoe.model.Position;

public interface BotPlayingStrategy {
    Position selectCell(Board board);
}
