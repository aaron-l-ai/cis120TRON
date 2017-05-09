import java.awt.Color;
import java.awt.Graphics;

public class Bonus extends GameObj{
    public Bonus (int px, int py, int width, int height, int courtWidth,
            int courtHeight) {
        super(px, py, width, height, courtWidth, courtHeight);
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(px, py, getWidth(), getHeight());
    }
}
