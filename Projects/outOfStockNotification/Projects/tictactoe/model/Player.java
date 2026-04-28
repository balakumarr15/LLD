package Projects.tictactoe.model;

import Projects.tictactoe.service.playerStrategy.PlayerStrategy;

public class Player {
    private final int id;
    private final String name;
    private final char symbol;
    private final PlayerType playerType;
    private final PlayerStrategy playerStrategy;

    public Player(int id, String name, char symbol, PlayerType playerType, PlayerStrategy playerStrategy) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
        this.playerStrategy = playerStrategy;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public char getSymbol() { return symbol; }
    public PlayerType getPlayerType() { return playerType; }
    public PlayerStrategy getPlayerStrategy() { return playerStrategy; }
}
