package Projects.tictactoe.service.botPlayingStrategy;

import Projects.tictactoe.exception.DrawGameException;
import Projects.tictactoe.model.Board;
import Projects.tictactoe.model.Cell;
import Projects.tictactoe.model.CellState;
import Projects.tictactoe.model.Position;

public class HardBotPlayingStrategy implements BotPlayingStrategy {
    @Override
    public Position selectCell(Board board) {
        int n = board.getDimension();
        int[][] preferred = {{n / 2, n / 2}, {0, 0}, {0, n - 1}, {n - 1, 0}, {n - 1, n - 1}};
        for (int[] p : preferred) {
            Cell c = board.getCell(p[0], p[1]);
            if (c.getCellState() == CellState.EMPTY) {
                return new Position(p[0], p[1]);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board.getCell(i, j).getCellState() == CellState.EMPTY) {
                    return new Position(i, j);
                }
            }
        }
        throw new DrawGameException("No empty cells available for bot");
    }
}
