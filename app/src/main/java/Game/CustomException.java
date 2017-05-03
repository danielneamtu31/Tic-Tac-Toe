package Game;

/**
 * Created by Dani on 4/18/2017.
 */

public class CustomException extends Exception{
    private int [] lastMove;
    private Option player;

    public CustomException(String s, int[] lastMove, Option player)
    {
        super(s);
        this.lastMove = lastMove;
        this.player = player;
    }

    public int[] getLastMove()
    {
        return lastMove;
    }

    public Option getPlayer()
    {
        return this.player;
    }

}
