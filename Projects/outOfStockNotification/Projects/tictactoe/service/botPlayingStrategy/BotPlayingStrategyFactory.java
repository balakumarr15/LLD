package Projects.tictactoe.service.botPlayingStrategy;

import Projects.tictactoe.model.BotDifficultyLevel;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy get(BotDifficultyLevel level) {
        switch (level) {
            case EASY:   return new EasyBotPlayingStrategy();
            case MEDIUM: return new MediumBotPlayingStrategy();
            case HARD:   return new HardBotPlayingStrategy();
            default:     return new EasyBotPlayingStrategy();
        }
    }
}
