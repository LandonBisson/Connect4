package connect4;

import java.awt.*;
import java.util.ArrayList;

public class Highlight {
    private static int counterColor;
    static ArrayList<int[]> currentWinner= new ArrayList<int[]>();
    public static void draw(Graphics2D g, int xdelta, int ydelta) {
        if(counterColor<-Connect4.getFrameRate()/2)
            counterColor=Connect4.getFrameRate()/2;
        for (int i=0;i<currentWinner.size();i++) {
            if(counterColor<=0)
                g.setColor(Color.green);
            else{
                g.setColor(Color.magenta);
            }
            g.fillRect(Window.getX(0) +currentWinner.get(i)[1]  * xdelta, Window.getY(0) + currentWinner.get(i)[0] * ydelta, xdelta, ydelta);
        }
    }
    public static void addSpot(int row, int column) {
        currentWinner.add(new int[] {row,column});
    }
    public static void clear() {
        currentWinner.clear();
    }
    public static void counterMinus(){
        counterColor--;
    }
}
