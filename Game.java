/**
 * CIS 120 Game HW


 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;

import java.io.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("TRON");
        frame.setLocation(500, 500);
        
        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.NORTH);
        final JLabel status = new JLabel("Running...");
        
        //High score information panel
        final JLabel scoreStatus = new JLabel("Try for a high score!");

        
        // Score panel
        final JPanel score_panel = new JPanel();
        score_panel.setBorder(BorderFactory.createTitledBorder("Player Scores"));
        frame.add(score_panel, BorderLayout.SOUTH);
        final JLabel blueScore = new JLabel ("Player 1:");
        blueScore.setBorder(BorderFactory.createLineBorder(Color.blue));
        final JLabel redScore = new JLabel ("Player 2:");
        redScore.setBorder(BorderFactory.createLineBorder(Color.red));
        score_panel.add(blueScore);
        score_panel.add(redScore);
        score_panel.add(status);
        
        // Instruction Window
        JButton instructions = new JButton("Instructions and Controls");
        instructions.addActionListener(new ActionListener() {
            String message = 
                    "Player one controls the blue cycle with arrows keys. \n"
                    + "Player two controls the red cycle with WASD. Outwit \n"
                    + "your opponents while collecting golden coins \n"
                    + "to boost your score!";
                
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(frame, message);
                }
                catch (HeadlessException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        score_panel.add(instructions);
        

        // Main playing area
        final GameCourt court = new GameCourt(status, blueScore, redScore, scoreStatus);
        frame.add(court, BorderLayout.CENTER);
        frame.setResizable(false);
        
        //high score button for IO
        JButton highScore = new JButton("High Scores");
        highScore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(frame, court.topThreeScores());
                }
                catch (HeadlessException e1) {
                    e1.printStackTrace();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);
        control_panel.add(highScore);
        control_panel.add(scoreStatus);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}