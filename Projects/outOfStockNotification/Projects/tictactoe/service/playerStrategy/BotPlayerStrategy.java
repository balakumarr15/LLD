package Projects.tictactoe.service.playerStrategy;

import Projects.tictactoe.model.Board;
import Projects.tictactoe.model.Position;
import Projects.tictactoe.service.botPlayingStrategy.BotPlayingStrategy;

public class BotPlayerStrategy implements PlayerStrategy {
    private final BotPlayingStrategy botPlayingStrategy;

    public BotPlayerStrategy(BotPlayingStrategy botPlayingStrategy) {
        this.botPlayingStrategy = botPlayingStrategy;
    }

    @Override
    public Position makeMove(Board board) {
        return botPlayingStrategy.selectCell(board);
    }
}
