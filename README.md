# 🛳️ Battleship Game

This is a simple implementation of the classic **Battleship** game written in **Java**.

## 📁 Project Structure

- `Battleship.java`: Main class that runs the game.
- `Board.java`: Handles game board logic and GUI interface.
- `Ship.java`: Manages ship placement and validation.
- `Player.java`: Represents a player and their actions.

## 🚀 Getting Started

### Prerequisites

- Java JDK (v8 or above)

### Running the Game

1. Compile all `.java` files using your preferred IDE or via terminal:
   ```bash
   javac *.java
   ```
2. Run the game:
   ```bash
   java Battleship
   ```

## 🎮 Gameplay Overview

- The game is a turn-based two-player mode using GUI.
- Players place ships on their own 10x10 grid (A–J and 1–10).
- Ship types follow the 2002 Hasbro version:
  - Carrier (5)
  - Battleship (4)
  - Destroyer (3)
  - Submarine (3)
  - Patrol Boat (2)
- Ships must be placed either vertically or horizontally without overlap.
- Players attack each other's grid; feedback includes "Hit", "Miss", or "Sunk".
- The game ends when all ships of a player are sunk or a draw is declared.

## 🖥️ Features

- GUI-based dual grid display.
- Color-coded ship identification.
- Pop-up messages for hits, misses, and sink status.
- Dynamic messages indicating whose turn it is.
- Automatic validation for ship placement (range, overlap, correct length).
- Optionally reveals all ships after game ends.
- Fully interactive turn system.

## ✅ Testing

The game includes thorough testing, including:
- Validation for ship placement (correct size, no overlaps).
- Turn-based GUI updates.
- Winner and draw condition checks.
- Error handling for invalid inputs (e.g., out of range).

## 🧪 Known Limitations

- Requires manual coordinate input for placing ships (head and foot).
- Game can crash on incorrect input formats (e.g., switching x/y order).

## 🔧 Future Improvements

- Convert entire ship placement to GUI (drag/click based).
- Add images for ships, water, and effects to enhance visuals.
- Improve input flexibility (e.g., allow entering x/y in any order).

## 📊 Comparison with Other Projects

- Supports two-player local mode (unlike some single-player-only versions).
- Clearer visual turn indicators and real-time grid interaction.
- Could benefit from more visual polish and user-friendly ship placement.

