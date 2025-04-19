import javax.swing.*;
import java.awt.event.*;

/**
 * Control whole process of the game
 * @author Hyowon
 * @version 1.0
 * 13/06/2022
 */

public class Battleship
{
    private static char currentPlayer = 'A'; // player A

    private static final byte boardSize = 11;

    private static Player playerA;
    private static Player playerB;

    private static boolean gameOverA = false; // change to 'true' when the player A attacks every ship of player B
    private static boolean gameOverB = false; // change to 'true' when the player B attacks every ship of player A
    private static boolean gameOver = false;; // change to 'true' when one or both player attacked every ships of opponent
    private static String message;
    public static void main (String[] args) {
        Board battleship = new Board(); // generate the game board
        battleship.clearTerminal(); // clear the console
        battleship.print(); // print the board on GUI
        boolean stop = false;
        while (!stop) {
            if (currentPlayer == 'A') {
                playerA = new Player('A', battleship, battleship.boardA, battleship.gridA, battleship.textA); // set player A for placing ships
                playerA.shipPlacement(); // player A places ships
                currentPlayer = 'B'; // give control to player B
            } else {
                playerB = new Player('B', battleship, battleship.boardB, battleship.gridB, battleship.textB); // set player B for placing ships
                playerB.shipPlacement(); // player B places ships
                currentPlayer = 'A'; // give control to player A
                battleship.textA.setText("Attack ships on board B"); // set the message on grid of player to attack (player A)
                stop = true; // stop looping
            }
        }
        System.out.println("Play on GUI!!"); // print the instruction 

        ActionListener actionListener = new ActionListener() {
                public void actionPerformed (ActionEvent e) {
                    for (int i = 1; i < boardSize; i++) {
                        for (int j = 1; j < boardSize; j++) {
                            if (currentPlayer == 'A') {
                                playerA = new Player('A', battleship, battleship.boardB, battleship.gridB, battleship.textA); // set player A for attacking
                                battleship.textA.setText("Attack ships on board B"); // set the message on grid of player to attack (player A)
                                if (e.getSource() == playerA.currentGrid[i][j]) {

                                    char attack = playerA.currentBoard[i][j]; // take the character from the attacked button

                                    playerA.attack(attack, (byte) i, (byte) j); // make an attack

                                    currentPlayer = 'B'; // give control to player B

                                    playerA.currentText.setText(""); 

                                    if (playerA.areAllShipsSunk()) // if all ships of player B are sunk 
                                    {
                                        gameOverA = true;
                                    }
                                }
                            } else {
                                playerB = new Player('B', battleship, battleship.boardA, battleship.gridA, battleship.textB); // set player B for attacking
                                playerB.currentText.setText("Attack ships on board A"); // set the message on grid of player to attack (player B)
                                if (e.getSource() == playerB.currentGrid[i][j]) {
                                    char attack = playerB.currentBoard[i][j]; // take the character from the attacked button

                                    playerB.attack(attack, (byte) i, (byte) j); // make an attack

                                    playerB.currentText.setText("");

                                    if (playerB.areAllShipsSunk()) // if player B attacked all ships on board A
                                    {
                                        gameOverB = true;
                                        if (gameOverA == true && gameOverB == true) // when both players attacked all ships on both boards
                                        {
                                            gameOver = true;        
                                            message = "The game is a draw";
                                            battleship.textA.setText(message);
                                            battleship.textB.setText(message);
                                        } else if (gameOverB == true) // when only the player B attacked all ships on board A
                                        {
                                            gameOver = true; 
                                            message = "The player B is a winner ";
                                            battleship.textB.setText(message);
                                            battleship.remainingShipsOfB(); // display the player B's ships have not been attacked
                                        }
                                    }  else if (gameOverA == true) // when only the player A attacked all ships on board B
                                    {
                                        gameOver = true; 
                                        message = "The player A is a winner";
                                        battleship.textA.setText(message);
                                        battleship.remainingShipsOfA(); // display the player A's ships have not been attacked
                                    } else // if not all ships on each board have been attacked
                                    {
                                        currentPlayer = 'A'; // give control to player A
                                    }
                                }
                            }
                            if (gameOver == true) {
                                for (i = 0; i < boardSize; i++) {
                                    for (j = 0; j < boardSize; j++) {
                                        // stop taking actions from buttons
                                        battleship.gridA[i][j].removeActionListener(this);
                                        battleship.gridB[i][j].removeActionListener(this);
                                    }
                                }
                                battleship.disableButtons(battleship.gridA, battleship.gridB); // make buttons not clickable
                                JOptionPane.showMessageDialog(null, message); // show the message of winner
                            }
                        }
                    }
                }
            };
        for (int i = 1; i < boardSize; i++) {
            for (int j = 1; j < boardSize; j++) {
                // start taking actions from buttons
                battleship.gridA[i][j].addActionListener(actionListener);
                battleship.gridB[i][j].addActionListener(actionListener);
            }
        }
    }
}