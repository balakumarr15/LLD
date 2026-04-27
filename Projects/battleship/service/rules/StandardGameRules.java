package Projects.battleship.service.rules;

import Projects.battleship.model.Direction;
import Projects.battleship.model.Player;
import Projects.battleship.model.Position;
import Projects.battleship.model.Ship;
import Projects.battleship.service.board.BoardInterface;
import Projects.battleship.service.board.BoardValidator;

public class StandardGameRules implements GameRules {
    private static final int DEFAULT_BOARD_SIZE = 10;

    private final int boardSize;
    private final BoardValidator validator;

    public StandardGameRules() {
        this(DEFAULT_BOARD_SIZE, new BoardValidator());
    }

    public StandardGameRules(int boardSize, BoardValidator validator) {
        this.boardSize = boardSize;
        this.validator = validator;
    }

    @Override
    public boolean validateShipPlacement(BoardInterface board, Ship ship, Position start, Direction direction) {
        return validator.validateShipPlacement(board, ship, start, direction);
    }

    @Override
    public boolean validateShot(Player shooter, Player opponent, Position position) {
        if (shooter == null || opponent == null || shooter.equals(opponent)) return false;
        return validator.validateShotPosition(opponent.getOwnBoard(), position);
    }

    @Override
    public boolean isGameOver(Player[] players) {
        if (players == null) return false;
        for (Player p : players) {
            if (p.allShipsSunk()) return true;
        }
        return false;
    }

    @Override
    public Player determineWinner(Player[] players) {
        if (players == null) return null;
        for (Player p : players) {
            if (!p.allShipsSunk()) {
                return p;
            }
        }
        return null;
    }

    @Override
    public int getBoardSize() {
        return boardSize;
    }
}
