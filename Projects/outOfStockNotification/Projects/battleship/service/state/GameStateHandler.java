package Projects.battleship.service.state;

import Projects.battleship.model.GameState;

public interface GameStateHandler {
    GameState getState();

    void onEnter();

    boolean canPlaceShips();

    boolean canFireShot();

    boolean isTerminal();
}
