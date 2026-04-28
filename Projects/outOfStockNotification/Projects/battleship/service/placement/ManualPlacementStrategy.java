package Projects.battleship.service.placement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Projects.battleship.model.Direction;
import Projects.battleship.model.Player;
import Projects.battleship.model.Position;
import Projects.battleship.model.Ship;

public class ManualPlacementStrategy implements ShipPlacementStrategy {

    public static class Placement {
        final Position start;
        final Direction direction;

        public Placement(Position start, Direction direction) {
            this.start = start;
            this.direction = direction;
        }
    }

    private final Map<Ship, Placement> placements;

    public ManualPlacementStrategy() {
        this.placements = new HashMap<>();
    }

    public ManualPlacementStrategy specify(Ship ship, Position start, Direction direction) {
        placements.put(ship, new Placement(start, direction));
        return this;
    }

    @Override
    public boolean placeShips(Player player, List<Ship> ships) {
        for (Ship ship : ships) {
            Placement p = placements.get(ship);
            if (p == null) return false;
            if (!player.placeShip(ship, p.start, p.direction)) return false;
        }
        return true;
    }
}
