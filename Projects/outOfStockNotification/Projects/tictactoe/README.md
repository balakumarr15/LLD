# Tic Tac Toe — Low Level Design

A scalable, extensible Tic Tac Toe implementation built around classic OOP design patterns: N×N board, Builder-constructed game, O(1) winner check, Bot with difficulty levels, unified `PlayerStrategy` for human/bot, `GameState` pattern, Observer for events, and Factory for player creation.

## How to run

```bash
cd /Users/balakumar/GitHub/LLD
mkdir -p out
javac -d out $(find Projects/tictactoe -name "*.java")
java -cp out Projects.tictactoe.Main
```

Prompts for board dimension (3–10), optional bot (with difficulty), and player names/symbols. Players = `dimension − 1`.

## Folder layout

```
Projects/tictactoe/
├── Main.java                           # entry point / CLI wiring
├── controller/
│   └── GameController.java             # orchestrates moves, state transitions, notifications
├── model/
│   ├── Board.java, Cell.java, CellState.java
│   ├── Position.java, Move.java
│   ├── Player.java, Bot.java, PlayerType.java, BotDifficultyLevel.java
│   ├── Game.java                       # aggregate root + Builder
│   └── GameStatus.java
├── exception/                          # Draw / InvalidMove / InvalidBoardDimension / etc.
├── factory/
│   ├── PlayerFactory.java
│   └── SimplePlayerFactory.java
├── observer/
│   ├── GameEventListener.java
│   └── ConsoleGameEventListener.java
└── service/
    ├── playerStrategy/                 # Human vs Bot move selection (unified interface)
    ├── botPlayingStrategy/             # Easy / Medium / Hard bot algorithms
    ├── state/                          # GameContext + YetToStart/InProgress/Won/Draw
    └── winnerCheckStrategy/            # O(1) winner detection
```

## Design patterns used

### 1. Strategy — Player move selection
`PlayerStrategy.makeMove(Board) -> Position` is implemented by:
- `HumanPlayerStrategy` — reads `row col` from a shared `Scanner` and validates against the board.
- `BotPlayerStrategy` — delegates to an inner `BotPlayingStrategy` chosen by difficulty.

This lets the controller treat every player identically (`player.getPlayerStrategy().makeMove(board)`), regardless of whether it's a human, bot, or future networked player.

### 2. Strategy — Bot difficulty
`BotPlayingStrategy` has three implementations:
- `EasyBotPlayingStrategy` — random empty cell.
- `MediumBotPlayingStrategy` — center first, then first available.
- `HardBotPlayingStrategy` — center → corners → any cell.

Selected via `BotPlayingStrategyFactory.get(BotDifficultyLevel)`.

### 3. Strategy — Winner check
`WinnerCheckStrategy.checkWinner(Board, Move) -> Player` with an O(1) implementation (`OrderOneWinnerCheckStrategy`) that maintains per-row, per-column, and per-diagonal symbol counts. `WinnerCheckStrategyFactory` + `WinnerCheckStrategyName` enum allow swapping to O(N) or O(N²) variants later without touching callers.

### 4. State — Game lifecycle
Instead of a single mutable enum, `GameState` is an interface with concrete states:
`YetToStartState → InProgressState → WonState | DrawState`.
`GameContext` owns the current state, the winner, and exposes `next(player, hasWon, isDraw)` which delegates the transition decision to the current state. The controller never checks `if (status == X)` — it just asks the context to transition.

### 5. Observer — Event notifications
`GameEventListener` has `onMoveMade(Move)` and `onGameStateChanged(GameState, Player)`. `Game` carries a list of listeners; `GameController` notifies them after every move and on every state change. `ConsoleGameEventListener` prints to stdout; a UI or logging listener can be plugged in without changing game logic.

### 6. Factory — Player creation
`PlayerFactory` (interface) / `SimplePlayerFactory` (implementation) hide the wiring between a `Player` and its `PlayerStrategy`. Creating a bot automatically wires in the correct `BotPlayingStrategy` for the requested difficulty.

### 7. Builder — Game construction
`Game.builder()` collects dimension, players, winner-check strategy name, and listeners, then runs validations (`validateBoardDimension`, `validateNumberOfPlayers`, `validateUniqueSymbols`, `validateBotCount`) before constructing the `Board`, `WinnerCheckStrategy`, and `Game` atomically.

### 8. Controller (MVC-ish)
`GameController` is the thin orchestrator: execute move → check winner → check draw → transition state → notify listeners. Keeps `Main` declarative and keeps model classes free of I/O.

## Extensibility (what's easy to change)

| Change | Where |
|---|---|
| Add a network / AI-minimax player | new `PlayerStrategy` impl |
| Tune bot difficulty | new `BotPlayingStrategy` + enum entry |
| Log moves to a file / push to a UI | new `GameEventListener` |
| Swap winner-check algorithm | new `WinnerCheckStrategy` + factory entry |
| Support K-in-a-row instead of N-in-a-row | new `WinnerCheckStrategy` |
| Different validation rules | add a validator in `Game.Builder` |

## Replay / history

Every executed move is appended to `game.getMoves()` and a cloned board snapshot to `game.getBoardHistory()`. `GameController.replayGame(game)` walks snapshots and redisplays the board evolution. Undo is stubbed — it's trivial to implement by popping the last move, restoring the previous snapshot, and decrementing the relevant winner-check counters.

