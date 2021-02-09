
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Renderer extends JComponent{
    ArrayList<Object> objects;
    ArrayList<Stat> stats;
    int fontsize = 18;
    Font font = new Font("Serif", Font.PLAIN, fontsize);
    public int[] defaultColor;
    public int numr,numg,numb;
    private int display_width, display_height, center_x, center_y;

    public Renderer(int width, int height){
        objects = new ArrayList<Object>();
        stats = new ArrayList<Stat>();
        setDisplay_width(width);
        setDisplay_height(height);
        setCenter_x(width/2 + 500);
        setCenter_y(height/2 + 500);
        this.defaultColor = new int[3];
        this.defaultColor[0] = 0;
        this.defaultColor[1] = 0;
        this.defaultColor[2] = 0;
        this.numr = 0;
        this.numg = 0;
        this.numb = 0;
    }


    // make a draw function which takes in a graphics g and draws the respective shape on it
    public void paint(Graphics g){
        super.paintComponent(g);
//        if (numb == 255){
//            numb = 0;
//            numg += 1;
//        }
//        if (numg == 255){
//            numg = 0;
//            numr += 1;
//        }
//        numb += 1;
//        System.out.printf("numr %d num g %d num b %d \n", numr, numg, numb);
        Graphics2D g2 = (Graphics2D) g;
//        ((Graphics2D) g).setBackground(Color.WHITE);
        drawGridLines(g2);
        g2.setPaint(new Color(numr,numg,numb));
        for (Object object : objects) {
            object.draw(g2);
            }

        int i = 0;
        for (Stat stat: stats){
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(font);
            g2.drawString(stat.getTitle(), fontsize, fontsize + fontsize * i);
            g2.drawString(String.valueOf(stat.getData()), fontsize, fontsize + fontsize * (i+1));
            i += 2;
        }
    }
    public void setObjects(ArrayList<Object> Objects){

        objects = Objects;

    }
    public void setStats(ArrayList<Stat> Stats){

        stats = Stats;

    }
//    public void setColor(int r, int g, int b){
//        this.defaultColor[0] = r;
//        this.defaultColor[1] = g;
//        this.defaultColor[2] = b;
//
//    }


    public void redraw(){
        repaint();
    }
    public void setDisplay_height(int display_height) {
        this.display_height = display_height;
    }
    public void setDisplay_width(int display_width) {
        this.display_width = display_width;
    }
    public void setCenter_x(int center_x) {
        this.center_x = center_x;
    }
    public void setCenter_y(int center_y){
        this.center_y = center_y;
    }
    private void drawGridLines(Graphics2D g){
        int numDivisions = 2;
        for (int i = 0; i <= numDivisions; i++){
            g.drawLine(i*display_width/numDivisions, 0, i*display_width/numDivisions, display_height);

        }
        for (int i = 0; i <= numDivisions; i++){
            g.drawLine(0, i*display_height/numDivisions, display_width, i*display_height/numDivisions);
        }
    }

}
