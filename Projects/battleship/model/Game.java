package Projects.battleship.model;

import java.util.ArrayList;
import java.util.List;

import Projects.battleship.exception.GameStateException;
import Projects.battleship.exception.InvalidShipPlacementException;
import Projects.battleship.exception.InvalidShotException;
import Projects.battleship.observer.GameEventListener;
import Projects.battleship.service.placement.ShipPlacementStrategy;
import Projects.battleship.service.rules.GameRules;
import Projects.battleship.service.state.FinishedState;
import Projects.battleship.service.state.GameStateHandler;
import Projects.battleship.service.state.InProgressState;
import Projects.battleship.service.state.SetupState;
import Projects.battleship.service.turn.TurnManager;

public class Game {
    private final Player[] players;
    private final GameRules rules;
    private final TurnManager turnManager;
    private final List<Shot> shotHistory;
    private final List<GameEventListener> listeners;

    private GameStateHandler stateHandler;
    private Player winner;

    public Game(Player[] players, GameRules rules, TurnManager turnManager) {
        if (players == null || players.length != 2) {
            throw new IllegalArgumentException("Battleship requires exactly two players");
        }
        this.players = players;
        this.rules = rules;
        this.turnManager = turnManager;
        this.shotHistory = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.stateHandler = new SetupState();
    }

    public void addListener(GameEventListener listener) {
        listeners.add(listener);
    }

    public void startNewGame() {
        this.stateHandler = new SetupState();
        stateHandler.onEnter();
        this.winner = null;
        this.shotHistory.clear();
        this.turnManager.reset();
    }

    public boolean placeShips(Player player, ShipPlacementStrategy strategy, List<Ship> ships) {
        if (!stateHandler.canPlaceShips()) {
            throw new GameStateException("Cannot place ships in state " + stateHandler.getState());
        }
        for (Ship ship : ships) {
            if (ship == null) throw new InvalidShipPlacementException("Null ship in fleet");
        }
        boolean ok = strategy.placeShips(player, ships);
        if (!ok) return false;
        if (allPlayersReady()) {
            transitionTo(new InProgressState());
        }
        return true;
    }

    public ShotResult fireShot(Position position) {
        if (!stateHandler.canFireShot()) {
            throw new GameStateException("Cannot fire shot in state " + stateHandler.getState());
        }
        Player shooter = turnManager.getCurrentPlayer();
        Player opponent = turnManager.getOpponent(shooter);
        if (!validateShot(shooter, opponent, position)) {
            throw new InvalidShotException("Invalid shot at " + position);
        }
        ShotResult result = shooter.fireShot(position, opponent);
        Shot shot = new Shot(shooter, position, result);
        shotHistory.add(shot);
        notifyShotFired(shot);
        if (result == ShotResult.SUNK) {
            Ship sunk = opponent.getOwnBoard().getCell(position).getShip();
            notifyShipSunk(opponent, sunk);
        }
        if (rules.isGameOver(players)) {
            this.winner = rules.determineWinner(players);
            transitionTo(new FinishedState());
            notifyGameOver(winner);
        } else if (result == ShotResult.MISS) {
            turnManager.switchTurn();
        }
        return result;
    }

    private boolean validateShot(Player shooter, Player opponent, Position position) {
        return rules.validateShot(shooter, opponent, position);
    }

    public Player checkWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return stateHandler.isTerminal();
    }

    public Player getOpponent(Player player) {
        return turnManager.getOpponent(player);
    }

    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }

    public void resetGame() {
        for (Player p : players) {
            int size = p.getOwnBoard().getSize();
            clearBoards(p, size);
        }
        startNewGame();
    }

    public GameState getGameState() {
        return stateHandler.getState();
    }

    public List<Shot> getShotHistory() {
        return new ArrayList<>(shotHistory);
    }

    public Player[] getPlayers() {
        return players.clone();
    }

    private boolean allPlayersReady() {
        for (Player p : players) {
            if (p.getOwnBoard().getShips().isEmpty()) return false;
        }
        return true;
    }

    private void transitionTo(GameStateHandler next) {
        this.stateHandler = next;
        next.onEnter();
    }

    private void clearBoards(Player player, int size) {
        java.lang.reflect.Field field;
        try {
            Board newOwn = new Board(size);
            Board newTracking = new Board(size);
            field = Player.class.getDeclaredField("ownBoard");
            field.setAccessible(true);
            field.set(player, newOwn);
            field = Player.class.getDeclaredField("trackingBoard");
            field.setAccessible(true);
            field.set(player, newTracking);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    private void notifyShotFired(Shot shot) {
        for (GameEventListener l : listeners) l.onShotFired(shot);
    }

    private void notifyShipSunk(Player owner, Ship ship) {
        for (GameEventListener l : listeners) l.onShipSunk(owner, ship);
    }

    private void notifyGameOver(Player winner) {
        for (GameEventListener l : listeners) l.onGameOver(winner);
    }
}
