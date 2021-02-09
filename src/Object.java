import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Object extends JComponent{

    public double pos_x, pos_y, vel_x, vel_y, acel_x, acel_y, mass;
    public double bounds_x, bounds_y;
    public boolean has_gravity;
    public boolean has_earthGravity;
    public ArrayList<Force> forces;
    public double gravConst;

    public static boolean applyGrav = false, applyEarthGrav = false;

    public Object(Tuple pos, Tuple vel, Tuple bounds, double mass){
        this.pos_x = pos.get(0);
        this.pos_y = pos.get(1);

        this.vel_x = vel.get(0);
        this.vel_y = vel.get(1);

        this.acel_x = 0;
        this.acel_y = 0;

        this.bounds_x = bounds.get(0);
        this.bounds_y = bounds.get(1);

        this.mass =  mass;

        has_gravity = applyGrav;
        has_earthGravity = applyEarthGrav;

        this.gravConst = 100;
        this.forces = new ArrayList<Force>();
    }

    public void updatePos(){
        pos_x += vel_x;
        pos_y += vel_y;
    }

    public void updateVel(){
        vel_x += acel_x;
        vel_y += acel_y;

    }

    public void updateAcel(){
        acel_x = 0;
        acel_y = 0;
        for (Force force : forces){
            acel_x += force.x_comp()/mass;
            acel_y += force.y_comp()/mass;
        }

    }

    public void updateForces(ArrayList<Object> objects){
        if (has_gravity){

            forces.addAll(updateGravity(objects));
        }
        if (has_earthGravity){
            System.out.println("check");
            forces.add(updateEarthGravity());
        }
    }

    private Force updateEarthGravity(){
        Force EarthGrav = new Force(0, 0.01*-9.8 * mass);
        return EarthGrav;
    }

    private ArrayList<Force> updateGravity(ArrayList<Object> objects){
        ArrayList<Force> gravities = new ArrayList<Force>();
        for (Object obj : objects){
            if (obj.hashCode() != this.hashCode()){
                Force gravity = calcGravity(obj);
                gravities.add(gravity);
            }
        }
        return gravities;
    }

    private Force calcGravity(Object obj){
        double total_dist = Tools.findDist(pos_x, pos_y, obj.pos_x, obj.pos_y);
        if (total_dist == 0) {
            total_dist = 1;
        }
        double magnitude = (obj.mass * mass / total_dist)/gravConst; // * g
        double angle = Tools.findAngle(pos_x, pos_y, obj.pos_x, obj.pos_y);
        return new Force(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);
    }

    public void updateStats(ArrayList<Object> objects){
        updateForces(objects);
        updateAcel();
        updateVel();
        updatePos();
        forces = new ArrayList<Force>();
    }
    @Override
    public String toString() {
        String word = String.valueOf(pos_x) + " " +  String.valueOf(pos_y);
        return word;
    }

    public ArrayList<Stat> toStat(){

        ArrayList<Stat> fields = new ArrayList<Stat>();
        fields.add(new Stat("pos_x", pos_x));
        fields.add(new Stat("pos_y", pos_y));
        fields.add(new Stat("vel_x", vel_x));
        fields.add(new Stat("vel_y", vel_y));
        fields.add(new Stat("acel_x", acel_x));
        fields.add(new Stat("acel_y", acel_y));
        return fields;
    }

    public static void setApplyGrav(boolean t){
        applyGrav = t;
    }

    public static void setApplyEarthGrav(boolean t){
        applyEarthGrav = t;
    }

    public abstract void draw(Graphics2D g);


    public double getPos_x() {
        return pos_x;
    }

    public double getPos_y() {
        return pos_y;
    }

    public double getVel_x() {
        return vel_x;
    }

    public double getVel_y() {
        return vel_y;
    }

    public double getAcel_x() {
        return acel_x;
    }

    public double getAcel_y() {
        return acel_y;
    }

}

