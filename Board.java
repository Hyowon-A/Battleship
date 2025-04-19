import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;

/**
 * Initialise the game board
 * @author Hyowon
 * @version 1.0
 * 13/06/2022
 */

public class Board extends JFrame
{
    private final byte boardSize = 11; 
    private JPanel panelA = new JPanel(); // panel for player A
    private JPanel panelB = new JPanel(); // panel for player B

    public JButton[][] gridA; // grid for player A
    public JButton[][] gridB; // grid for player B
    public char[][] boardA; // char board for player A
    public char[][] boardB; // char board for player B

    private JLabel labelA = new JLabel("Board A for player A");
    private JLabel labelB = new JLabel("Board B for player B");

    public JTextField textA = new JTextField(" ");
    public JTextField textB = new JTextField(" ");

    public Board() {
        super("Battleship");
        setSize(boardSize * 160, boardSize * 70); // size will be decided by the board size

        panelA.setLayout(new GridBagLayout()); 
        boardA = new char[boardSize][boardSize];
        gridA = new JButton[boardSize][boardSize];

        panelB.setLayout(new GridBagLayout());
        boardB = new char[boardSize][boardSize];
        gridB = new JButton[boardSize][boardSize];
        initialiseBoard();
    }

    private void initialiseBoard() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                boardA[row][col] = ' ';
                boardB[row][col] = ' ';

                if (row == 0 && col > 0) // setting 1 to 10 for x coordinates
                {
                    gridA[0][col] = new JButton("" + col);
                    gridB[0][col] = new JButton("" + col);
                } else if (col == 0 && row > 0) // setting A to J for y coordinates
                {
                    char alphabet = (char) (row + 64); // A = 65 (1 + 64) to J = 74 (10 + 64); ASCII
                    gridA[row][0] = new JButton("" + alphabet);  
                    gridB[row][0] = new JButton("" + alphabet); 
                } else // for other buttons
                {
                    gridA[row][col] = new JButton();
                    gridB[row][col] = new JButton();
                }
                addItem(panelA, gridA[row][col], col, row+1, 1, 0);
                addItem(panelB, gridB[row][col], col, row+1, 1, 0);
            }
        }
        addItem(panelA, labelA, 5, 0, 8, 0);
        addItem(panelB, labelB, 5, 0, 8, 0);
        addItem(panelA, textA, 1, 12, 9, 0);
        addItem(panelB, textB, 1, 12, 9, 0);
        textA.setEditable(false);
        textA.setHorizontalAlignment(JTextField.CENTER);
        textB.setEditable(false);
        textB.setHorizontalAlignment(JTextField.CENTER);
        add(panelA, BorderLayout.WEST);
        add(panelB, BorderLayout.EAST);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setVisible(true); 
    }

    private void addItem(JPanel pnl, JComponent c, int x, int y,
    int horizontalSpan, int margin) { // Method to add a control to the panel
        GridBagConstraints gc = new GridBagConstraints(); // Defines where control will be positioned and formatting
        gc.gridx = x; // Column for control
        gc.gridy = y; // Row for control
        gc.gridwidth = horizontalSpan; // Number of columns spanned
        gc.gridheight = 1; // Each component only spans 1 row
        gc.insets = new Insets(margin,margin,margin,margin); // Sets the margins around the controls
        gc.fill = GridBagConstraints.BOTH; // Enlarge control in both directions to fill the cell
        gc.anchor = GridBagConstraints.CENTER; // Centralise the control in the cell
        gc.weightx = 1.0; // Distribute extra space equally in x direction
        gc.weighty = 1.0; // Distrubute extra space equally in y direction
        pnl.add(c,gc); // Add the component
    }

    public void print() // print the board
    {
        repaint();
    }

    public void clearTerminal() // clear the console
    {  
        System.out.print('\u000C');
    }  

    public void hideShips(JButton[][] currentGrid) // for hiding ships after all ships are placed
    {
        for (int i = 1; i < boardSize; i++) {
            for (int j = 1; j < boardSize; j++) {
                currentGrid[i][j].setOpaque(false);
                currentGrid[i][j].setContentAreaFilled(false);
                currentGrid[i][j].setBorderPainted(true);
            }
        }
    }

    public void disableButtons(JButton[][] gridA, JButton[][] gridB) // not to click buttons after the game is over
    {
        for (int i = 1; i < boardSize; i++) {
            for (int j = 1; j < boardSize; j++) {
                gridA[i][j].setEnabled(false);
                gridB[i][j].setEnabled(false);
            }
        }
    }

    public void remainingShipsOfA() { // display remaining ships (have not been attacked) of player A when player A wins the game
        for (int i = 1; i < boardSize; i++) {
            for (int j = 1; j < boardSize; j++) {
                char boardChar = boardA[i][j]; // take a character from the board.
                Color colour;
                if (boardChar != 'H' && boardChar != ' ') // if the character of board is not 'H'(hit) and blank
                { 
                    switch (boardChar) 
                    {
                        case 'C': // Carrier
                            colour = Color.GREEN; break;
                        case 'B': // Battleship
                            colour = Color.BLUE; break;
                        case 'D': // Destroyer
                            colour = Color.RED; break;
                        case 'S': // Submarine
                            colour = Color.YELLOW; break;
                        case 'P': // Patrol Boat
                            colour = Color.ORANGE; break;
                        default : 
                            colour = Color.GRAY; break;
                    }
                    gridA[i][j].setBackground(colour); // set background with the colour of each ship
                    gridA[i][j].setOpaque(true);
                    gridA[i][j].setBorderPainted(false);
                    gridA[i][j].setText("N"); // 'N' represents 'Not attacked'
                }
            }
        }
    }
    
    public void remainingShipsOfB() { // display remaining ships of player B when the player B wins the game
        for (int i = 1; i < boardSize; i++) {
            for (int j = 1; j < boardSize; j++) {
                char boardChar = boardB[i][j]; // take a character from the board
                Color colour;
                if (boardChar != 'H' && boardChar != ' ') // if the character of board is not 'H'(hit) and blank
                {
                    switch (boardChar) 
                    {
                        case 'C': // Carrier
                            colour = Color.GREEN; break;
                        case 'B': // Battleship
                            colour = Color.BLUE; break;
                        case 'D': // Destroyer
                            colour = Color.RED; break;
                        case 'S': // Submarine
                            colour = Color.YELLOW; break;
                        case 'P': // Patrol Boat
                            colour = Color.ORANGE; break;
                        default :
                            colour = Color.GRAY; break;
                    }
                    gridB[i][j].setBackground(colour); // set background with the colour of each ship
                    gridB[i][j].setOpaque(true);
                    gridB[i][j].setBorderPainted(false);
                    gridB[i][j].setText("N"); // 'N' represents 'Not attacked'
                }
            }
        }
    }
}

