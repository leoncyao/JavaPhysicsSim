import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class RectangularObject extends Object {
    public double startX, startY;
    public double width, height, orientation;

    public RectangularObject(Tuple pos, Tuple vel, Tuple bounds, Tuple dim, double orientation, double mass) {
        super(pos, vel, bounds, mass);
        this.startX = pos_x;
        this.startY = pos_y;
        this.width = dim.get(0);
        this.height = dim.get(1);
        this.orientation = orientation;
    }

    @Override
    public void draw(Graphics2D g) {
//        System.out.println("check");
        Rectangle2D r = new Rectangle2D.Double((int)(pos_x-width/2), (int)(bounds_y-(pos_y+height/2)),(int) width, (int) height);
        g.rotate(orientation,pos_x,pos_y);
        g.fill(r);
        g.rotate(-orientation,pos_x,pos_y);
    }

    @Override
    public void updateVel() {
        vel_x = 0;
        vel_y = 0;
    }

    public void updatePos() {
        pos_x = startX;
        pos_y = startY;
    }

    public void updateStats(ArrayList<Object> objects){
//        updateForces(objects);
//        updateAcel();
        updateVel();
        updatePos();
        this.forces = new ArrayList<Force>();
    }
}
