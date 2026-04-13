package Projects.tictactoe.service.botPlayingStrategy;

import Projects.tictactoe.exception.DrawGameException;
import Projects.tictactoe.model.Board;
import Projects.tictactoe.model.Cell;
import Projects.tictactoe.model.CellState;
import Projects.tictactoe.model.Position;

import java.util.List;

public class MediumBotPlayingStrategy implements BotPlayingStrategy {
    @Override
    public Position selectCell(Board board) {
        int n = board.getDimension();
        int center = n / 2;
        Cell c = board.getCell(center, center);
        if (c.getCellState() == CellState.EMPTY) {
            return new Position(center, center);
        }
        for (List<Cell> row : board.getMatrix()) {
            for (Cell cell : row) {
                if (cell.getCellState() == CellState.EMPTY) {
                    return new Position(cell.getRow(), cell.getCol());
                }
            }
        }
        throw new DrawGameException("No empty cells available for bot");
    }
}
