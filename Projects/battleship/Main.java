package Projects.battleship;

import Projects.battleship.controller.GameController;
import Projects.battleship.model.Cell;
import Projects.battleship.model.Player;
import Projects.battleship.model.Position;
import Projects.battleship.model.ShotResult;
import Projects.battleship.observer.ConsoleGameEventListener;
import Projects.battleship.service.placement.RandomPlacementStrategy;

public class Main {
    public static void main(String[] args) {
        GameController controller = new GameController("Alice", "Bob");
        controller.registerListener(new ConsoleGameEventListener());
        controller.start();

        Player[] players = controller.players();
        RandomPlacementStrategy strategy = new RandomPlacementStrategy();
        for (Player p : players) {
            boolean placed = controller.setupPlayer(p, strategy);
            System.out.println("Placed fleet for " + p.getName() + ": " + placed);
        }

        int size = players[0].getOwnBoard().getSize();
        int safety = 0;
        while (!controller.isOver() && safety++ < 10_000) {
            Player shooter = controller.currentPlayer();
            Position shot = pickUnshotPosition(shooter, size);
            if (shot == null) break;
            ShotResult result = controller.fire(shot);
            System.out.println(shooter.getName() + " -> " + shot + " : " + result);
        }

        Player w = controller.winner();
        System.out.println("Winner: " + (w != null ? w.getName() : "none"));
    }

    private static Position pickUnshotPosition(Player shooter, int size) {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Cell cell = shooter.getTrackingBoard().getCell(new Position(r, c));
                if (!cell.isShot()) return new Position(r, c);
            }
        }
        return null;
    }
}
