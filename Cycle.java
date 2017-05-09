import java.awt.*;
import java.util.ArrayList;


public abstract class Cycle {
    // create an empty array list to populate to cycle segments
    public ArrayList<Segment> cycle = new ArrayList<Segment>();
    //width and height of segments
    int width = 15;
    int height = 15;
    // keep track of direction 
    public Direction d;
    int maxX = 500;
    int maxY = 500;
    
    //populate with initial head
    public Cycle(Color color) {
        /*
        for (int i = 0; i < 1; i++) {
            // first two entries generate random location within bounds of court
            cycle.add(new Segment((int) Math.round((Math.random()*(500))/10)*10, 
                    (int) Math.round((Math.random()*(500))/10)*10, 
                    width, height , 500, 500, color));
        }
        d = Direction.getRandomDirect();
        */
    }
    
    //movement 
    public void move(Color color) {
        for (int i = cycle.size() - 1; i > 0; i --) {
            cycle.get(i).px = cycle.get(i-1).getPx();
            cycle.get(i).py = cycle.get(i-1).getPy();
        }
        if (d == Direction.UP) {
            cycle.get(0).py -= height;
        }

        if (d == Direction.DOWN) {
            cycle.get(0).py += height;
        }
        if (d == Direction.LEFT) {
            cycle.get(0).px -= width;
        }

        if (d == Direction.RIGHT) {
            cycle.get(0).px += width;
        }
        //adds more segments as cycles traverse plane
        cycle.add(new Segment(cycle.get(cycle.size()-1).getPx() - width, 
                cycle.get(cycle.size()-1).getPy(), width, height, 500, 500, color)); 
        clip();

    }
    
    public void draw(Graphics g) {
        for (int i = 0;i < cycle.size(); i++) {
            cycle.get(i).draw(g);
        }
    }
    
    public boolean selfCollisions() {
        for (int i = 2; i < cycle.size(); i++) {
           if ((cycle.get(0).intersects(cycle.get(i))) && 
                   (cycle.get(0).intersects(cycle.get(i)))) { 
               return true; 
               }
        }
        return false; 
    }
    
    public boolean otherCollisions(Cycle c) {
        for (int i = 0; i < 2; i++) {
            for (int j = 2; j < c.cycle.size(); j++) {
                if ((cycle.get(i).intersects(c.cycle.get(j))) && 
                (cycle.get(i).intersects(c.cycle.get(j)))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean otherCollisions(Bonus b) {
        if ((cycle.get(0).intersects(b))) { 
            return true;
        }
        return false;
    }
    
    private void clip() {
        for (int i = 0;i < cycle.size(); i++) {
            if (cycle.get(i).px > maxX) {
                cycle.get(i).px = Math.abs(maxX - cycle.get(i).px);
            }
            if (cycle.get(i).px< 0) {
                cycle.get(i).px = Math.abs(cycle.get(i).px - maxX);
            }
            if (cycle.get(i).py > maxY) {
                cycle.get(i).py = Math.abs(maxY - cycle.get(i).py);
            }
            if (cycle.get(i).py < 0) {
                cycle.get(i).py= Math.abs(cycle.get(i).py - maxY);
            }
        }
    }
    
    
    
     
    
}
