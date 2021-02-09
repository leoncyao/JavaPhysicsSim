public class Tools {

    public static double[] convertToNewAxis(double magnitude, double origin_angle, double new_axis_angle){
        // origin angle is the ccw angle the vector makes with the x axis
        // new_axis_angle is the angle between the x axises of two coordinate planes
        double[] components = new double[2];

        double total_angle = origin_angle - new_axis_angle;

        components[0] = Math.cos(total_angle) * magnitude;
        components[1] = Math.sin(total_angle) * magnitude;
//        System.out.printf("components 0 check %f \n", components[0]);
//        System.out.printf("components 1 check %f \n", components[1]);
        return components;
    }
    public static double[] convertToOldAxis(double magnitude, double origin_angle, double new_axis_angle){
        // origin angle is the ccw angle the vector makes with the x axis
        // new_axis_angle is the angle between the x axises of two coordinate planes
        double[] components = new double[2];

        double total_angle = origin_angle + new_axis_angle;

        components[0] = Math.cos(total_angle) * magnitude;
        components[1] = Math.sin(total_angle) * magnitude;

        return components;
    }

    public static double findDist(double obj1_x,double obj1_y,double obj2_x,double obj2_y){

        double dist_x = Math.abs(obj2_x - obj1_x);
        double dist_y = Math.abs(obj2_y - obj1_y);

        return Math.sqrt(Math.pow(dist_x, 2) + Math.pow(dist_y, 2));

    }
    public static double findAngle(double obj1_x, double obj1_y,double obj2_x,double obj2_y) {

        double dx = (obj2_x - obj1_x);
        double dy = (obj2_y - obj1_y);

        double angle = 0;

        if (dx == 0) {
            if (dy > 0) {
                angle = Math.PI/2;
            } else if (dy <= 0) {
                angle = 3*Math.PI / 2;
            }
        }
        else
            {
                angle = Math.atan(dy / dx);
            }

            angle = Math.abs(angle);

            if (dx > 0 && dy >= 0) {
                angle = angle;
//                System.out.println("1rst quad");
            } else if (dx < 0 && dy > 0) {
//                System.out.println("2nd quad");
                angle = Math.PI - angle;
            } else if (dx < 0 && dy <= 0) {
                angle = Math.PI + angle;
//                System.out.println("3rth quad");
            } else if (dx > 0 && dy < 0) {
                angle = 2 * Math.PI - angle;
//                System.out.println("4rth quad");
            }


//        System.out.println("angle is " + String.valueOf(180/Math.PI*angle));

        return angle;
    }

    public static double findRefAngle(double obj1_x, double obj1_y,double obj2_x,double obj2_y) {

        double dx = (obj2_x - obj1_x);
        double dy = (obj2_y - obj1_y);

        double angle = 0;

        if (dx == 0) {
            if (dy > 0) {
                angle = Math.PI/2;
            } else if (dy <= 0) {
                angle = 3*Math.PI / 2;
            }
        }
        else
        {
            angle = Math.atan(dy / dx);
        }

        angle = Math.abs(angle);

//        System.out.println("angle is " + String.valueOf(180/Math.PI*angle));

        return angle;
    }
}
