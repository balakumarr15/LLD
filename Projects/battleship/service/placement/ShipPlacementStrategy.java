package Projects.battleship.service.placement;

import java.util.List;

import Projects.battleship.model.Player;
import Projects.battleship.model.Ship;

public interface ShipPlacementStrategy {
    boolean placeShips(Player player, List<Ship> ships);
}
