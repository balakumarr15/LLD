package Projects.battleship.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Projects.battleship.model.Ship;
import Projects.battleship.model.ShipType;

public class ShipFactory {

    public Ship createShip(ShipType type) {
        if (type == null) {
            throw new IllegalArgumentException("ShipType cannot be null");
        }
        return new Ship(type);
    }

    public List<Ship> createStandardFleet() {
        return createFleet(Arrays.asList(
                ShipType.CARRIER,
                ShipType.BATTLESHIP,
                ShipType.SUBMARINE,
                ShipType.DESTROYER,
                ShipType.PATROL_BOAT
        ));
    }

    public List<Ship> createFleet(List<ShipType> types) {
        List<Ship> fleet = new ArrayList<>();
        for (ShipType t : types) {
            fleet.add(createShip(t));
        }
        return fleet;
    }

    public Ship cloneShip(Ship ship) {
        return ship.clone();
    }
}
