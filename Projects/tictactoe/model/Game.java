package Projects.tictactoe.model;

import Projects.tictactoe.exception.DuplicatePlayerSymbolException;
import Projects.tictactoe.exception.InvalidBoardDimensionException;
import Projects.tictactoe.exception.InvalidBotCountException;
import Projects.tictactoe.exception.InvalidNumberOfPlayersException;
import Projects.tictactoe.observer.GameEventListener;
import Projects.tictactoe.service.state.GameContext;
import Projects.tictactoe.service.winnerCheckStrategy.WinnerCheckStrategy;
import Projects.tictactoe.service.winnerCheckStrategy.WinnerCheckStrategyFactory;
import Projects.tictactoe.service.winnerCheckStrategy.WinnerCheckStrategyName;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final List<Move> moves;
    private final List<Board> boardHistory;
    private final GameContext gameContext;
    private final WinnerCheckStrategy winnerCheckStrategy;
    private final List<GameEventListener> listeners;
    private int currentPlayerIndex;

    private Game(Board board, List<Player> players,
                 WinnerCheckStrategy winnerCheckStrategy,
                 List<GameEventListener> listeners) {
        this.board = board;
        this.players = players;
        this.winnerCheckStrategy = winnerCheckStrategy;
        this.listeners = listeners;
        this.moves = new ArrayList<>();
        this.boardHistory = new ArrayList<>();
        this.gameContext = new GameContext();
        this.currentPlayerIndex = 0;
    }

    public static Builder builder() { return new Builder(); }

    public Board getBoard() { return board; }
    public List<Player> getPlayers() { return players; }
    public List<Move> getMoves() { return moves; }
    public List<Board> getBoardHistory() { return boardHistory; }
    public GameContext getGameContext() { return gameContext; }
    public WinnerCheckStrategy getWinnerCheckStrategy() { return winnerCheckStrategy; }
    public List<GameEventListener> getListeners() { return listeners; }

    public Player getCurrentPlayer() { return players.get(currentPlayerIndex); }
    public void advancePlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public static class Builder {
        private int dimension;
        private List<Player> players;
        private WinnerCheckStrategyName strategyName = WinnerCheckStrategyName.ORDER_ONE_STRATEGY;
        private final List<GameEventListener> listeners = new ArrayList<>();

        public Builder setDimension(int dimension) { this.dimension = dimension; return this; }
        public Builder setPlayers(List<Player> players) { this.players = players; return this; }
        public Builder setWinnerCheckStrategy(WinnerCheckStrategyName name) { this.strategyName = name; return this; }
        public Builder addListener(GameEventListener listener) { this.listeners.add(listener); return this; }

        private void validateBoardDimension() {
            if (dimension < 3 || dimension > 10) {
                throw new InvalidBoardDimensionException("Board dimension must be between 3 and 10");
            }
        }

        private void validateNumberOfPlayers() {
            if (players == null || players.size() != dimension - 1) {
                throw new InvalidNumberOfPlayersException(
                        "Players count must be N-1 (got " + (players == null ? 0 : players.size())
                                + ", expected " + (dimension - 1) + ")");
            }
        }

        private void validateUniqueSymbols() {
            Set<Character> seen = new HashSet<>();
            for (Player p : players) {
                if (!seen.add(p.getSymbol())) {
                    throw new DuplicatePlayerSymbolException("Duplicate symbol: " + p.getSymbol());
                }
            }
        }

        private void validateBotCount() {
            long bots = players.stream().filter(p -> p.getPlayerType() == PlayerType.BOT).count();
            if (bots > 1) {
                throw new InvalidBotCountException("At most 1 bot is allowed, found " + bots);
            }
        }

        private void validate() {
            validateBoardDimension();
            validateNumberOfPlayers();
            validateUniqueSymbols();
            validateBotCount();
        }

        public Game build() {
            validate();
            Board board = new Board(dimension);
            WinnerCheckStrategy strategy = WinnerCheckStrategyFactory.get(strategyName, dimension);
            return new Game(board, players, strategy, listeners);
        }
    }
}
