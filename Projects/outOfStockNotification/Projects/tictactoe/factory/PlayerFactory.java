package Projects.tictactoe.factory;

import Projects.tictactoe.model.BotDifficultyLevel;
import Projects.tictactoe.model.Player;

import java.util.Scanner;

public interface PlayerFactory {
    Player createHuman(int id, String name, char symbol, Scanner scanner);
    Player createBot(int id, String name, char symbol, BotDifficultyLevel level);
}
