package Projects.battleship.exception;

public class InvalidShotException extends RuntimeException {
    public InvalidShotException(String message) {
        super(message);
    }
}
