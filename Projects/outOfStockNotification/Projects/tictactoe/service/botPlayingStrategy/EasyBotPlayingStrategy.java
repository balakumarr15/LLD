package Projects.tictactoe.service.botPlayingStrategy;

import Projects.tictactoe.exception.DrawGameException;
import Projects.tictactoe.model.Board;
import Projects.tictactoe.model.Cell;
import Projects.tictactoe.model.CellState;
import Projects.tictactoe.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyBotPlayingStrategy implements BotPlayingStrategy {
    private final Random random = new Random();

    @Override
    public Position selectCell(Board board) {
        List<Position> empty = new ArrayList<>();
        for (List<Cell> row : board.getMatrix()) {
            for (Cell cell : row) {
                if (cell.getCellState() == CellState.EMPTY) {
                    empty.add(new Position(cell.getRow(), cell.getCol()));
                }
            }
        }
        if (empty.isEmpty()) {
            throw new DrawGameException("No empty cells available for bot");
        }
        return empty.get(random.nextInt(empty.size()));
    }
}
