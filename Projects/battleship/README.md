# Battleship - LLD

Low-level design implementation of classic Battleship in Java.

## Package layout

```
Projects/battleship/
├── Main.java                            demo runner
├── controller/GameController.java       thin facade over Game
├── model/                               core entities
│   ├── Game.java                        orchestrates flow (delegates rules/turn/state)
│   ├── Player.java                      owns two boards
│   ├── Board.java                       concrete board (extends AbstractBoard)
│   ├── Ship.java                        implements Cloneable (Prototype)
│   ├── Cell.java                        holds ship ref + shot flag
│   ├── Position.java                    value object
│   ├── Shot.java                        immutable shot record
│   └── enums: ShipType, ShotResult, Direction, GameState
├── service/
│   ├── board/                           BoardInterface, AbstractBoard, BoardValidator
│   ├── rules/                           GameRules, StandardGameRules
│   ├── turn/                            TurnManager, StandardTurnManager
│   ├── state/                           GameStateHandler + Setup/InProgress/Finished
│   └── placement/                       ShipPlacementStrategy, Random, Manual
├── factory/ShipFactory.java             Factory + Prototype entry point
├── observer/                            GameEventListener, ConsoleGameEventListener
└── exception/                           domain exceptions
```

## Design patterns

| Pattern | Location |
| --- | --- |
| Strategy | `service/placement/ShipPlacementStrategy` + Random / Manual |
| Factory | `factory/ShipFactory.createShip(ShipType)` |
| Prototype | `Ship.clone()` (also `ShipFactory.cloneShip`) |
| State | `service/state/GameStateHandler` + Setup / InProgress / Finished |
| Observer | `observer/GameEventListener` (shot fired, ship sunk, game over) |

## Evaluator feedback addressed

- **SRP on Game**: turn logic extracted into `TurnManager`, validation into `BoardValidator`,
  rules into `GameRules`. `Game` only orchestrates.
- **Rules engine**: `GameRules` interface with `StandardGameRules`, injected into `Game`.
  New modes = new implementation, no change to `Game`.
- **Interface + abstract class**: `BoardInterface` defines the contract, `AbstractBoard`
  holds shared logic (validation, cell access), concrete `Board` implements `placeShip` /
  `receiveShot`.
- **Ship/Cell relationship**: `Ship` stores `List<Position>` (not `List<Cell>`), `Cell`
  holds `Ship` reference and is responsible for keeping both sides in sync via `setShip`.
- **Validator**: `BoardValidator` centralizes boundary/overlap/shot-already-fired checks.

## Run

```bash
javac Projects/battleship/Main.java
java Projects.battleship.Main
```

## Flow

1. `SETUP`: each player places ships via a `ShipPlacementStrategy`. Once all fleets are
   placed, the game transitions to `IN_PROGRESS`.
2. `IN_PROGRESS`: current player fires; `HIT` / `SUNK` keeps the turn, `MISS` switches.
3. `FINISHED`: reached when `GameRules.isGameOver` is true; `determineWinner` picks the
   survivor and listeners are notified.
