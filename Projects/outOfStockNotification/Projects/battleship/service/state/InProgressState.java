package Projects.battleship.service.state;

import Projects.battleship.model.GameState;

public class InProgressState implements GameStateHandler {
    @Override
    public GameState getState() { return GameState.IN_PROGRESS; }

    @Override
    public void onEnter() {
        System.out.println("[STATE] Game in progress - fire away!");
    }

    @Override
    public boolean canPlaceShips() { return false; }

    @Override
    public boolean canFireShot() { return true; }

    @Override
    public boolean isTerminal() { return false; }
}
