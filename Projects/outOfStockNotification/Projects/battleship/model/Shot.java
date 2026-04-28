package Projects.battleship.model;

public class Shot {
    private final Player player;
    private final Position position;
    private final ShotResult result;

    public Shot(Player player, Position position, ShotResult result) {
        this.player = player;
        this.position = position;
        this.result = result;
    }

    public Player getPlayer() { return player; }

    public Position getPosition() { return position; }

    public ShotResult getResult() { return result; }

    @Override
    public String toString() {
        return player.getName() + " fired at " + position + " -> " + result;
    }
}
