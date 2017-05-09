import java.awt.Graphics;
import java.awt.Color;

public class Segment extends GameObj {

    Color color;
    
    public Segment(int px, int py, int width, int height,  int courtWidth, int courtHeight, Color c) {
        super (px, py, width, height, courtWidth, courtHeight);
        this.color = c;
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(getPx(), getPy(), getWidth(), getHeight());
    }
    
}
