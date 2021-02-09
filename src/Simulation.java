//import javafx.scene.layout.BorderRepeat;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;


public class Simulation extends JPanel{
    public static int WIDTH = 1280, HEIGHT = 960;
    private int display_width, display_height;
    private int test_width, test_height, center_x, center_y;
    Timer t;
    private long startSeconds;
    private long ctime, prevtime;
    private JFrame f;
    private Renderer renderer;
    private  boolean Running;
    private ArrayList<Object> objects;
    private ArrayList<Stat> stats;
    private double simLength;
    private Simulation(int testWidth, int testHeight, double SimLength){
        this.display_width = WIDTH;
        this.display_height = HEIGHT;
        this.test_width = testWidth;
        this.test_height = testHeight;
//        this.center_x = testWidth / 2 + 500;
        this.center_x = testWidth / 2;
        this.center_y = testHeight / 2;
        this.simLength = SimLength;
        renderer = new Renderer(test_width, test_height);

//        f.setLocation(200, 200); // moves app around screen
        f = new JFrame();

        f.getContentPane().setBackground(Color.WHITE);

        f.setSize(display_width, display_height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("PhysicsSimulator");
        f.setResizable(true);
        prevtime = 0;
        f.add(renderer);
        f.setVisible(true);
//        f.add(renderer);
        objects = new ArrayList<Object>();
        stats = new ArrayList<Stat>();
        startSeconds = System.currentTimeMillis();
        ctime = 0;

        Running = true;

    }

    private void addObject(Object o){
        objects.add(o);
        f.add(o);

    }

    private void CircularOrbitSimulation(){
        // will be about circular assuming grav is relatively constant using v^2/r
        Object.setApplyGrav(true);
        CircularObject.setTrail(true, 250, 5);

        double k = 0; //Vertical offset of center ball
        CircularObject temp1 = new CircularObject(new Tuple(center_x, center_y-k), new Tuple(0, 0) , new Tuple(test_width, test_height) ,10, 10000);
        temp1.hasTrail = false;
        addObject(temp1);

        double b = 0; //horizontal offset of orbiting ball
//        double c =(Math.random()*3+1) * 150; //vertical offset of orbiting ball
        double c = 100;
        double r2 = 10; // preset radius of the second ball
        double v = Math.sqrt(Math.pow(10,-1)*9.81 * c + 2*(temp1.radius + r2 - 25) ); //initial velocity of orbiting ball
        CircularObject temp2 = new CircularObject(new Tuple(center_x - b, center_y - k + c), new Tuple(v, 0) , new Tuple(test_width, test_height) , r2, 1);
        addObject(temp2);


    }
    private void GravitySimulation(int numCircularObjs){
        Object.setApplyGrav(true);
        CircularObject.setTrail(true, 100, 5);

        double r_pos_x, r_pos_y, r_vel_x, r_vel_y;
        int speedConst = 100;
        for (int i = 0; i < numCircularObjs; i++) {
            r_pos_x = Math.random() * test_width;
            r_pos_y = Math.random() * test_height;
//            r_pos_x = 0;
//            r_pos_y = 0;
            r_vel_x = test_width/2/speedConst*(1-2*Math.random());
            r_vel_y = test_height/2/speedConst*(1-2*Math.random());
//            r_vel_x = 0;
//            r_vel_y = 0;
            CircularObject temp = new CircularObject(new Tuple(r_pos_x, r_pos_y), new Tuple(r_vel_x, r_vel_y) , new Tuple(test_width, test_height) ,10, 1);
            addObject(temp);
        }
        objects.get(0).has_gravity = false;
        objects.get(0).vel_x = 0;
        objects.get(0).vel_y = 0;
        objects.get(0).pos_x = center_x;
        objects.get(0).pos_y = center_y;
        objects.get(0).mass = 1000;
    }
    private void PlanksSimulation(){

//        RectangularObjec
        RectangularObject temp1 = new RectangularObject(new Tuple(center_x, 0),new Tuple(0, 0),new Tuple(test_width, test_height), new Tuple(test_width-25, 10),0, 100000000);
        RectangularObject temp2 = new RectangularObject(new Tuple(center_x, test_height),new Tuple(0, 0),new Tuple(test_width, test_height), new Tuple(test_width-25, 10),0, 100000000);
        RectangularObject temp3 = new RectangularObject(new Tuple(0, center_y),new Tuple(0, 0),new Tuple(test_width, test_height), new Tuple(10, test_height-25),0, 100000000);
        RectangularObject temp4 = new RectangularObject(new Tuple(test_width, center_y),new Tuple(0, 0),new Tuple(test_width, test_height), new Tuple(10, test_height-25),0, 100000000);
        addObject(temp1);
        addObject(temp2);
        addObject(temp3);
        addObject(temp4);

    }
    private void CollisionSimulation(int numCircularObjs){
        // direct vertical
        CircularObject temp1 = new CircularObject(new Tuple(center_x, center_y + 100), new Tuple(0, -1) , new Tuple(test_width, test_height) ,10,  10);
        CircularObject temp2 = new CircularObject(new Tuple(center_x, center_y - 100), new Tuple(0, 1) , new Tuple(test_width, test_height) ,10,  10);
        temp1.has_gravity = false;
        temp2.has_gravity = false;
        addObject(temp1);
        addObject(temp2);

        // direct horizontal
        CircularObject temp3 = new CircularObject(new Tuple(center_x + 100, center_y), new Tuple(-1, 0) , new Tuple(test_width, test_height) ,10,  10);
        CircularObject temp4 = new CircularObject(new Tuple(center_x - 100, center_y), new Tuple(1, 0) , new Tuple(test_width, test_height) ,10,  10);
        temp3.has_gravity = false;
        temp4.has_gravity = false;
//        addObject(temp3);
//        addObject(temp4);

        // direct diagonal
        CircularObject temp5 = new CircularObject(new Tuple(center_x + 100, center_y + 100), new Tuple(-1, -1) , new Tuple(test_width, test_height) ,10,  10);
        CircularObject temp6 = new CircularObject(new Tuple(center_x - 100, center_y - 100), new Tuple(1, 1) , new Tuple(test_width, test_height) ,10,  10);
        temp5.has_gravity = false;
        temp6.has_gravity = false;
//        addObject(temp5);
//        addObject(temp6);

        //miscellaneous
//        int a = 1;
//        CircularObject temp7 = new CircularObject(new Tuple(center_x + a, center_y + 100), new Tuple(0, -1) , new Tuple(test_width, test_height) ,10,  10);
//        CircularObject temp8 = new CircularObject(new Tuple(center_x - a, center_y + 100), new Tuple(0, -1) , new Tuple(test_width, test_height) ,10,  10);
//        temp7.has_gravity = false;
//        temp8.has_gravity = false;
//        addObject(temp7);
//        addObject(temp8);
        int b = -1;
        CircularObject temp9 = new CircularObject(new Tuple(center_x + 100, center_y + b), new Tuple(-1, 0) , new Tuple(test_width, test_height) ,10,  10);
        temp9.has_gravity = false;
//        addObject(temp9);

        double r_pos_x, r_pos_y, r_vel_x, r_vel_y;
        int speedConst = 100;
        for (int i = 0; i < numCircularObjs; i++) {
            r_pos_x = Math.random() * test_width;
            r_pos_y = Math.random() * test_height;
//            r_pos_x = 0;
//            r_pos_y = 0;
            r_vel_x = test_width/2/speedConst*(1-2*Math.random());
//            System.out.println(r_vel_x);
            r_vel_y = test_height/2/speedConst*(1-2*Math.random());
//            r_vel_x = 0;
//            r_vel_y = 0;
            CircularObject temp = new CircularObject(new Tuple(r_pos_x, r_pos_y), new Tuple(r_vel_x, r_vel_y) , new Tuple(test_width, test_height) ,10, 1);
            temp.has_gravity = false;
            addObject(temp);
        }
    }
    private void FunSimulation(){
        CircularObject.setTrail(true, 1000, 15);
        Object.setApplyGrav(true);

        double k = 100;
        CircularObject temp1 = new CircularObject(new Tuple(center_x, center_y-k), new Tuple(0, 0) , new Tuple(test_width, test_height) ,1, 1000);
        temp1.hasTrail = false;
        addObject(temp1);
        double n = 3;
        double a = 2.5;
        double b = 2.875;
        double c = 200;
        CircularObject temp2 = new CircularObject(new Tuple(center_x-n, center_y -k + c), new Tuple(-a, b) , new Tuple(test_width, test_height) ,1, 1);
        addObject(temp2);
        CircularObject temp3 = new CircularObject(new Tuple(center_x+n, center_y -k+ c), new Tuple(a, b) , new Tuple(test_width, test_height) ,1, 1);
        addObject(temp3);
    }

    public void nineTeenSimulation(){
        // will be about circular assuming grav is relatively constant using v^2/r
        Object.setApplyGrav(true);
        CircularObject.setTrail(true, 1000, 5);
        double g = 100;
        double k = -100; //Vertical offset of center ball
        CircularObject temp1 = new CircularObject(new Tuple(center_x + g, center_y-k), new Tuple(0, 0) , new Tuple(test_width, test_height) ,10, 10000);
        temp1.hasTrail = false;
        addObject(temp1);

        double b = 0; //horizontal offset of orbiting ball
        double c = 100;
        double r2 = 10; // preset radius of the second ball
        double v = Math.sqrt(Math.pow(10,-1)*9.81 * c + 2*(temp1.radius + r2 - 25) ); //initial velocity of orbiting ball
        CircularObject temp2 = new CircularObject(new Tuple(center_x + g - b, center_y - k + c), new Tuple(v, 0) , new Tuple(test_width, test_height) , r2, 1);
        addObject(temp2);

        CircularObject temp3 = new CircularObject(new Tuple(center_x + g + 90 , center_y - k), new Tuple(0, -4) , new Tuple(test_width, test_height) , r2, 1);
        temp3.has_gravity = false;
        addObject(temp3);

        CircularObject temp4 = new CircularObject(new Tuple(center_x + g -200 , center_y - k + 100), new Tuple(0, -4*5/3) , new Tuple(test_width, test_height) , r2, 1);
        temp4.has_gravity = false;
        addObject(temp4);

    }

    private ArrayList<Object[]> get_pairs_of_objects(){
        ArrayList<Object[]> pairs = new ArrayList<Object[]>();
        int i = 0;
//        System.out.println("check" + objects.size());
        while (i < objects.size() - 1) {
            int j = i + 1;
            while (j <= objects.size() - 1){
                Object[] pair = new Object[2];
                pair[0] = objects.get(i);
                pair[1] = objects.get(j);
                pairs.add(pair);
                j += 1;
            }
            i += 1;
        }
//        System.out.println("length of pairs " + pairs.size());
        return pairs;
    }
    private void check_Collisions(){

        // Get all pairs of Objects
        ArrayList<Object[]> pairs = get_pairs_of_objects();
        // Iterate through the pairs

//        System.out.println(pairs.size());
        for (Object[] pair: pairs){

            Object obj1 = pair[0];
            Object obj2 = pair[1];

            if (obj1 instanceof CircularObject && obj2 instanceof CircularObject){
                Collisions.checkCollision((CircularObject)obj1, (CircularObject)obj2);
            } else if (obj1 instanceof CircularObject && obj2 instanceof RectangularObject){
                Collisions.checkCollision((CircularObject)obj1, (RectangularObject)obj2);
            } else if (obj1 instanceof RectangularObject && obj2 instanceof CircularObject){
                Collisions.checkCollision((CircularObject)obj2, (RectangularObject) obj1);
            } else if (obj1 instanceof RectangularObject && obj2 instanceof RectangularObject){
//                Collisions.checkCollision((RectangularObject) obj1, (RectangularObject) obj2);
            }

//
//            // For Circular Objects, check if the sum of their radii is greater than their distance
//            CircularObject object1 = (CircularObject) (obj1);
//            CircularObject object2 = (CircularObject) (obj2);
//            double distance = Tools.findDist(object1.pos_x, object1.pos_y, object2.pos_x, object2.pos_y);
//
//            if (distance < object1.radius + object2.radius){
////                System.out.println("In contact");
////                System.out.println(obj1.mass);
////                System.out.printf("obj1 ori components %f %f obj2 ori components %f %f \n", obj1.vel_x, obj1.vel_y, obj2.vel_x, obj2.vel_y);
//                // define the new positive normal axis as the vector from the center of object 1 to the center of object to
//                // tangent axis is ccw
//
//                //First find object 1 and 2 velocity and their angles
//
//                double obj1_vel = Math.sqrt((Math.pow(obj1.vel_x, 2))+(Math.pow(obj1.vel_y, 2)));
//                double obj2_vel = Math.sqrt((Math.pow(obj2.vel_x, 2))+(Math.pow(obj2.vel_y, 2)));
//
//                double obj1_angle = Tools.findAngle(0, 0, obj1.vel_x, obj1.vel_y);
//                double obj2_angle = Tools.findAngle(0, 0, obj2.vel_x, obj2.vel_y);
//
//                // Find the angle the new axis makes with the conventional xy plane
//
//                double normal_axis_angle = Tools.findAngle(obj1.pos_x, obj1.pos_y, obj2.pos_x, obj2.pos_y);
////                System.out.printf("normal axis angle %f obj1 angle %f obj2 angle %f \n", normal_axis_angle, obj1_angle, obj2_angle);
//                // get the new components with respect the collision frame of reference
//                double[] obj1_components = convertToNewAxis(obj1_vel, obj1_angle, normal_axis_angle);
//                double[] obj2_components = convertToNewAxis(obj2_vel, obj2_angle, normal_axis_angle);
//                double obj1_vn = obj1_components[0];
//                double obj1_vt = obj1_components[1];
//                double obj2_vn = obj2_components[0];
//                double obj2_vt = obj2_components[1];
//
////                System.out.printf("obj1 ref components %f %f obj2 ref components %f %f \n", obj1_vn, obj1_vt, obj2_vn, obj2_vt);
//                // calculate the new normal velocities after colision
//                double c1 = 1 / (obj1.mass + obj2.mass);
//                double c2 = (obj1.mass - obj2.mass);
//                double c3 = c2 * c1;
//
//                double obj1_vn_f = c3 * obj1_vn + 2 * obj2.mass * c1 * obj2_vn;
//                double obj2_vn_f = 2 * obj1.mass * c1 * obj1_vn + -1 * c3 * obj2_vn;
//
//                double obj1_vel_rf = Math.sqrt((Math.pow(obj1_vn_f, 2))+(Math.pow(obj1_vt, 2)));
//                double obj2_vel_rf = Math.sqrt((Math.pow(obj2_vn_f, 2))+(Math.pow(obj2_vt, 2)));
//
////                System.out.printf("obj1 vel rf %f obj1 vel rf %f \n", obj1_vel_rf, obj2_vel_rf);
//
//                double obj1_angle_f = Tools.findAngle(0, 0, obj1_vn_f, obj1_vt);
//                double obj2_angle_f = Tools.findAngle(0, 0, obj2_vn_f, obj2_vt);
//
//
//                double[] obj1_components_f = convertToOldAxis(obj1_vel_rf, obj1_angle_f, normal_axis_angle);
//                double[] obj2_components_f = convertToOldAxis(obj2_vel_rf, obj2_angle_f, normal_axis_angle);
//
////                obj1.vel_x = obj1_components_f[0];
////                obj1.vel_y = obj1_components_f[1];
////                obj2.vel_x = obj2_components_f[0];
////                obj2.vel_y = obj2_components_f[1];
////
//                obj1.pos_x += obj1_components_f[0];
//                obj1.pos_y += obj1_components_f[1];
//                obj2.pos_x += obj2_components_f[0];
//                obj2.pos_y += obj2_components_f[1];
//
//                double obj1_impulse_x = obj1_components_f[0] - obj1.vel_x;
//                double obj1_impulse_y = obj1_components_f[1] - obj1.vel_y;
//
//                double obj2_impulse_x = obj2_components_f[0] - obj2.vel_x;
//                double obj2_impulse_y = obj2_components_f[1] - obj2.vel_y;
//
//                Force force1 = new Force(obj1_impulse_x * obj1.mass, obj1_impulse_y * obj1.mass);
//                Force force2 = new Force(obj2_impulse_x * obj2.mass, obj2_impulse_y * obj2.mass);
////                Force force1 = new Force(obj1_impulse_x, obj1_impulse_y);
////                Force force2 = new Force(obj2_impulse_x, obj2_impulse_y);
////                Force force1 = new Force(2*obj1.mass*obj1_components_f[0], 2*obj1.mass*obj1_components_f[1]);
////                Force force2 = new Force(-1*obj2.mass*obj2_components_f[0], 2*obj2.mass*obj2_components_f[1]);
//
////                Force force1 = new Force(obj1_components[0], obj1_components[1]);
////                Force force2 = new Force(obj2_components[0], obj2_components[1]);
//                obj1.forces.add(force1);
//                obj2.forces.add(force2);
////                System.out.println("forces added");
////                System.out.printf("obj1 ref components FINAL %f %f obj2 ref components FINAL %f %f \n", obj1.vel_x, obj1.vel_y, obj2.vel_x, obj2.vel_y);
//            }
        }
    }
    private void run(){


        int r = 0;
        int g = 0;
        int b = 0;
        while (Running){

            if (ctime - prevtime > 10){


//                RectangularObject temp1 = new RectangularObject(new Tuple(center_x, center_y),new Tuple(0, 0),new Tuple(test_width, test_height), new Tuple(50, 50),0, 100);
//                addObject(temp1);
//                this.renderer.setColor(r,g,b);

                prevtime = ctime;
                check_Collisions();
                for (Object object : objects){
                    object.updateStats(objects);
//                        double new_x = f1(object.pos_x, object.pos_y);
//                        double new_y = f2(object.pos_x, object.pos_y);
//                        object.pos_x = new_x;
//                        object.pos_y = new_y;
                }
                stats = new ArrayList<Stat>();
                stats.add(new Stat("Time", (double) prevtime/1000));
                stats.addAll(objects.get(1).toStat());
//                stats.add(new Stat("rx", objects.get(0).pos_x));
//                stats.add(new Stat("ry", objects.get(0).pos_y));
//                stats.add(new Stat("rw", ((RectangularObject)(objects.get(0))).width));
//                stats.add(new Stat("rh", ((RectangularObject)(objects.get(0))).height));
//                stats.add(new Stat("bx", objects.get(1).pos_x));
//                stats.add(new Stat("by", objects.get(1).pos_y));
//                stats.add(new Stat("d", ((CircularObject)objects.get(1)).radius));
//                stats.add(new Stat("center DistX", Math.abs(objects.get(0).pos_x - objects.get(1).pos_x)));
//                stats.add(new Stat("center DistY", Math.abs(objects.get(0).pos_y - objects.get(1).pos_y)));
                this.renderer.setObjects(objects);
                this.renderer.setStats(stats);
            }
            this.renderer.redraw();
            ctime = (System.currentTimeMillis()-startSeconds);
            if (ctime >= simLength*1000){
                Running = false;
            }
        }
//        f.dispose();
    }


    static double f1(double x, double y){
        return x * y;
    }

    static double f2(double x, double y){
        return x * y;
    }

    public static void main(String args[]){
        Simulation test = new Simulation(500, 500, 1000);

//        Simulation test = new Simulation(500, 500, 120);

//        test.PlanksSimulation();
//        test.CircularOrbitSimulation();
        test.GravitySimulation(8);
//        test.nineTeenSimulation();

//        int a = 5;
//        test.CollisionSimulation(50*a);


        test.run();


        // For Fun
//        Simulation fun = new Simulation(800, 800, 3.38325);
//        fun.FunSimulation();
//        fun.run();


    }
}
