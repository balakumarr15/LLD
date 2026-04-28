package Projects.tictactoe.controller;

import Projects.tictactoe.exception.InvalidMoveException;
import Projects.tictactoe.model.Board;
import Projects.tictactoe.model.Cell;
import Projects.tictactoe.model.CellState;
import Projects.tictactoe.model.Game;
import Projects.tictactoe.model.GameStatus;
import Projects.tictactoe.model.Move;
import Projects.tictactoe.model.Player;
import Projects.tictactoe.model.Position;
import Projects.tictactoe.observer.GameEventListener;
import Projects.tictactoe.service.state.GameContext;

public class GameController {

    public Move executeMove(Game game, Player player) {
        Board board = game.getBoard();
        Position position = player.getPlayerStrategy().makeMove(board);
        if (!board.isValidMove(position)) {
            throw new InvalidMoveException("Invalid move at " + position);
        }
        Cell cell = board.getCell(position.getRow(), position.getCol());
        cell.setCellState(CellState.FILLED);
        cell.setPlayer(player);
        Move move = new Move(player, cell);
        game.getMoves().add(move);
        game.getBoardHistory().add(board.cloneBoard());
        notifyMoveMade(game, move);
        return move;
    }

    public Player checkWinner(Game game, Move move) {
        return game.getWinnerCheckStrategy().checkWinner(game.getBoard(), move);
    }

    public boolean isDraw(Game game) {
        int dim = game.getBoard().getDimension();
        return game.getMoves().size() >= dim * dim;
    }

    public void transitionState(Game game, Player player, boolean hasWon, boolean isDraw) {
        GameContext ctx = game.getGameContext();
        GameStatus before = ctx.getStatus();
        ctx.next(player, hasWon, isDraw);
        GameStatus after = ctx.getStatus();
        if (before != after) {
            notifyStateChanged(game);
        }
    }

    public void displayBoard(Game game) {
        game.getBoard().display();
    }

    public GameStatus getGameStatus(Game game) {
        return game.getGameContext().getStatus();
    }

    public void replayGame(Game game) {
        System.out.println("=== Replay ===");
        for (Board snapshot : game.getBoardHistory()) {
            snapshot.display();
        }
    }

    private void notifyMoveMade(Game game, Move move) {
        for (GameEventListener l : game.getListeners()) l.onMoveMade(move);
    }

    private void notifyStateChanged(Game game) {
        GameContext ctx = game.getGameContext();
        for (GameEventListener l : game.getListeners()) {
            l.onGameStateChanged(ctx.getCurrentState(), ctx.getWinner());
        }
    }
}
