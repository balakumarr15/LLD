package Projects.battleship.exception;

public class InvalidShipPlacementException extends RuntimeException {
    public InvalidShipPlacementException(String message) {
        super(message);
    }
}
