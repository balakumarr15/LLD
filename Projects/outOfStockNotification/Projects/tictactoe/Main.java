package Projects.tictactoe;

import Projects.tictactoe.controller.GameController;
import Projects.tictactoe.factory.PlayerFactory;
import Projects.tictactoe.factory.SimplePlayerFactory;
import Projects.tictactoe.model.BotDifficultyLevel;
import Projects.tictactoe.model.Game;
import Projects.tictactoe.model.GameStatus;
import Projects.tictactoe.model.Move;
import Projects.tictactoe.model.Player;
import Projects.tictactoe.observer.ConsoleGameEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlayerFactory playerFactory = new SimplePlayerFactory();

        System.out.println("Welcome to TicTacToe!");
        System.out.print("Enter board dimension (3-10): ");
        int dimension = scanner.nextInt();

        System.out.print("Include a bot? (Y/N): ");
        String botAns = scanner.next();

        List<Player> players = new ArrayList<>();
        int id = 1;

        if (botAns.equalsIgnoreCase("Y")) {
            System.out.print("Bot difficulty (EASY/MEDIUM/HARD): ");
            BotDifficultyLevel level = BotDifficultyLevel.valueOf(scanner.next().toUpperCase());
            players.add(playerFactory.createBot(id++, "BOT", '$', level));
        }

        while (players.size() < dimension - 1) {
            System.out.print("Player " + id + " name: ");
            String name = scanner.next();
            System.out.print("Player " + id + " symbol (single char): ");
            char symbol = scanner.next().charAt(0);
            players.add(playerFactory.createHuman(id++, name, symbol, scanner));
        }

        Collections.shuffle(players);

        Game game = Game.builder()
                .setDimension(dimension)
                .setPlayers(players)
                .addListener(new ConsoleGameEventListener())
                .build();

        GameController controller = new GameController();

        controller.transitionState(game, null, false, false);

        while (!game.getGameContext().isGameOver()) {
            controller.displayBoard(game);
            Player currentPlayer = game.getCurrentPlayer();
            Move move = controller.executeMove(game, currentPlayer);
            Player winner = controller.checkWinner(game, move);
            boolean hasWon = winner != null;
            boolean isDraw = !hasWon && controller.isDraw(game);
            controller.transitionState(game, currentPlayer, hasWon, isDraw);
            if (!game.getGameContext().isGameOver()) {
                game.advancePlayer();
            }
        }

        controller.displayBoard(game);
        GameStatus finalStatus = controller.getGameStatus(game);
        if (finalStatus == GameStatus.WIN) {
            System.out.println("WINNER: " + game.getGameContext().getWinner().getName());
        } else if (finalStatus == GameStatus.DRAW) {
            System.out.println("Game is a DRAW!");
        }
    }
}
