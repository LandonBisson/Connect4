package connect4;
import java.awt.Color;
public class Player {
    final private static int numPlayers=2;
    private static Player players[] = new Player[numPlayers];
    private static Player currentPlayer;
    private Color color;
    private int Score;
    private boolean win;
    //Class methods.
    public static void Reset() {
//If we have not created any instances yet, create the instances of the 2 players.
//Have the first player be the current player.
        if (players[0] == null)
        {
            players[0]=new Player(Color.red);
            players[1]=new Player(Color.yellow);
        }
        for (Player ptr : players){
            ptr.setWin(false);
        }
        currentPlayer=players[0];

    }
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
    public void setWin(boolean _win) {
        win=_win;
    }
    public static boolean getSpecificWin(Player _player) {
        return _player.getWin();
    }
    public boolean getWin() {
        return win;
    }
    public void addScore(int _score) {
        Score+=_score;
    }
    public int getScore() {
        return Score;
    }
    public static void switchCurrentPlayer() {
        if(currentPlayer==players[0]){
            currentPlayer=players[1];
        }
        else{
            currentPlayer=players[0];
        }
    }
    public static Player getPlayer1() {
        return players[0];
    }
    public static Player getPlayer2() {
        return players[1];
    }

    public Player() {
        //Leave default constructor empty.
    }
    public Player(Color _color) {
        color=_color;
    }
    //accessor methods.
    public Color getColor() {
        return color;
    }
}
