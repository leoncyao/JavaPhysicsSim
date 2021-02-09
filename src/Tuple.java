import java.lang.Object;
import java.util.ArrayList;
import java.util.List;

public class Tuple {
//    public ArrayList<Double> items = new ArrayList<>();
    public List<Object> items = new ArrayList<>();

    public Tuple(double item1, double item2){
        items.add(item1);
        items.add(item2);

    }
    public Tuple(double item1, double item2, double item3){

        items.add(item1);
        items.add(item2);
        items.add(item3);

    }

    public Tuple(String item1, double item2){

        items.add(item1);
        items.add(item2);

    }

    public double get(int index){

        return (double) items.get(index);

    }

    public String getString(int index){
        return String.valueOf(items.get(index));
    }

//    public String get(int index){
//
//        return (String) items.get(index);
//
//    }



}
