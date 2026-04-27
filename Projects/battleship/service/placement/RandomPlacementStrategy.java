package Projects.battleship.service.placement;

import java.util.List;
import java.util.Random;

import Projects.battleship.model.Direction;
import Projects.battleship.model.Player;
import Projects.battleship.model.Position;
import Projects.battleship.model.Ship;

public class RandomPlacementStrategy implements ShipPlacementStrategy {
    private static final int MAX_ATTEMPTS = 200;
    private final Random random;

    public RandomPlacementStrategy() {
        this(new Random());
    }

    public RandomPlacementStrategy(Random random) {
        this.random = random;
    }

    @Override
    public boolean placeShips(Player player, List<Ship> ships) {
        int size = player.getOwnBoard().getSize();
        for (Ship ship : ships) {
            boolean placed = false;
            int attempts = 0;
            while (!placed && attempts < MAX_ATTEMPTS) {
                int row = random.nextInt(size);
                int col = random.nextInt(size);
                Direction direction = random.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL;
                placed = player.placeShip(ship, new Position(row, col), direction);
                attempts++;
            }
            if (!placed) return false;
        }
        return true;
    }
}
