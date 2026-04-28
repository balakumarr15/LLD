package Projects.tictactoe.service.playerStrategy;

import Projects.tictactoe.model.Board;
import Projects.tictactoe.model.Position;

import java.util.Scanner;

public class HumanPlayerStrategy implements PlayerStrategy {
    private final String playerName;
    private final Scanner scanner;

    public HumanPlayerStrategy(String playerName) {
        this(playerName, new Scanner(System.in));
    }

    public HumanPlayerStrategy(String playerName, Scanner scanner) {
        this.playerName = playerName;
        this.scanner = scanner;
    }

    @Override
    public Position makeMove(Board board) {
        while (true) {
            System.out.printf("%s, enter your move as 'row col' (0 to %d): ",
                    playerName, board.getDimension() - 1);
            try {
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                Position move = new Position(row, col);
                if (board.isValidMove(move)) return move;
                System.out.println("Invalid move. Try again.");
            } catch (Exception e) {
                System.out.println("Invalid input. Enter row and column as numbers.");
                scanner.nextLine();
            }
        }
    }
}
