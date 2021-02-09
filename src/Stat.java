import java.util.ArrayList;
import java.lang.reflect.Field;
public class Stat {
    /*Class for SINGULAR STATSSSS
    * Use an arraylist to hold multiple if necessary
    * */
    private String title;
    private double data;
    private ArrayList<Tuple> entries;

    public Stat(String title, double data){
        this.title = title;
        this.data = Math.round(data*100)/100;
    }

    public double getData() {
        return data;
    }

    public String getTitle() {
        return title;
    }


}
