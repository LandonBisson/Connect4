package connect4;
import java.awt.*;

public class Board {
    private final static int NUM_ROWS = 7;
    private final static int NUM_COLUMNS = 7;
    private static Piece board[][] = new Piece[NUM_ROWS][NUM_COLUMNS];
    private static boolean backSpaceSameColor;

    public static void Animate(){
        for (int zrow=0;zrow<Board.getNumRows();zrow++)
        {
            for (int zcol=0;zcol<Board.getNumColumnss();zcol++)
            {
                if (board[zrow][zcol] != null)
                    board[zrow][zcol].animate();
                if(Piece.isAnimating()) {
                    if (board[zrow][zcol] != null) {
                        if (board[zrow][zcol].animateDown()) {
                            board[zrow][zcol] = null;
                        }
                    }
                }
            }
        }
        if(Piece.isAnimating()) {
            Piece.setAnimating(false);
            for (int zrow = Board.getNumRows() - 1; zrow > -1; zrow--) {
                for (int zcol = Board.getNumColumnss() - 1; zcol > -1; zcol--) {
                    if (board[zrow][zcol] != null) {
                        Piece.setAnimating(true);
                        break;
                    }
                }
                if (Piece.isAnimating())
                    break;
            }
        }
    }
    public static void getRidOfTokens(){
        //get a nice fall
        for(int i=0;i<NUM_ROWS;i++)
            setYdirRow(i, i*0.5);
        Piece.setAnimating(true);
    }
    public static boolean checkWin(int [] newPieceLocation, Color color){
        if(checkline(-1,-1,newPieceLocation,color)){
            return true;
        }
        else if(checkline(-1,0,newPieceLocation,color)){
            return true;
        }
        else if(checkline(-1,1,newPieceLocation,color)){
            return true;
        }
        else if(checkline(0,-1,newPieceLocation,color)){
            return true;
        }
        else if(checkline(0,1,newPieceLocation,color)){
            return true;
        }
        else if(checkline(1,-1,newPieceLocation,color)){
            return true;
        }
        else if(checkline(1,0,newPieceLocation,color)){
            return true;
        }
        else if(checkline(1,1,newPieceLocation,color)){
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean checkline(int xDir, int yDir, int []location, Color color){
        int counter=0;
        int connectWhat=4;
        //amount needed to be in a row is the middle test
        Highlight.clear();
        Highlight.addSpot(location[0],location[1]);
        for (int i=1;i<connectWhat;i++){
            int Testrow = location[0] + (yDir * i);
            int Testcolumn=location[1] + (xDir * i);
            if (Testcolumn < 0 || Testrow < 0)
                return false;
            if (Testcolumn > NUM_COLUMNS - 1 || Testrow > NUM_ROWS - 1)
                return false;
            if (board[Testrow][Testcolumn] == null) {
                return false;
            }
            Highlight.addSpot(Testrow,Testcolumn);
            if (connectWhat==4)
                reverseCheck(xDir,yDir,location,color);
            if (board[Testrow][Testcolumn].getColor() == color) {
                if(counter==2 && backSpaceSameColor) {
                    System.out.println("out 1");
                    backSpaceSameColor=false;
                    return true;
                }
                //last one
                counter++;
                System.out.println(color + " Counter: " + counter);
                System.out.println(Testrow + " , " + Testcolumn);
                System.out.println("--------");
                //only during diagonals
                if(xDir!=0 && yDir!=0 && counter==2 && backSpaceSameColor) {
                    backSpaceSameColor=false;
                    System.out.println("out 2");
                    return true;
                }
            }
            if (counter==3) {
                backSpaceSameColor=false;
                System.out.println("Connect 4!");
                return true;
            }
        }
        return false;

    }
    private static void reverseCheck(int xDir, int yDir, int []location, Color color){
        int reverseCheckRow=(location[0] - yDir);
        int reverseCheckCol=(location[1] - xDir);
        //mae sure that its not out of bounds
        if (!(reverseCheckCol<0 || reverseCheckRow<0 || reverseCheckCol>NUM_COLUMNS-1 || reverseCheckRow>NUM_ROWS-1)) {
            //is there a piece at reverse there
            if (board[reverseCheckRow][reverseCheckCol] != null) {
                //is it the correct color
                if (board[reverseCheckRow][reverseCheckCol].getColor() == color) {
                    Highlight.addSpot(reverseCheckRow,reverseCheckCol);
                    System.out.println(reverseCheckRow+" "+reverseCheckCol+" Connect breaked ");
                    backSpaceSameColor=true;
                }
                else backSpaceSameColor=false;
            }
            else backSpaceSameColor=false;
        }
        else backSpaceSameColor=false;
    }
    public static void highlightColumn(int[] highlight, Graphics2D g){
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;
        int column=highlight[1];
        g.setColor(Player.getCurrentPlayer().getColor());
        g.drawRect(Window.getX(column*xdelta), Window.getY(0), xdelta, ydelta*NUM_ROWS);
    }

    public static void Draw(Graphics2D g) {
//draw grid
        int ydelta = Window.getHeight2()/NUM_ROWS;
        int xdelta = Window.getWidth2()/NUM_COLUMNS;

        g.setColor(Color.black);
        for (int zi = 1;zi<NUM_ROWS;zi++)
        {
            g.drawLine(Window.getX(0),Window.getY(zi*ydelta),
                    Window.getX(Window.getWidth2()),Window.getY(zi*ydelta));
        }
        if (Player.getSpecificWin(Player.getPlayer1()) || Player.getSpecificWin(Player.getPlayer2())){
            Highlight.draw(g,xdelta, ydelta);
        }
        g.setColor(Color.black);
        for (int zi = 1;zi<NUM_COLUMNS;zi++)
        {
            g.drawLine(Window.getX(zi*xdelta),Window.getY(0),
                    Window.getX(zi*xdelta),Window.getY(Window.getHeight2()));
        }
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
        {
            for (int zcol=0;zcol<NUM_COLUMNS;zcol++)
            {
                if (board[zrow][zcol] != null)
                    board[zrow][zcol].draw(g, zrow, zcol,xdelta, ydelta);
            }
        }
    }
    public static void removeBottom(int[] location){
        int columnEffected=location[1];
        int rowRemoved=location[0];
        board[rowRemoved][columnEffected]=null;
        for(int row=0;row<NUM_ROWS;row++){
            if(board[row][columnEffected]!=null) {
                board[row][columnEffected].setFinalRowPos(board[row][columnEffected].getFinalRowPos()+Window.getWidth2()/NUM_COLUMNS);
            }
        }

    }
    public static boolean isSquareFull(int[] location){
        if (board[location[0]][location[1]]!=null)
            return true;
        return false;
    }
    public static int[] createNewPiece(int[] location, Color color){
        board[location[0]][location[1]]=new Piece(color);
        return (location);
    }
    public static void setYdirRow(int row, double _ydir){
        for (int col=0;col<Board.getNumColumnss();col++)
        {
            if(board[row][col]!=null)
                board[row][col].setYdir(_ydir);
        }
    }
    public static Color getSpaceColor(int row,int col){
        if (board[row][col]!=null)
            return board[row][col].getColor();
        return null;
    }
    public static int getNumRows(){
        return NUM_ROWS;
    }
    public static int getNumColumnss(){
        return NUM_COLUMNS;
    }
}


