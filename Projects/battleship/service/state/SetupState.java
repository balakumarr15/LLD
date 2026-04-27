package Projects.battleship.service.state;

import Projects.battleship.model.GameState;

public class SetupState implements GameStateHandler {
    @Override
    public GameState getState() { return GameState.SETUP; }

    @Override
    public void onEnter() {
        System.out.println("[STATE] Setup phase - place your ships.");
    }

    @Override
    public boolean canPlaceShips() { return true; }

    @Override
    public boolean canFireShot() { return false; }

    @Override
    public boolean isTerminal() { return false; }
}
