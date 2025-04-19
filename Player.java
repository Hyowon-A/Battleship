import javax.swing.*;
import java.awt.*;

/**
 * Control each player's ship placement and attack
 * @author Hyowon
 * @version 1.0
 * 13/06/2022
 */

public class Player
{
    private char currentPlayer;
    private Board battleshipBoard;
    public char[][] currentBoard;
    public JButton[][] currentGrid;
    public JTextField currentText;

    private byte holes;
    // private static byte count;
    
    public Player(char currentPlayerIn, Board battleshipIn, char[][] currentBoardIn, JButton[][] currentGridIn, JTextField currentTextIn)
    {
        currentPlayer = currentPlayerIn;
        battleshipBoard = battleshipIn;
        currentBoard = currentBoardIn;
        currentGrid = currentGridIn;
        currentText = currentTextIn;
    }

    public void shipPlacement() {
        Ship ship = new Ship();
        
        System.out.println("Current player is " + currentPlayer + "\n"); // current player 
        System.out.println("Please place each ship by entering x and y coordinates of each ship's head and foot\n"); // how to place the ships
        
        ship.placeShips(currentBoard, currentGrid, currentText, "Carrier"); // place the Carrier
        ship.placeShips(currentBoard, currentGrid, currentText, "Battleship"); // place the Battleship
        ship.placeShips(currentBoard, currentGrid, currentText, "Destroyer"); // place the Destroyer
        ship.placeShips(currentBoard, currentGrid, currentText, "Submarine"); // place the Submarine
        ship.placeShips(currentBoard, currentGrid, currentText, "Patrol Boat"); // place the Patrol Boat

        battleshipBoard.hideShips(currentGrid); // hide ships when all ships are placed

        currentText.setText("All ships are placed");

        battleshipBoard.clearTerminal(); // clear the console
    }
    
    public boolean isShipSunk(char shipChar) // check if the ship is sunk
    {
        switch (shipChar) {
            case 'C': 
                holes = 5;
                break;
            
            case 'B':
                holes = 4;
                break;
                
            case 'D':
                holes = 3;
                break;
                
            case 'S':
                holes = 3;
                break;
                
            case 'P':
                holes = 2;
                break;
        }
        
        byte remainingHoles = holes;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (currentBoard[i][j] == shipChar) // if there are holes (buttons) of particular ship on board
                {
                    remainingHoles--;
                }
            }
        }
        
        if (holes - remainingHoles == 0) // if there are no holes (buttons) of particular ship on board; sunk
        {
            return true;
        } else // if there are still holes (buttons) of particular ship on board; not sunk
        {
            return false;
        }
    }
    
    public void attack(char attackIn, byte row, byte col) // attack ships on opponent's board
    {
        Color colour;
        String message;
        switch (attackIn) {
            case 'C': 
                colour = Color.GREEN;
                currentBoard[row][col] = 'H'; // the sign for a hit ship 
                if (isShipSunk('C')) // if the Carrier is sunk
                {
                    message = "The Carrier on opponent's board is sunk";
                } else {
                    message = "You hit the Carrier";
                }
                break;
            case 'B':
                colour = Color.BLUE;
                currentBoard[row][col] = 'H'; // the sign for a hit ship
                if (isShipSunk('B')) // if the Battleship is sunk
                {
                    message = "The Battleship on opponent's board is sunk";
                } else {
                    message = "You hit the Battleship";
                }
                break;
            case 'D':
                colour = Color.RED;
                currentBoard[row][col] = 'H'; // the sign for a hit ship
                if (isShipSunk('D')) // if the Destroyer is sunk
                {
                    message = "The Destroyer on opponent's board is sunk";
                } else {
                    message = "You hit the Destroyer";
                }
                break;
            case 'S':
                colour = Color.YELLOW;
                currentBoard[row][col] = 'H'; // the sign for a hit ship
                if (isShipSunk('S')) // if the Submarine is sunk
                {
                    message = "The Submarine on opponent's board is sunk";
                } else {
                    message = "You hit the Submarine";
                }
                break;
            case 'P':
                colour = Color.ORANGE;
                currentBoard[row][col] = 'H'; // the sign for a hit ship
                if (isShipSunk('P')) // the Patrol Boat is sunk
                {
                    message = "The Patrol Boat on opponent's board is sunk";
                } else {
                    message = "You hit the Patrol Boat";
                }
                break;
                
            default: // when player missed the ship
                colour = Color.WHITE;
                currentGrid[row][col].setText("Miss");
                message = "You missed the ships";
                break;
        } 

        currentGrid[row][col].setEnabled(false); // not clickable
        currentGrid[row][col].setBackground(colour);
        currentGrid[row][col].setOpaque(true);
        currentGrid[row][col].setBorderPainted(false);
        
        JOptionPane.showMessageDialog(null, message); // show message of hit, miss, or sink
    }
    
    public boolean areAllShipsSunk() // check if all ships are sunk
    {
        if (isShipSunk('C') && isShipSunk('B') && isShipSunk('D') && isShipSunk('S') && isShipSunk('P')) {
            // if there are no holes of any ship on board
            return true;
        } else {
            // if there are remaining holes of any ship on board
            return false;
        }
    }
}

