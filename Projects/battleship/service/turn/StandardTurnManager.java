package Projects.battleship.service.turn;

import Projects.battleship.model.Player;

public class StandardTurnManager implements TurnManager {
    private final Player[] players;
    private int currentPlayerIndex;

    public StandardTurnManager(Player[] players) {
        if (players == null || players.length < 2) {
            throw new IllegalArgumentException("At least two players required");
        }
        this.players = players;
        this.currentPlayerIndex = 0;
    }

    @Override
    public void switchTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    @Override
    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    @Override
    public Player getOpponent(Player player) {
        for (Player p : players) {
            if (!p.equals(player)) return p;
        }
        throw new IllegalStateException("Opponent not found");
    }

    @Override
    public void reset() {
        currentPlayerIndex = 0;
    }
}
