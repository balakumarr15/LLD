package Projects.battleship.service.state;

import Projects.battleship.model.GameState;

public class FinishedState implements GameStateHandler {
    @Override
    public GameState getState() { return GameState.FINISHED; }

    @Override
    public void onEnter() {
        System.out.println("[STATE] Game finished.");
    }

    @Override
    public boolean canPlaceShips() { return false; }

    @Override
    public boolean canFireShot() { return false; }

    @Override
    public boolean isTerminal() { return true; }
}
