import java.awt.Color;
import javax.swing.JButton;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

/**
 * Handle the logic of placing different ships
 * @author Hyowon
 * @version 1.0
 * 13/06/2022
 */

public class Ship {
    private char startRowChar, endRowChar; // character inputs for row (y-coordinates)
    private byte startRow, startCol, endRow, endCol; // number values for coordinates
    private byte holes; // the number of holes (buttons) of each ship
    private char shipChar; // first character of each ship
    private Color colour; // colour of each ship
    private byte overlap = 0; 

    private byte boardSize = 11;

    public void placeShips(char[][] currentBoard, JButton[][] currentGrid, JTextField currentText, String shipName) // place all ships with this method
    {
        boolean stop = false;
        switch (shipName) {
            case "Carrier": 
                holes = 5;
                colour = Color.GREEN;
                shipChar = shipName.charAt(0);
                break;

            case "Battleship":
                holes = 4;
                colour = Color.BLUE;
                shipChar = shipName.charAt(0);
                break;

            case "Destroyer":
                holes = 3;
                colour = Color.RED;
                shipChar = shipName.charAt(0);
                break;

            case "Submarine":
                holes = 3;
                colour = Color.YELLOW;
                shipChar = shipName.charAt(0);
                break;

            case "Patrol Boat":
                holes = 2;
                colour = Color.ORANGE;
                shipChar = shipName.charAt(0);
                break;
        }

        Scanner sc = new Scanner(System.in); // for taking user inputs from console
        while (!stop) {
            System.out.println(shipName + " contains " + holes + " holes"); 

            System.out.print("Please enter x-coordinate of starting point of " + shipName + ": "); // x-coordinate of ship's head
            startCol = sc.nextByte();

            System.out.print("Please enter y-coordinate of starting point of " + shipName + ": "); // y-coordinate of ship's head
            startRowChar = Character.toUpperCase(sc.next().charAt(0)); // convert to upper case 
            startRow = (byte) (startRowChar - 64); // convert to byte with ASCII code

            System.out.print("Please enter x-coordinate of ending point of " + shipName + ": "); // x-coordinate of ship's foot
            endCol = sc.nextByte();

            System.out.print("Please enter y-coordinate of ending point of " + shipName + ": "); // y-coordinate of ship's foot
            endRowChar = Character.toUpperCase(sc.next().charAt(0)); // convert to upper case 
            endRow = (byte) (endRowChar - 64); // convert to byte with ASCII code
            
            if ((startCol > 0 && startCol < 11) && (startRow > 0 && startRow < 11) && (endCol > 0 && endCol < 11) && (endRow > 0 && endRow < 11)) {
                // if all coordinates are in the range of board size
                if (endCol < startCol || endRow < startRow) // if starting x or y is bigger than ending x or y
                {
                    byte temp;
                    // bubble sort
                    temp = startCol;
                    startCol = endCol;
                    endCol = temp;

                    temp = startRow;
                    startRow = endRow;
                    endRow = temp;
                }

                if ((holes == (endCol - startCol + 1) && endRow - startRow == 0) || (holes == (endRow - startRow + 1) && endCol - startCol == 0)) {
                    // if entered coordinates are either horizontal or vertical with correct number of holes
                    
                    for (int i = startRow; i <= endRow; i++) {
                        for (int j = startCol; j <= endCol; j++) {
                            // check there are overlapped ships between the entered coordinates
                            char currentCoord = currentBoard[i][j];
                            if (currentCoord != ' ') // if the current coordinate is ship
                            {
                                overlap++;
                            }
                        }
                    }

                    if (overlap != 0) // if ships are overlapped
                    {
                        System.out.println("Ships cannot be overlapped\n");
                        overlap = 0;
                        stop = false;
                    } else // if ships are not overlapped
                    {
                        for (int i = startRow; i <= endRow; i++) {
                            for (int j = startCol; j <= endCol; j++) {
                                currentBoard[i][j] = shipChar; // set the sign of each ship
                                currentGrid[i][j].setBackground(colour); // set background colour of each ship
                                currentGrid[i][j].setOpaque(true);
                                currentGrid[i][j].setBorderPainted(false);
                                stop = true; // stop looping 
                            }
                        }
                    }
                } else // if the number of holes is not correct 
                {
                    System.out.println("Please check the number of holes\n");
                    stop = false;
                }
            } else // if one or more coordinates are not in the range.
            {
                System.out.println("Input coordinates are out of range.");
                System.out.println("Please enter only '1 to 10' for x and 'A to J' for y\n");
            }
        }
        System.out.println("");
    }
}
    
