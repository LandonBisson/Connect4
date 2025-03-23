package connect4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Connect4 extends JFrame implements Runnable {
    boolean animateFirstTime = true;
    Image image;
    Graphics2D g;
    static int MouseX;
    static int MouseY;
    static int Countdown;
    static final int frameRate = 50;
    


    public static void main(String[] args) {
        Connect4 frame = new Connect4();
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Connect4() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                if (e.BUTTON1 == e.getButton() ) {
                    int mouseycheck=Window.getYNormal(0);
                    if (MouseX<100 && MouseX>-100 &&
                            MouseY>mouseycheck-25 &&
                            MouseY<mouseycheck+75){
                        reset();
                    }
                    else if(Countdown<0){
                        if (Player.getSpecificWin(Player.getPlayer1()) || Player.getSpecificWin(Player.getPlayer2()))
                            return;
                        if (Piece.isAnimating())
                            return;
                        if (!Board.isSquareFull(getSquare(e.getX(), e.getY()))) {
                            Countdown= (int) (0.25*frameRate);
                            if (Board.checkWin((Board.createNewPiece(getSquare(e.getX(), e.getY()), Player.getCurrentPlayer().getColor())), Player.getCurrentPlayer().getColor())) {
                                if (Player.getCurrentPlayer().getColor() == Color.red) {
                                   Player.getPlayer1().setWin(true);
                                    Player.getCurrentPlayer().addScore(1);
                                }
                                else {
                                    Player.getPlayer2().setWin(true);
                                    Player.getCurrentPlayer().addScore(1);
                                }
                            }
                            Player.switchCurrentPlayer();
                        }
                    }
                }
                //right click
                if (Player.getSpecificWin(Player.getPlayer1()) || Player.getSpecificWin(Player.getPlayer2()))
                    return;
                if (Piece.isAnimating())
                    return;
                if (e.BUTTON3 == e.getButton()) {
                    Board.removeBottom(getSquare(e.getX(), e.getY()));
                    Player.switchCurrentPlayer();
                }
                repaint();
            }
        });


        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {

                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                repaint();
                MouseX=e.getX();
                MouseY=e.getY();
            }
        });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_UP == e.getKeyCode()) {
                } else if (e.VK_DOWN == e.getKeyCode()) {
                } else if (e.VK_LEFT == e.getKeyCode()) {
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                } else if (e.VK_ESCAPE == e.getKeyCode()) {
                    reset();
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
    ////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
    ////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
    ////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background

        g.setColor(Color.gray);
        g.fillRect(0, 0, Window.xsize, Window.ysize);

        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};
//fill border
        g.setColor(Color.darkGray);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.black);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        Board.Draw(g);
        if(g!=null)
            Board.highlightColumn(getSquare(MouseX, MouseY),g);
        drawFillRect(g,Window.getX(0), Window.getYNormal(-10), 0, 2, 1.5,Color.black);
        drawFillRect(g,Window.getX(0), Window.getYNormal(-10), 0, 2, 1.5,Color.black);
        g.setColor(Color.white);
        g.setFont (new Font ("Arial",Font.PLAIN, 25));
        g.drawString("Reset",0,Window.getYNormal(-10));

        g.setFont (new Font ("Arial",Font.PLAIN, 17));
        g.setColor(Color.black);
        g.drawString("REDS SCORE: "+Player.getPlayer1().getScore(), Window.getWidth2()/10-1,Window.getYNormal(Window.getHeight2()));
        g.setColor(Color.red);
        g.drawString("REDS SCORE: "+Player.getPlayer1().getScore(), Window.getWidth2()/10,Window.getYNormal(Window.getHeight2()));
        g.setColor(Color.black);
        g.drawString("YELLOWS SCORE: "+Player.getPlayer2().getScore(), 4*Window.getWidth2()/5-11,Window.getYNormal(Window.getHeight2()));
        g.setColor(Color.yellow);
        g.drawString("YELLOWS SCORE: "+Player.getPlayer2().getScore(), 4*Window.getWidth2()/5-10,Window.getYNormal(Window.getHeight2()));
        if (Player.getSpecificWin(Player.getPlayer2())){
            g.setColor(Color.yellow);
            g.setFont (new Font ("Arial",Font.PLAIN, 40));
            g.drawString("YELLOW WIN", Window.getWidth2()/2-72,Window.getYNormal(Window.getHeight2()/2));
            g.setColor(Color.black);
            g.drawString("YELLOW WIN", Window.getWidth2()/2-70,Window.getYNormal(Window.getHeight2()/2));
        }
        else if(Player.getSpecificWin(Player.getPlayer1())){
            g.setColor(Color.red);
            g.setFont (new Font ("Arial",Font.PLAIN, 40));
            g.drawString("RED WIN", Window.getWidth2()/2-52,Window.getYNormal(Window.getHeight2()/2));
            g.setColor(Color.black);
            g.drawString("RED WIN", Window.getWidth2()/2-50,Window.getYNormal(Window.getHeight2()/2));
        }
        else{
            if(Player.getCurrentPlayer().getColor()==Color.red){
                g.setColor(Color.black);
                g.setFont (new Font ("Arial",Font.PLAIN, 30));
                g.drawString("REDS TURN", Window.getWidth2()/2-58,Window.getYNormal(Window.getHeight2()));
                g.setColor(Color.red);
                g.drawString("REDS TURN", Window.getWidth2()/2-55,Window.getYNormal(Window.getHeight2()));
            }
            else{
                g.setColor(Color.black);
                g.setFont (new Font ("Arial",Font.PLAIN, 30));
                g.drawString("YELLOWS TURN", Window.getWidth2()/2-98,Window.getYNormal(Window.getHeight2()));
                g.setColor(Color.YELLOW);
                g.drawString("YELLOWS TURN", Window.getWidth2()/2-95,Window.getYNormal(Window.getHeight2()));
            }
        }

        gOld.drawImage(image, 0, 0, null);
    }

    ////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = (1.0 /frameRate);    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////
    public void reset() {
        Player.Reset();
        Board.getRidOfTokens();
    }
    /////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }

            reset();

        }
        Highlight.counterMinus();
        Countdown--;
        Board.Animate();
    }

    ////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    private void drawFillRect(Graphics2D g, int _xpos, int _ypos, double rot, double xscale, double yscale,Color color) {
        g.translate(_xpos,_ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        g.setColor(color);
        g.fillRect(-25,-25, 50,50);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-_xpos,-_ypos);

    }
    public int [] getSquare(int mouseX,int mouseY){
        int row = 0;
        int column = 0;
        int NumRows= Board.getNumRows();
        int NumCols= Board.getNumRows();
        int ydelta = Window.getHeight2()/NumRows;
        int xdelta = Window.getWidth2()/NumCols;

        row=(mouseY-Window.getY(0))/ydelta;
        column=(mouseX-Window.getX(0))/xdelta;
        if(row>=NumRows)
            row=NumRows-1;
        if(column>=NumCols)
            column=NumCols-1;
        for (int low=0;low<NumRows;low++)
        {
            if (!Board.isSquareFull(new int[]{low,column})) {
                row=low;
            }
            else
                break;
        }
        return (new int[]{row,column});
    }
    public static int getFrameRate(){
        return frameRate;
    }

}




