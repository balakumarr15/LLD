package Projects.battleship.model;

import Projects.battleship.service.board.AbstractBoard;
import Projects.battleship.service.board.BoardValidator;

public class Board extends AbstractBoard {

    private final BoardValidator validator;

    public Board(int size) {
        this(size, new BoardValidator());
    }

    public Board(int size, BoardValidator validator) {
        super(size);
        this.validator = validator;
    }

    @Override
    public boolean placeShip(Ship ship, Position start, Direction direction) {
        if (!validator.validateShipPlacement(this, ship, start, direction)) {
            return false;
        }
        for (int i = 0; i < ship.getSize(); i++) {
            Position p = validator.nextPosition(start, direction, i);
            Cell cell = getCell(p);
            cell.setShip(ship);
        }
        ships.add(ship);
        return true;
    }

    @Override
    public ShotResult receiveShot(Position position) {
        if (!isValidPosition(position)) {
            return ShotResult.MISS;
        }
        Cell cell = getCell(position);
        if (cell.isShot()) {
            return ShotResult.ALREADY_SHOT;
        }
        cell.markShot();
        if (!cell.hasShip()) {
            return ShotResult.MISS;
        }
        Ship ship = cell.getShip();
        ship.registerHit();
        return ship.isSunk() ? ShotResult.SUNK : ShotResult.HIT;
    }
}
