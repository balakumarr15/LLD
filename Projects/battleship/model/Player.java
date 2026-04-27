package Projects.battleship.model;

import java.util.Objects;

public class Player {
    private final String id;
    private final String name;
    private final Board ownBoard;
    private final Board trackingBoard;

    public Player(String id, String name, int boardSize) {
        this.id = id;
        this.name = name;
        this.ownBoard = new Board(boardSize);
        this.trackingBoard = new Board(boardSize);
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public Board getOwnBoard() { return ownBoard; }

    public Board getTrackingBoard() { return trackingBoard; }

    public boolean placeShip(Ship ship, Position start, Direction direction) {
        return ownBoard.placeShip(ship, start, direction);
    }

    public ShotResult fireShot(Position position, Player opponent) {
        ShotResult result = opponent.getOwnBoard().receiveShot(position);
        trackingBoard.getCell(position).markShot();
        return result;
    }

    public boolean allShipsSunk() {
        return ownBoard.allShipsSunk();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player p = (Player) o;
        return Objects.equals(id, p.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
