package Projects.tictactoe.model;

import Projects.tictactoe.service.playerStrategy.PlayerStrategy;

public class Bot extends Player {
    private final BotDifficultyLevel botDifficultyLevel;

    public Bot(int id, String name, char symbol, BotDifficultyLevel level, PlayerStrategy strategy) {
        super(id, name, symbol, PlayerType.BOT, strategy);
        this.botDifficultyLevel = level;
    }

    public BotDifficultyLevel getBotDifficultyLevel() {
        return botDifficultyLevel;
    }
}
