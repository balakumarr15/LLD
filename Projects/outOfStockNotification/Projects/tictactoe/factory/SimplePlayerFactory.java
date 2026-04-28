package Projects.tictactoe.factory;

import Projects.tictactoe.model.Bot;
import Projects.tictactoe.model.BotDifficultyLevel;
import Projects.tictactoe.model.Player;
import Projects.tictactoe.model.PlayerType;
import Projects.tictactoe.service.botPlayingStrategy.BotPlayingStrategyFactory;
import Projects.tictactoe.service.playerStrategy.BotPlayerStrategy;
import Projects.tictactoe.service.playerStrategy.HumanPlayerStrategy;
import Projects.tictactoe.service.playerStrategy.PlayerStrategy;

import java.util.Scanner;

public class SimplePlayerFactory implements PlayerFactory {
    @Override
    public Player createHuman(int id, String name, char symbol, Scanner scanner) {
        PlayerStrategy strategy = new HumanPlayerStrategy(name, scanner);
        return new Player(id, name, symbol, PlayerType.HUMAN, strategy);
    }

    @Override
    public Player createBot(int id, String name, char symbol, BotDifficultyLevel level) {
        PlayerStrategy strategy = new BotPlayerStrategy(BotPlayingStrategyFactory.get(level));
        return new Bot(id, name, symbol, level, strategy);
    }
}
