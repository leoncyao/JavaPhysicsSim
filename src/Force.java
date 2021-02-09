import java.util.ArrayList;

public class Force extends Tuple{

    private ArrayList components;

    public Force(double x, double y){
        super(x, y);
//        components = new ArrayList<>();
//        components.add(x);
//        components.add(y);

    }

    public double x_comp(){

        return (double) items.get(0);

    }

    public double y_comp(){
        return (double) items.get(1);
    }


}
