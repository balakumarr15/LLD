package Projects.battleship.service.rules;

import Projects.battleship.model.Direction;
import Projects.battleship.model.Player;
import Projects.battleship.model.Position;
import Projects.battleship.model.Ship;
import Projects.battleship.service.board.BoardInterface;

public interface GameRules {
    boolean validateShipPlacement(BoardInterface board, Ship ship, Position start, Direction direction);

    boolean validateShot(Player shooter, Player opponent, Position position);

    boolean isGameOver(Player[] players);

    Player determineWinner(Player[] players);

    int getBoardSize();
}
