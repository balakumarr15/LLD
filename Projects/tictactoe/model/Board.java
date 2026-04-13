package Projects.tictactoe.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<List<Cell>> matrix;
    private final int dimension;

    public Board(int dimension) {
        this.dimension = dimension;
        this.matrix = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < dimension; j++) {
                matrix.get(i).add(new Cell(i, j));
            }
        }
    }

    public int getDimension() { return dimension; }
    public List<List<Cell>> getMatrix() { return matrix; }

    public Cell getCell(int row, int col) {
        return matrix.get(row).get(col);
    }

    public boolean isValidMove(Position pos) {
        int r = pos.getRow(), c = pos.getCol();
        if (r < 0 || r >= dimension || c < 0 || c >= dimension) return false;
        return matrix.get(r).get(c).getCellState() == CellState.EMPTY;
    }

    public void display() {
        for (List<Cell> row : matrix) {
            for (Cell cell : row) {
                if (cell.getCellState() == CellState.EMPTY) {
                    System.out.print("|   ");
                } else {
                    System.out.print("| " + cell.getPlayer().getSymbol() + " ");
                }
            }
            System.out.println("|");
        }
        System.out.println();
    }

    public Board cloneBoard() {
        Board copy = new Board(dimension);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Cell src = matrix.get(i).get(j);
                Cell dst = copy.matrix.get(i).get(j);
                dst.setCellState(src.getCellState());
                dst.setPlayer(src.getPlayer());
            }
        }
        return copy;
    }
}
