package Projects.battleship.model;

import java.util.ArrayList;
import java.util.List;

public class Ship implements Cloneable {
    private final ShipType type;
    private final int size;
    private int health;
    private final List<Position> positions;

    public Ship(ShipType type) {
        this.type = type;
        this.size = type.getSize();
        this.health = type.getSize();
        this.positions = new ArrayList<>();
    }

    public ShipType getType() { return type; }

    public int getSize() { return size; }

    public int getHealth() { return health; }

    public List<Position> getPositions() {
        return new ArrayList<>(positions);
    }

    public void addPosition(Position position) {
        if (!positions.contains(position)) {
            positions.add(position);
        }
    }

    public void registerHit() {
        if (health > 0) {
            health--;
        }
    }

    public boolean isSunk() {
        return health == 0;
    }

    public boolean occupiesPosition(Position position) {
        return positions.stream().anyMatch(p -> p.equals(position));
    }

    @Override
    public Ship clone() {
        try {
            Ship cloned = (Ship) super.clone();
            java.lang.reflect.Field positionsField = Ship.class.getDeclaredField("positions");
            positionsField.setAccessible(true);
            positionsField.set(cloned, new ArrayList<>());
            return cloned;
        } catch (CloneNotSupportedException | NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }
}
