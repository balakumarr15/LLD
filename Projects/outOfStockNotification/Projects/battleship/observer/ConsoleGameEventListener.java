package Projects.battleship.observer;

import Projects.battleship.model.Player;
import Projects.battleship.model.Ship;
import Projects.battleship.model.Shot;

public class ConsoleGameEventListener implements GameEventListener {

    @Override
    public void onShotFired(Shot shot) {
        System.out.println("[EVENT] " + shot);
    }

    @Override
    public void onShipSunk(Player owner, Ship ship) {
        System.out.println("[EVENT] " + owner.getName() + "'s " + ship.getType() + " was sunk!");
    }

    @Override
    public void onGameOver(Player winner) {
        System.out.println("[EVENT] Game over. Winner: " + winner.getName());
    }
}
