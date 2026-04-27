package Projects.battleship.model;

public class Cell {
    private final Position position;
    private Ship ship;
    private boolean isShot;

    public Cell(Position position) {
        this.position = position;
        this.ship = null;
        this.isShot = false;
    }

    public Position getPosition() { return position; }

    public boolean hasShip() {
        return ship != null;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        if (ship != null) {
            ship.addPosition(this.position);
        }
    }

    public boolean isShot() { return isShot; }

    public void markShot() {
        this.isShot = true;
    }
}
