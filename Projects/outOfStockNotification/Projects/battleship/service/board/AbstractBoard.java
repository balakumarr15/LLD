package Projects.battleship.service.board;

import java.util.ArrayList;
import java.util.List;

import Projects.battleship.model.Cell;
import Projects.battleship.model.Position;
import Projects.battleship.model.Ship;

public abstract class AbstractBoard implements BoardInterface {
    protected final int size;
    protected final Cell[][] cells;
    protected final List<Ship> ships;

    protected AbstractBoard(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        this.ships = new ArrayList<>();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                cells[r][c] = new Cell(new Position(r, c));
            }
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isValidPosition(Position position) {
        return position != null
                && position.getRow() >= 0 && position.getRow() < size
                && position.getColumn() >= 0 && position.getColumn() < size;
    }

    @Override
    public Cell getCell(Position position) {
        if (!isValidPosition(position)) {
            return null;
        }
        return cells[position.getRow()][position.getColumn()];
    }

    @Override
    public boolean allShipsSunk() {
        if (ships.isEmpty()) return false;
        return ships.stream().allMatch(Ship::isSunk);
    }

    public List<Ship> getShips() {
        return new ArrayList<>(ships);
    }
}
