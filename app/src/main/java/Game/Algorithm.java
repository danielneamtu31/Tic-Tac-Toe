package Game;

/**
 * Created by Dani on 4/17/2017.
 */

/**
 * Created by Dani on 4/15/2017.
 */
public class Algorithm {

    private Option computer, player;
    private int level;
    Board board;

    public Algorithm(Option computer,Option player, int level)
    {
        this.computer = computer;
        this.player = player;
        this.level = level;
        board = new Board();
    }

    public void setTable(Board b)
    {
        board = b;
    }

    public int minMaxAlgorithm(Board board, int currentNode, boolean isMax)
    {
        if(board.checkScore(this.computer)==600)
        {
            return 600;
        }
        if(board.checkScore(this.computer)==-600)
        {
            return -600;
        }

        if(currentNode == level)
        {
            return board.checkScore(this.computer);
        }
        else
        if(currentNode < level)
        {

            if(isMax)
            {
                //opponent is about to move
                int result = -1000;
                for(int i=0; i<3; i++)
                    for(int j=0; j<3; j++)
                        if(board.canMove(i,j)) {
                            Board newMove = new Board(board);
                            newMove.move(i, j, computer);
                            result = Math.max(result,minMaxAlgorithm(newMove,currentNode+1, !isMax));
                        }
                return result;


            }
            else
            {
                int result = 1000;
                for(int i=0; i<3; i++)
                    for(int j=0; j<3; j++)
                        if(board.canMove(i,j)) {
                            Board newMove = new Board(board);
                            newMove.move(i, j, this.player);
                            result = Math.min(result, minMaxAlgorithm(newMove,currentNode+1,!isMax));
                        }
                return result;
            }

        }
        return 0;
    }


    public int[] makeBest() throws CustomException{
        int best = -1000;
        Board finalMove = new Board();
        int iBest = -1, jBest = -1;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (board.canMove(i, j) == true) {
                    Board newMove = new Board(board);
                    newMove.move(i, j, computer);
                    int val = minMaxAlgorithm(newMove, 0, true);

                    if (newMove.checkScore(this.player) == -600) {
                        throw new CustomException("You losed!", new int[] {i,j},this.computer);
                    }
                    if (newMove.isFull()) {
                       throw new CustomException("Tie!", new int[] {i, j},Option.EMPTY);
                    }

                    if (val >= best) {
                        best = val;
                        finalMove = newMove;
                        iBest = i;
                        jBest = j;
                    }
                }
            }
        board = new Board(finalMove);
        int[] move = {iBest, jBest};
        return move;

    }

    public void move (int move[]) throws CustomException
    {
        board.move(move[0],move[1],this.player);
        if (board.checkScore(this.computer) == -600 ) {
            throw new CustomException("You won!", new int[] {move[0],move[1]},this.player);
        }
    }

    //translate a move with this format {x,y} in a grid tag (0-8)
    public int moveToTag(int [] move)
    {
        if (move[1] == 0)
            return move[0];
        if (move[1] == 1)
            return move[0] + 3;
        if (move[1] == 2)
            return move[0] + 6;
        return -1;
    }
    public boolean isFull(){
        return board.isFull();
    }

    //translate a tag to a move
    public int[] tagToMove(Object tag) {
        int position = (int) tag;
        int[] move = new int[2];
        if (position <= 2) {
            move[0] = position;
            move[1] = 0;
        } else if (position <= 5) {
            move[0] = position - 3;
            move[1] = 1;
        } else  {
            move[0] = position - 6;
            move[1] = 2;
        }

        return move;
    }

    public Option getComputer() {
        return computer;
    }

    public Option getPlayer() {
        return player;
    }
}
