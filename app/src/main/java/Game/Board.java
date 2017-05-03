package Game;

/**
 * Created by Dani on 4/15/2017.
 */
public class Board {

    private Option[][] board;

    public Board()
    {
        board = new Option[3][3];
        for(int i=0; i<3 ;i++)
            for(int j=0; j<3; j++)
                board[i][j] = Option.EMPTY;
    }

    public Board(Board board)
    {
        this.board = new Option[3][3];

        for(int i=0; i<3 ;i++)
            for(int j=0; j<3; j++)
                this.board[i][j] = board.getMove(i,j);
    }

    public Option getMove(int i, int j){
        return board[i][j];
    }

    public void move(int i, int j, Option option)
    {
        if(board[i][j] == Option.EMPTY)
            board[i][j] = option;
    }

    public boolean canMove(int i, int j)
    {
        return board[i][j] == Option.EMPTY;
    }

    public boolean checkWin(Option option)
    {
        if(checkDiagonal(option,3) >= 1)
            return true;
        if(checkColumn(option,3) >= 1)
            return true;
        if(checkRow(option,3) >= 1)
            return true;
        return false;
    }

    private int checkDiagonal(Option option, int numberOfTimes) {
        int currentNumberOfTimes = 0;
        int count = 0;

        for(int i=0; i<3; i++)
            if(board[i][i] == option)
                count++;
            else
            if(board[i][i] != Option.EMPTY)
                count--;

        if(count == numberOfTimes )
            currentNumberOfTimes++;

        count = 0;
        for(int i=0; i<3; i++)
            if(board[i][2-i] == option)
                count++;
            else
            if(board[i][2-i] != Option.EMPTY)
                count--;

        if(count == numberOfTimes)
            currentNumberOfTimes++;

        return currentNumberOfTimes;
    }

    public int checkScore(Option option)
    {
        Option opponent;
        if(option == Option.X)
            opponent = Option.O;
        else
            opponent = Option.X;

        if(checkWin(opponent) == true) //if the opponent win on the current board, function will return -600
            return -600;
        if(checkWin(option) == true) //if the player win, function will return 600
            return 600;

        //check if opponent has one row and one column with 2 options marked
        if(checkRow(opponent,2) == 1 && checkColumn(opponent,2) == 1)
            return -300;
        if(checkRow(option,2) == 1 && checkColumn(option,2) == 1)
            return 300;

        //check if opponent has 2 diagonals with same options marked
        if(checkDiagonal(opponent,2) == 2)
            return -300;
        if(checkDiagonal(option,2) == 2)
            return 300;

        if(checkColumn(opponent,2) == 1)
            return -200;
        if(checkRow(opponent,2) == 1)
            return -200;
        if(checkDiagonal(opponent,2) == 1)
            return -200;

        if(checkColumn(option,2) == 1)
            return 200;
        if(checkRow(option,2) == 1)
            return 200;
        if(checkDiagonal(option,2) == 1)
            return 200;

        if(checkColumn(opponent,1) == 1)
            return -10;
        if(checkRow(opponent,1) == 1)
            return -10;

        if(checkColumn(option,1) == 1)
            return 10;
        if(checkRow(option,1) == 1)
            return 10;
        return 0;


    }


    private int checkColumn(Option option,int numberOfTimes) {
        int numberOfColumns=0;
        int count;

        for(int j=0; j<3; j++)
        {
            count = 0;
            for(int i=0; i<3; i++)
                if(board[i][j] == option)
                    count++;
                else
                if(board[i][j] != Option.EMPTY)
                    count--;

            if(count == numberOfTimes)
                numberOfColumns++;
        }
        return numberOfColumns;
    }

    private int checkRow(Option option, int numberOfTimes) {
        int count;
        int numberOfRows=0;
        for(int i=0; i<3; i++)
        {
            count = 0;
            for(int j=0; j<3; j++)
                if(board[i][j] == option)
                    count++;
                else
                if(board[i][j] != Option.EMPTY)
                    count--;

            if(count == numberOfTimes)
                numberOfRows++;
        }
        return numberOfRows;
    }

    @Override
    public String toString() {
        String s = new String();
        for(int i=0; i<3;i++)
        {
            for(int j =0 ;j<3;j++) {
                if (board[i][j] == Option.X)
                    s += "x ";
                if(board[i][j]==Option.O)
                    s+="o ";
                if(board[i][j]==Option.EMPTY)
                    s+="- ";
            }
            s+= System.lineSeparator();
        }
        return s;
    }


    public boolean isFull()
    {
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(board[i][j].equals(Option.EMPTY))
                    return false;

        return true;
    }
}

