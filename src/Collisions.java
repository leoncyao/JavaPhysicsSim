
public class Collisions {

    public static void checkCollision(CircularObject obj1, CircularObject obj2){

        double distance = Tools.findDist(obj1.pos_x, obj1.pos_y, obj2.pos_x, obj2.pos_y);

        if (distance < obj1.radius + obj2.radius) {
            double normal_axis_angle = Tools.findAngle(obj1.pos_x, obj1.pos_y, obj2.pos_x, obj2.pos_y);
            calculateCollisions(obj1, obj2, normal_axis_angle);
        }
//        System.out.println("check2");

    }

    public static void checkCollision(CircularObject obj1, RectangularObject obj2) {
//        System.out.println("check3");
        if (rectBall(obj1.pos_x, obj1.pos_y, obj1.radius, obj2.pos_x, obj2.pos_y, obj2.width, obj2.height, obj2.orientation)) {

//            System.out.println("New collisions");
            // if you know a ball has collided with a rectangle
            // assume it has only hit on one side

            // find the angle the line between the center of the two objects makes with the x axis
            double normal_angle = Tools.findAngle(obj2.pos_x, obj2.pos_y, obj1.pos_x, obj1.pos_y);
//            System.out.println("normal angle is " + Math.toDegrees(normal_angle));
            // find the angle the line from the center of the rect to the top right corner makes with the x axis
            double rect_corner_angle = Tools.findAngle(0, 0, obj2.width/2, obj2.height/2);
//            System.out.println("rect Corner angle is " + Math.toDegrees(rect_corner_angle));

            double impactAngle = 0;
//            System.out.println("check 1 " + Math.toRadians(360));
            if ((Math.toRadians(360)-rect_corner_angle <= normal_angle && normal_angle <= 2*Math.PI) || (0 <= normal_angle && normal_angle <= rect_corner_angle)){
//                System.out.println("right");
                impactAngle = obj2.orientation - Math.toRadians(90);
            }else if (rect_corner_angle <= normal_angle && normal_angle <= Math.PI - rect_corner_angle){
                impactAngle = obj2.orientation;
//                System.out.println("top");
            }else if (Math.toRadians(180)-rect_corner_angle <= normal_angle && normal_angle <= Math.toRadians(180) + rect_corner_angle){
                impactAngle = Math.toRadians(90)-obj2.orientation;
//                System.out.println("left");
            }else if (Math.toRadians(180) + rect_corner_angle <= normal_angle && normal_angle <= Math.toRadians(360) - rect_corner_angle){
                impactAngle = obj2.orientation;
//                System.out.println("bot");
            }
//            impactAngle += Math.toRadians(90);
//            System.out.println("impact angle is " +  Math.toDegrees(impactAngle));
//            calculateCollisions(obj1,obj2, impactAngle + Math.toRadians(90));
            calculateCollisions(obj1,obj2, impactAngle + Math.toRadians(90));
        }
    }


    public static boolean rectBall(double bx, double by, double r, double rx, double ry, double rw, double rh, double orientation) {

        double centerDistX = Math.abs(rx - bx);
        double centerDistY = Math.abs(ry - by);
//        double techX = Math.cos(orientation) * rw/2 + Math.sin(orientation) * rh/2;
//        double techY = Math.sin(orientation) * rw/2 + Math.cos(orientation) * rh/2;
//        double dimDistX = techX + r;
//        double dimDistY = techY + r;
        double dimDistX = (rw / 2) + r;
        double dimDistY =  rh / 2 + r;
//        System.out.printf("centerDistX %f centerDistY %f dimDistX %f dimDistY %f \n", centerDistX, centerDistY, dimDistX, dimDistY);
        return centerDistX < dimDistX && centerDistY < dimDistY;


    }


    private static void calculateCollisions(Object obj1, Object obj2, double normal_axis_angle){
//        System.out.println("New collisions");
        double obj1_vel = Math.sqrt((Math.pow(obj1.vel_x, 2))+(Math.pow(obj1.vel_y, 2)));
        double obj2_vel = Math.sqrt((Math.pow(obj2.vel_x, 2))+(Math.pow(obj2.vel_y, 2)));

        double obj1_angle = Tools.findAngle(0, 0, obj1.vel_x, obj1.vel_y);
        double obj2_angle = Tools.findAngle(0, 0, obj2.vel_x, obj2.vel_y);

        // Find the angle the new axis makes with the conventional xy plane

//        double normal_axis_angle = Tools.findAngle(obj1.pos_x, obj1.pos_y, obj2.pos_x, obj2.pos_y);
//                System.out.printf("normal axis angle %f obj1 angle %f obj2 angle %f \n", normal_axis_angle, obj1_angle, obj2_angle);
        // get the new components with respect the collision frame of reference
        double[] obj1_components = Tools.convertToNewAxis(obj1_vel, obj1_angle, normal_axis_angle);
        double[] obj2_components = Tools.convertToNewAxis(obj2_vel, obj2_angle, normal_axis_angle);
        double obj1_vn = obj1_components[0];
        double obj1_vt = obj1_components[1];
        double obj2_vn = obj2_components[0];
        double obj2_vt = obj2_components[1];
//                System.out.printf("obj1 ref components %f %f obj2 ref components %f %f \n", obj1_vn, obj1_vt, obj2_vn, obj2_vt);
        // calculate the new normal velocities after colision
        double c1 = 1 / (obj1.mass + obj2.mass);
        double c2 = (obj1.mass - obj2.mass);
        double c3 = c2 * c1;

        double obj1_vn_f = c3 * obj1_vn + 2 * obj2.mass * c1 * obj2_vn;
        double obj2_vn_f = 2 * obj1.mass * c1 * obj1_vn + -1 * c3 * obj2_vn;

        double obj1_vel_rf = Math.sqrt((Math.pow(obj1_vn_f, 2))+(Math.pow(obj1_vt, 2)));
        double obj2_vel_rf = Math.sqrt((Math.pow(obj2_vn_f, 2))+(Math.pow(obj2_vt, 2)));
//        System.out.printf("final obj vel n r %f \n", obj1_vel_rf);

//                System.out.printf("obj1 vel rf %f obj1 vel rf %f \n", obj1_vel_rf, obj2_vel_rf);
        double obj1_angle_f = Tools.findAngle(0, 0, obj1_vn_f, obj1_vt);
        double obj2_angle_f = Tools.findAngle(0, 0, obj2_vn_f, obj2_vt);

//        System.out.printf("obj1_angle_f %f \n", Math.toDegrees(obj1_angle_f));
//        System.out.printf("normal axis angle %f \n", Math.toDegrees(normal_axis_angle));

        double[] obj1_components_f = Tools.convertToNewAxis(obj1_vel_rf, obj1_angle_f, - normal_axis_angle);

//        System.out.printf("final obj vel y %f \n", obj1_components_f[1]);

        double[] obj2_components_f = Tools.convertToNewAxis(obj2_vel_rf, obj2_angle_f, - normal_axis_angle);

//        obj1.pos_x += obj1_components_f[0];
//        obj1.pos_y += obj1_components_f[1];
//        obj2.pos_x += obj2_components_f[0];
//        obj2.pos_y += obj2_components_f[1];

        double obj1_impulse_x = obj1_components_f[0] - obj1.vel_x;
        double obj1_impulse_y = obj1_components_f[1] - obj1.vel_y;
//        System.out.println(obj1_impulse_y);
        double obj2_impulse_x = obj2_components_f[0] - obj2.vel_x;
        double obj2_impulse_y = obj2_components_f[1] - obj2.vel_y;

        obj1.pos_x += obj1_impulse_x ;
        obj1.pos_y += obj1_impulse_y ;
        obj2.pos_x += obj2_impulse_x ;
        obj2.pos_y += obj2_impulse_y ;

        Force force1 = new Force(obj1_impulse_x * obj1.mass, obj1_impulse_y * obj1.mass);
        Force force2 = new Force(obj2_impulse_x * obj2.mass, obj2_impulse_y * obj2.mass);
        obj1.forces.add(force1);
        obj2.forces.add(force2);
    }

}
