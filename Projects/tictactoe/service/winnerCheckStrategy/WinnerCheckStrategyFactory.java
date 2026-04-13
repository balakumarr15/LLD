package Projects.tictactoe.service.winnerCheckStrategy;

public class WinnerCheckStrategyFactory {
    public static WinnerCheckStrategy get(WinnerCheckStrategyName name, int dimension) {
        switch (name) {
            case ORDER_ONE_STRATEGY:
            default:
                return new OrderOneWinnerCheckStrategy(dimension);
        }
    }
}
