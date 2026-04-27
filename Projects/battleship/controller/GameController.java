package Projects.battleship.controller;

import java.util.List;

import Projects.battleship.factory.ShipFactory;
import Projects.battleship.model.Game;
import Projects.battleship.model.Player;
import Projects.battleship.model.Position;
import Projects.battleship.model.Ship;
import Projects.battleship.model.ShotResult;
import Projects.battleship.observer.GameEventListener;
import Projects.battleship.service.placement.ShipPlacementStrategy;
import Projects.battleship.service.rules.GameRules;
import Projects.battleship.service.rules.StandardGameRules;
import Projects.battleship.service.turn.StandardTurnManager;
import Projects.battleship.service.turn.TurnManager;

public class GameController {
    private final Game game;
    private final ShipFactory shipFactory;

    public GameController(String p1Name, String p2Name) {
        this(p1Name, p2Name, new StandardGameRules());
    }

    public GameController(String p1Name, String p2Name, GameRules rules) {
        Player p1 = new Player("P1", p1Name, rules.getBoardSize());
        Player p2 = new Player("P2", p2Name, rules.getBoardSize());
        TurnManager turnManager = new StandardTurnManager(new Player[]{p1, p2});
        this.game = new Game(new Player[]{p1, p2}, rules, turnManager);
        this.shipFactory = new ShipFactory();
    }

    public void registerListener(GameEventListener listener) {
        game.addListener(listener);
    }

    public void start() {
        game.startNewGame();
    }

    public boolean setupPlayer(Player player, ShipPlacementStrategy strategy) {
        List<Ship> fleet = shipFactory.createStandardFleet();
        return game.placeShips(player, strategy, fleet);
    }

    public boolean setupPlayer(Player player, ShipPlacementStrategy strategy, List<Ship> fleet) {
        return game.placeShips(player, strategy, fleet);
    }

    public ShotResult fire(Position position) {
        return game.fireShot(position);
    }

    public Player currentPlayer() {
        return game.getCurrentPlayer();
    }

    public Player winner() {
        return game.checkWinner();
    }

    public boolean isOver() {
        return game.isGameOver();
    }

    public Game game() {
        return game;
    }

    public Player[] players() {
        return game.getPlayers();
    }
}
