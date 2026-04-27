package Projects.battleship.service.turn;

import Projects.battleship.model.Player;

public interface TurnManager {
    void switchTurn();

    Player getCurrentPlayer();

    Player getOpponent(Player player);

    void reset();
}
