package Projects.battleship.observer;

import Projects.battleship.model.Player;
import Projects.battleship.model.Ship;
import Projects.battleship.model.Shot;

public interface GameEventListener {
    void onShotFired(Shot shot);

    void onShipSunk(Player owner, Ship ship);

    void onGameOver(Player winner);
}
