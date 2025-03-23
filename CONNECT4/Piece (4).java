package connect4;

import java.awt.*;

public class Piece {
    private Color color;
    private double ydir;
    private int finalRowPos;
    private int finalRow;
    private int numBounces;
    private double rowPos;
    private static boolean AllAnimating;

    Piece(Color _color)
    {
        color = _color;
        //row pos is the top row in cord mode
        rowPos=Window.getY(0);
        ydir=10;
        numBounces=0;
        AllAnimating=false;
    }
    public Color getColor()
    {
        return (color);
    }
    public void animate(){
        finalRowPos=Window.getY(0)+finalRow*Window.getHeight2()/Board.getNumRows();
        if(AllAnimating){
            animateDown();
        }
        else if (rowPos<finalRowPos){
            rowPos+=ydir;
            if (rowPos>finalRowPos){
                numBounces++;
                ydir= -ydir;
                rowPos+=ydir;
            }
            if (numBounces<5) {
                if (ydir < 0)
                    //0.75
                    //the higher this number the faster the ups go away
                    ydir += 2.75;
                else {
                    //0.2
                    //gravity
                    ydir += 0.2;
                }
            }
            else {
                rowPos=finalRowPos;
            }
        }
    }
    public boolean animateDown(){
        if (rowPos>Window.getHeight2()+Window.getWidth2()/Board.getNumColumnss()){
            return true;
        }

        rowPos+=ydir;
        //ydir += 0.1*finalRowPos/((double) Window.getHeight2() /Board.getNumRows());
        ydir += 0.2;
        return false;
    }
    public void draw(Graphics2D g,int row,int column,int xdelta,int ydelta) {
        finalRow=row;
        g.setColor(color);
        g.fillOval(Window.getX(0)+column*xdelta, (int)rowPos, xdelta, ydelta);
        g.setColor(Color.black);
        g.drawOval(Window.getX(0)+column*xdelta, (int)rowPos, xdelta, ydelta);
    }
    public void setYdir(double _ydir){
        ydir=_ydir;
    }
    public void setFinalRowPos(int _finalrowpos){
        finalRowPos=_finalrowpos;
    }
    public int getFinalRowPos(){
        return finalRowPos;
    }
    public static void setAnimating(boolean _Animating){
        AllAnimating=_Animating;
    }
    public static boolean isAnimating() {
        return AllAnimating;
    }
}