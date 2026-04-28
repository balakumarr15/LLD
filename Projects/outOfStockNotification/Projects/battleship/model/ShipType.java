package Projects.battleship.model;

public enum ShipType {
    CARRIER(5),
    BATTLESHIP(4),
    SUBMARINE(3),
    DESTROYER(2),
    PATROL_BOAT(1);

    private final int size;

    ShipType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
