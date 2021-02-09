
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class CircularObject extends Object{
    public double radius;
    public ArrayList<Tuple> trail_pos;
    public ArrayList<Integer> trail_color;
    public Tuple[] trail;
    public boolean hasTrail;
//    private final int TRAIL_LENGTH = 225;

    public int TRAIL_LENGTH;
    public static double trailTime = 1, trailRad = 1;

    private double trailCounter;
    public static boolean applyTrail = false;


    public CircularObject(Tuple pos, Tuple vel, Tuple bounds, double radius, double mass){
        super(pos, vel, bounds, mass);
        this.radius = radius;
        this.hasTrail = applyTrail;
        this.trailCounter = 0;
        this.TRAIL_LENGTH = 254;
        this.trail = new Tuple[TRAIL_LENGTH];
        for (int i = 0; i < TRAIL_LENGTH; i++) {
            trail[i] = new Tuple(0, 0, 0);
        }
    }

    public void draw(Graphics2D g){
        /*Draws a circle on the given graphic*/
        if (hasTrail) {
            double x, y = 0;
            for (int i = 0; i<TRAIL_LENGTH; i++) {
                Tuple temp = trail[i];
                int color = (int) temp.get(2);
                x = temp.get(0);
                y = temp.get(1);
//                System.out.printf("color at index %d is %d trailCounter is %d \n", i, color, trailCounter);
                g.setPaint(new Color(255, color, color));
//                Shape dot = new Ellipse2D.Double(x, bounds_y-y, radius / 2, radius / 2);
                Shape dot = new Ellipse2D.Double(x-trailRad, bounds_y-(y+trailRad), trailRad, trailRad);
                g.fill(dot);
                if (color >= TRAIL_LENGTH-5){
                    color = 0;
                }
                trail[i] = new Tuple(x, y, color+TRAIL_LENGTH/trailTime);
            }
            updateTrail();
        }
        g.setPaint(Color.BLACK);
        Ellipse2D tempShape = new Ellipse2D.Double(pos_x - radius, bounds_y - (pos_y + radius), 2 * radius, 2 * radius);
        g.fill(tempShape);
    }

    public void updateVel(){
        vel_x += acel_x;
        vel_y += acel_y;
    }
    private void updateTrail(){
        if (trailCounter >= TRAIL_LENGTH){
            trailCounter = 0;
        }
        trail[(int)trailCounter] = new Tuple(pos_x, pos_y, 0);
        trailCounter += TRAIL_LENGTH/trailTime;
    }

    public static void setTrail(boolean b, double t, double r){
        applyTrail = b;
        trailTime = t;
        trailRad = r;
    }
}