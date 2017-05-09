import static org.junit.Assert.*;
import org.junit.*;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import org.junit.Test;

public class snakecycleTest {
    
    private GameCourt field;
    private Blue blue;
    private Red red;
    private JLabel status;
    

    @Test public void testInitializeCycles() {
        blue = new Blue(Color.blue);
        red = new Red(Color.red);
        assertEquals(blue.cycle.size(), 1);
        assertEquals(red.cycle.size(), 1);
    }
    
    @Before
    public void prior() {
        JLabel status = new JLabel("Running...");
        JLabel blueScore = new JLabel ("Player 1:");
        JLabel redScore = new JLabel ("Player 2:");
        JLabel scoreStatus = new JLabel("Try for a high score!");
        field = new GameCourt(status, blueScore, redScore, scoreStatus);
        
    }
    
    @Test public void cyclesColide() throws AWTException {
        Robot robot = new Robot();
        field.reset();
        blue = field.getBlueCycle();
        red = field.getRedCycle();
        
        for (int i = 0; i < 200; i++) {
            field.tick();
            robot.keyPress(KeyEvent.VK_KP_DOWN);
            robot.keyPress(KeyEvent.VK_W);
            blue.move(Color.blue);
            red.move(Color.red);
            field.tick();
        }
        assertTrue(!field.getPlaying());
    }
    
    @Test public void cycleSelfColides() throws AWTException {
        Robot robot = new Robot();
        field.reset();
        blue = field.getBlueCycle();
        red = field.getRedCycle();
        
        for (int i = 0; i < 200; i++) {
            field.tick();
            robot.keyPress(KeyEvent.VK_KP_LEFT);
            robot.keyPress(KeyEvent.VK_A);
            blue.move(Color.blue);
            red.move(Color.red);
            field.tick();
        }
        assertTrue(!field.getPlaying());
    }
    
    @Test public void bonusValueTest() throws AWTException {
        field.reset();
        blue = field.getBlueCycle();
        red = field.getRedCycle();
        field.addBonus(70, 50);
        
        for (int i = 0; i < 20; i++) {
            field.tick();
            blue.move(Color.blue);
            red.move(Color.red);
            field.tick();
        }
        assertEquals(field.getBonusBlue(), 10);
    }
    
    @Test public void bonusValueTest2() throws AWTException {
        field.reset();
        blue = field.getBlueCycle();
        red = field.getRedCycle();
        field.addBonus(70, 450);
        
        for (int i = 0; i < 20; i++) {
            field.tick();
            blue.move(Color.blue);
            red.move(Color.red);
            field.tick();
        }
        assertEquals(field.getBonusRed(), 10);
    }
    
    @Test public void redWins() throws AWTException {
        Robot robot = new Robot();
        field.reset();
        blue = field.getBlueCycle();
        red = field.getRedCycle();
        field.addBonus(60, 450);
        
        for (int i = 0; i < 20; i++) {
            field.tick();
            robot.keyPress(KeyEvent.VK_KP_LEFT);
            robot.keyPress(KeyEvent.VK_A);
            blue.move(Color.blue);
            red.move(Color.red);
            field.tick();
            this.status = field.getStatus();
        }
        //System.out.println(status.getText());
        assertEquals("Player 2 wins!", status.getText());
    }
    
    @Test public void cyclesTie() throws AWTException {
        Robot robot = new Robot();
        field.reset();
        blue = field.getBlueCycle();
        red = field.getRedCycle();
        
        for (int i = 0; i < 200; i++) {
            field.tick();
            robot.keyPress(KeyEvent.VK_KP_DOWN);
            robot.keyPress(KeyEvent.VK_W);
            blue.move(Color.blue);
            red.move(Color.red);
            field.tick();
            this.status = field.getStatus();
        }
        assertEquals("It's a tie!", status.getText());
    }
	
}
