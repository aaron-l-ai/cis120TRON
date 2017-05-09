import java.awt.Color;

public class Red extends Cycle{

    public Red(Color color) {
        super(color);
        for (int i = 0; i < 1; i++) {
            // first two entries generate random location within bounds of court
            cycle.add(new Segment(50, 450, 
                    width, height , 500, 500, color));
        }
        d = Direction.RIGHT;
        
    }
    
}
