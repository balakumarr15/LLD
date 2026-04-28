package Projects.battleship.service.board;

import Projects.battleship.model.Direction;
import Projects.battleship.model.Position;
import Projects.battleship.model.Ship;

public class BoardValidator {

    public boolean validateShipPlacement(BoardInterface board, Ship ship, Position start, Direction direction) {
        if (board == null || ship == null || start == null || direction == null) {
            return false;
        }
        int size = ship.getSize();
        for (int i = 0; i < size; i++) {
            Position p = nextPosition(start, direction, i);
            if (!board.isValidPosition(p)) return false;
            if (board.getCell(p).hasShip()) return false;
        }
        return true;
    }

    public boolean validateShotPosition(BoardInterface board, Position position) {
        if (board == null || position == null) return false;
        if (!board.isValidPosition(position)) return false;
        return !board.getCell(position).isShot();
    }

    public Position nextPosition(Position start, Direction direction, int offset) {
        if (direction == Direction.HORIZONTAL) {
            return new Position(start.getRow(), start.getColumn() + offset);
        }
        return new Position(start.getRow() + offset, start.getColumn());
    }
}
