package Projects.tictactoe.model;

public class Cell {
    private final int row;
    private final int col;
    private CellState cellState;
    private Player player;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cellState = CellState.EMPTY;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public CellState getCellState() { return cellState; }
    public void setCellState(CellState cellState) { this.cellState = cellState; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
}
