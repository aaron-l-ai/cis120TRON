import java.awt.Color;
import java.awt.*;
import java.util.ArrayList;

public class Blue extends Cycle{
    
    public Blue(Color color) {
        super (color);
        
        for (int i = 0; i < 1; i++) {
            // first two entries generate random location within bounds of court
            cycle.add(new Segment(50, 50, 
                    width, height , 500, 500, color));
        }
        d = Direction.RIGHT;
        
    }
    
}
