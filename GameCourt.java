/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Blue blue; // the Black Square, keyboard control
    private Red red;
    private Bonus gold; 
    
    // bonus points from golden coin
    private int bonusBlue;
    private int bonusRed;
    
    //simple timer for in-game events
    private int timer;
    
    // keeps track of whether a snake should continue to move
    private boolean redrawBlue = false;
    private boolean redrawRed = false;

    private boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."
    private JLabel blueScore; //score for blue
    private JLabel redScore; //score for red
    private JLabel scoreStatus; // status of score

    // Game constants
    public static final int COURT_WIDTH = 500;
    public static final int COURT_HEIGHT = 500;
    
    //Score
    private int scoreBlue;
    private int scoreRed;
    private int highScore;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 60;

    public GameCourt(JLabel status, JLabel blueScore, JLabel redScore, JLabel scoreStatus) {
        // creates border around the court area, JComponent method
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        
        //blue snake cycle controls
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    blue.d = Direction.LEFT; 
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    blue.d = Direction.RIGHT; 
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    blue.d = Direction.DOWN; 
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                    blue.d = Direction.UP; 
            }

        });
        
        //red snake cycle controls
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_A)
                red.d = Direction.LEFT; 
            else if (e.getKeyCode() == KeyEvent.VK_D)
                red.d = Direction.RIGHT; 
            else if (e.getKeyCode() == KeyEvent.VK_S)
                red.d = Direction.DOWN; 
            else if (e.getKeyCode() == KeyEvent.VK_W)
                red.d = Direction.UP; 
            }
        });
        this.status = status;
        this.blueScore = blueScore;
        this.redScore = redScore;
        this.scoreStatus = scoreStatus;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        blue = new Blue(Color.blue);
        red = new Red(Color.red);
        redrawBlue = false;
        redrawRed = false;
        newGold();
        scoreRed = 0;
        scoreBlue = 0;
        bonusBlue = 0;
        bonusRed = 0;
        playing = true;
        status.setText("Running...");
        blueScore.setText("Player 1");
        redScore.setText("Player 2");
        scoreStatus.setText("Try for a high score!");
        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */

    void tick() {
        if (playing) {
            //increment timer for each tick
            timer ++;
            //System.out.println(timer);
            // move snakes
            if (!redrawBlue) {
                blue.move(Color.cyan);
            }
            if (!redrawRed) {
                red.move(Color.magenta);
            }
            // update the display
            repaint();
            scoreBlue = (int) ((int) blue.cycle.size() * 0.5 + getBonusBlue());
            blueScore.setText("Player 1: " + scoreBlue);
            scoreRed = (int) ((int) red.cycle.size() * 0.5 + getBonusRed());
            redScore.setText("Player 2: " + scoreRed);
            scoreStatus.setText("Try for a high score!");
            
            // redraws the gold coin every 170 ticks
            if (timer >= 170) {
                timer = 0;
                newGold();
            }
            // check for collisions
            if (redrawRed && redrawBlue) {
                try {
                    inputScores();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                playing = false;
                if (scoreBlue > scoreRed) {
                    updateScores();
                    status.setText("Player 1 wins!");
                    highScore = scoreBlue;
                    try {
                        inputScores();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (scoreRed > scoreBlue) {
                    updateScores();
                    status.setText("Player 2 wins!");
                    highScore = scoreRed;
                    try {
                        inputScores();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    updateScores();
                    status.setText("It's a tie!");
                    highScore = scoreBlue;
                    try {
                        inputScores();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            if (blue.otherCollisions(red)) {
                updateScores();
                redrawBlue = true;
            }
            
            if (red.otherCollisions(blue)) {
                // playing = false; 
                updateScores();
                redrawRed = true;

            }
            
            //self collisions, stop drawing new segments and halt
            if (blue.selfCollisions()) {
                redrawBlue = true;
            }
            
            if (red.selfCollisions()) {
                redrawRed = true;
            }
            
            //colliding with the bonus coin
            if (blue.otherCollisions(gold)) {
                bonusBlue += 10;
                newGold();
            }
            
            if (red.otherCollisions(gold)) {
                bonusRed += 10;
                newGold();
            }
        }
    }
    
    public int getBlueScore() {
        return scoreBlue;
    }
    
    public void updateScores() {
        redScore.setText("Player 2: " + scoreRed);
        blueScore.setText("Player 1: " + scoreBlue);
    }
    // for generation of new gold coin
    private void newGold() {
        gold = new Bonus((int) Math.round((Math.random()*(500))/10)*10, 
                (int) Math.round((Math.random()*(500))/10)*10, 
                15, 15, 500, 500);
    }
    
    
    //IO methods
    public String topThreeScores() throws IOException {
        String s = "";
        FileReader input = new FileReader("highscore.txt");
        BufferedReader b = new BufferedReader(input);
        try {
            for (int i = 0; i < 3; i++) {
                s += b.readLine() + "\n";
            }
            return s;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            b.close();
            input.close();
        }
        return s;
               
    }
    
    private void writeScore(String [] s) throws IOException {
        FileWriter write = new FileWriter("highscore.txt");
        BufferedWriter out = new BufferedWriter(write);
        try {
            for (int i = 0; i < s.length; i++) {
                out.write(s[i]);
                out.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            out.close();
        }
        
    }
    
    private void inputScores() throws NumberFormatException, IOException {
        FileReader input = new FileReader("highscore.txt");
        BufferedReader b = new BufferedReader(input);
        while (b.ready()) {
            int compareScore = Integer.parseInt(b.readLine());
            //System.out.println(highScore);
            //System.out.println(compareScore);
            if (highScore > compareScore) {
                String first = Integer.toString(highScore);
                String second = Integer.toString(compareScore);
                String third = b.readLine();
                String[] toWrite = {first, second, third};
                writeScore(toWrite);
                scoreStatus.setText("You've beaten the highest score!");
                break;
            }
            
            else if (highScore <= compareScore) {
                int second = Integer.parseInt(b.readLine());
                int third = Integer.parseInt(b.readLine());
                if (highScore == compareScore ||
                    highScore == second ||
                    highScore == third) {
                    scoreStatus.setText("You've tied a high score!");
                    break;
                }
                
                else if (highScore > second) {
                    String first = Integer.toString(compareScore);
                    String sec = Integer.toString(highScore);
                    String thi = Integer.toString(second);
                    String[] toWrite = {first, sec, thi};
                    writeScore(toWrite);
                    scoreStatus.setText("You've beaten the second highest score!");
                    break;
                }
                
                else if (highScore > third) {
                    String first = Integer.toString(compareScore);
                    String sec = Integer.toString(second);
                    String thi = Integer.toString(highScore);
                    String[] toWrite = {first, sec, thi};
                    writeScore(toWrite);
                    scoreStatus.setText("You've beaten the third highest score!");
                    break;
                }
            }
        }
        
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        blue.draw(g);
        red.draw(g);
        gold.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    // testing methods
    public Blue getBlueCycle() {
        return blue;
    }
    
    public Red getRedCycle() {
        return red;
    }
    
    public Bonus getBonus() {
        return gold;
    }
    
    public boolean getPlaying() {
        return playing;
    }
    
    public int getBonusRed() {
        return bonusRed;
    }
    
    public int getBonusBlue() {
        return bonusBlue;
    }
    
    void addBonus(int x, int y) {
        gold = new Bonus(x, y, 15, 15, 500, 500);
    }
    
    public JLabel getStatus() {
        return status;
    }
    
}