package Projects.tictactoe.service.winnerCheckStrategy;

import Projects.tictactoe.model.Board;
import Projects.tictactoe.model.Move;
import Projects.tictactoe.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOneWinnerCheckStrategy implements WinnerCheckStrategy {
    private final int dimension;
    private final List<Map<Character, Integer>> rowCounts;
    private final List<Map<Character, Integer>> colCounts;
    private final Map<Character, Integer> leftDiagonal = new HashMap<>();
    private final Map<Character, Integer> rightDiagonal = new HashMap<>();

    public OrderOneWinnerCheckStrategy(int dimension) {
        this.dimension = dimension;
        rowCounts = new ArrayList<>();
        colCounts = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            rowCounts.add(new HashMap<>());
            colCounts.add(new HashMap<>());
        }
    }

    @Override
    public Player checkWinner(Board board, Move lastPlayedMove) {
        Player player = lastPlayedMove.getPlayer();
        char symbol = player.getSymbol();
        int row = lastPlayedMove.getCell().getRow();
        int col = lastPlayedMove.getCell().getCol();

        if (increment(rowCounts.get(row), symbol) == dimension) return player;
        if (increment(colCounts.get(col), symbol) == dimension) return player;
        if (row == col && increment(leftDiagonal, symbol) == dimension) return player;
        if ((row + col) == dimension - 1 && increment(rightDiagonal, symbol) == dimension) return player;
        return null;
    }

    private int increment(Map<Character, Integer> map, char symbol) {
        int count = map.getOrDefault(symbol, 0) + 1;
        map.put(symbol, count);
        return count;
    }
}
