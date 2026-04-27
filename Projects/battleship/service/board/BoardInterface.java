package Projects.battleship.service.board;

import Projects.battleship.model.Cell;
import Projects.battleship.model.Direction;
import Projects.battleship.model.Position;
import Projects.battleship.model.Ship;
import Projects.battleship.model.ShotResult;

public interface BoardInterface {
    boolean placeShip(Ship ship, Position start, Direction direction);

    ShotResult receiveShot(Position position);

    boolean isValidPosition(Position position);

    boolean allShipsSunk();

    Cell getCell(Position position);

    int getSize();
}
