import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Snorri
 * Date: 15.2.2014
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class State {
    //These are split into many arrays so that we can use the columns from the last state without making a new array.
    char[] col1 = new char[6];
    char[] col2 = new char[6];
    char[] col3 = new char[6];
    char[] col4 = new char[6];
    char[] col5 = new char[6];
    char[] col6 = new char[6];
    char[] col7 = new char[6];
    boolean currentPlayer = true;
    int lastMove; //the column that was dropped into, last move...
    int lastMoveY; //the row the disc of the last move landed in.

    public State()
    {

    }
    //Copy constructor.
    public State(State copiedState)
    {
        col1 = copiedState.col1;
        col2 = copiedState.col2;
        col3 = copiedState.col3;
        col4 = copiedState.col4;
        col5 = copiedState.col5;
        col6 = copiedState.col6;
        col7 = copiedState.col7;
        currentPlayer = copiedState.currentPlayer;
        lastMove = copiedState.lastMove;
        lastMoveY = copiedState.lastMoveY;
    }

    //Simply returns actions that are available, checks if the arrays are full.
    public List<Integer> getLegalActions()
    {
        ArrayList<Integer> datActionList = new ArrayList<Integer>();

        if(col1[5] == '\u0000')
            datActionList.add(1);
        if(col2[5] == '\u0000')
            datActionList.add(2);
        if(col3[5] == '\u0000')
            datActionList.add(3);
        if(col4[5] == '\u0000')
            datActionList.add(4);
        if(col5[5] == '\u0000')
            datActionList.add(5);
        if(col6[5] == '\u0000')
            datActionList.add(6);
        if(col7[5] == '\u0000')
            datActionList.add(7);

        return datActionList;
    }

    public State successorState(int i)
    {
        //Copies the last state.
        State newState = new State(this);
        //Sets the changes of the new state.
        newState.currentPlayer = !newState.currentPlayer;
        newState.lastMove = i;
        //Makes a cloned char[] from the old column, so that we can change this column and it doesn't change the last state.
        char[] newCol = getCol(i).clone();
        newState.setCol(i,newCol);
        for (int j = 0; j < newState.getCol(i).length; j++) {
            if(newState.getCol(i)[j] == '\u0000')
            {
                newState.getCol(i)[j] = currentPlayerChar();
                newState.lastMoveY = j;
                return newState;
            }
        }
        return newState;
    }

    //Returns the char used for current player, w for white, b for black(red)
    public char currentPlayerChar()
    {
        if(currentPlayer)
        {
            return 'w';
        }
        else
        {
            return 'b';
        }
    }
    //Opposite of currentPlayerChar
    public char lastPlayerChar()
    {
        if(currentPlayer)
        {
            return 'b';
        }
        else
        {
            return 'w';
        }
    }

    //Returns successor states, uses the getLegalActions and successorState(action) functions.
    public List<State> successorStates()
    {
        ArrayList<State> states = new ArrayList<State>();
        for(int action : getLegalActions())
        {
            states.add(successorState(action));
        }
        return states;
    }
    //Returns column number i
    public char[] getCol(int i)
    {
        switch(i)
        {
            case 1: return col1;
            case 2: return col2;
            case 3: return col3;
            case 4: return col4;
            case 5: return col5;
            case 6: return col6;
            case 7: return col7;
            default: return null;
        }
    }
    //Sets column number i
    public void setCol(int i,char[] newcol)
    {
        switch(i)
        {
            case 1: col1 = newcol;
                break;
            case 2: col2 = newcol;
                break;
            case 3: col3 = newcol;
                break;
            case 4: col4 = newcol;
                break;
            case 5: col5 = newcol;
                break;
            case 6: col6 = newcol;
                break;
            case 7: col7 = newcol;
                break;
        }
    }


    //Checks if this is a goal/terminal state.
    public boolean isGoal()
    {
        if(EW() >= 4)
        {
            return true;
        }
        if(NESW() >= 4)
        {
            return true;
        }
        if(NWSE() >= 4)
        {
            return true;
        }
        if(S() == 4)
        {
            return true;
        }

        return false;
    }



    //Counts how many discs are in a row from the lastmove, in the row NE -> SW
    private int NESW()
    {
        int count = 1;
        for(int i = 1; lastMove+i<= 7 && lastMoveY+i <= 5;i++){
            if(getCol(lastMove+i)[lastMoveY+i] == lastPlayerChar())
                count++;
            else
                break;
        }
        for(int i = 1; lastMove-i>= 1 && lastMoveY-i >= 0;i++){
            if(getCol(lastMove-i)[lastMoveY-i] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return count;
    }

    //Counts how many discs are in a row from the lastmove, in the row South
    private int S()
    {
        int count = 1;
        for(int i = 1; lastMoveY-i>= 0;i++){
            if(getCol(lastMove)[lastMoveY-i] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return count;
    }
    //Counts how many discs are in a row from the lastmove, in the row E -> W
    private int EW()
    {
        int count = 1;
        for(int i = 1; lastMove+i<= 7;i++){
            if(getCol(lastMove+i)[lastMoveY] == lastPlayerChar())
                count++;
            else
                break;
        }
        for(int i = 1; lastMove-i>= 1;i++){
            if(getCol(lastMove-i)[lastMoveY] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return count;
    }

    //Counts how many discs are in a row from the lastmove, in the row NW -> SE
    private int NWSE()
    {
        int count = 1;
        for(int i = 1; lastMove-i>= 1 && lastMoveY+i <= 5;i++){
            if(getCol(lastMove-i)[lastMoveY+i] == lastPlayerChar())
                count++;
            else
                break;
        }
        for(int i = 1; lastMove+i<= 7 && lastMoveY-i >= 0;i++){
            if(getCol(lastMove+i)[lastMoveY-i] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return count;
    }

    //This was used in our first evaluate function.
    private int points(int count)
    {
        int points = 0;
        if(lastMove==4)
            points+=3;
        if(count == 1)
            points += 1;
        if(count == 2)
            points += 4;
        if(count == 3)
            points += 8;
        if(count >= 4)
            return 100000;

        return points;
    }

    //Our terrible evaluate function.
/*    public int evaluate()
    {
        int valueTown = 0;

        valueTown = points(NWSE())+points(NESW())+points(S())+points(EW());//Math.max(Math.max(points(NESW()),points(NWSE())),Math.max(points(S()),points(EW())));

        return valueTown;
    }*/

    //Our evaluate function, simply adds up the values of the squares and returns it.
    public int evaluate()
    {
        //Using IBEF's weighting of squares, see: http://www.hiof.no/neted/upload/attachment/site/group12/Martin_Stenmark_Synthesizing_Board_Evaluation_Functions_for_Connect4_using_Machine_Learning_Techniques.pdf
        if(isGoal())
            return -Integer.MAX_VALUE;

        int trump = 0;
        //col1
        if(col1[0] == currentPlayerChar())
            trump+=3;
        else if(col1[0] == lastPlayerChar())
            trump-=3;
        if(col1[1] == currentPlayerChar())
            trump+=4;
        else if(col1[1] == lastPlayerChar())
            trump-=4;
        if(col1[2] == currentPlayerChar())
            trump+=5;
        else if(col1[2] == lastPlayerChar())
            trump-=5;
        if(col1[3] == currentPlayerChar())
            trump+=5;
        else if(col1[3] == lastPlayerChar())
            trump-=5;
        if(col1[4] == currentPlayerChar())
            trump+=4;
        else if(col1[4] == lastPlayerChar())
            trump-=4;
        if(col1[5] == currentPlayerChar())
            trump+=3;
        else if(col1[5] == lastPlayerChar())
            trump-=3;

        //col2
        if(col2[0] == currentPlayerChar())
            trump+=4;
        else if(col2[0] == lastPlayerChar())
            trump-=4;
        if(col2[1] == currentPlayerChar())
            trump+=6;
        else if(col2[1] == lastPlayerChar())
            trump-=6;
        if(col2[2] == currentPlayerChar())
            trump+=8;
        else if(col2[2] == lastPlayerChar())
            trump-=8;
        if(col2[3] == currentPlayerChar())
            trump+=8;
        else if(col2[3] == lastPlayerChar())
            trump-=8;
        if(col2[4] == currentPlayerChar())
            trump+=6;
        else if(col2[4] == lastPlayerChar())
            trump-=6;
        if(col2[5] == currentPlayerChar())
            trump+=4;
        else if(col2[5] == lastPlayerChar())
            trump-=4;

        //col3
        if(col3[0] == currentPlayerChar())
            trump+=5;
        else if(col3[0] == lastPlayerChar())
            trump-=5;
        if(col3[1] == currentPlayerChar())
            trump+=8;
        else if(col3[1] == lastPlayerChar())
            trump-=8;
        if(col3[2] == currentPlayerChar())
            trump+=11;
        else if(col3[2] == lastPlayerChar())
            trump-=11;
        if(col3[3] == currentPlayerChar())
            trump+=11;
        else if(col3[3] == lastPlayerChar())
            trump-=11;
        if(col3[4] == currentPlayerChar())
            trump+=8;
        else if(col3[4] == lastPlayerChar())
            trump-=8;
        if(col3[5] == currentPlayerChar())
            trump+=5;
        else if(col3[5] == lastPlayerChar())
            trump-=5;

        //col4
        if(col4[0] == currentPlayerChar())
            trump+=7;
        else if(col4[0] == lastPlayerChar())
            trump-=7;
        if(col4[1] == currentPlayerChar())
            trump+=10;
        else if(col4[1] == lastPlayerChar())
            trump-=10;
        if(col4[2] == currentPlayerChar())
            trump+=13;
        else if(col4[2] == lastPlayerChar())
            trump-=13;
        if(col4[3] == currentPlayerChar())
            trump+=13;
        else if(col4[3] == lastPlayerChar())
            trump-=13;
        if(col4[4] == currentPlayerChar())
            trump+=10;
        else if(col4[4] == lastPlayerChar())
            trump-=10;
        if(col4[5] == currentPlayerChar())
            trump+=7;
        else if(col4[5] == lastPlayerChar())
            trump-=7;

        //col5
        if(col5[0] == currentPlayerChar())
            trump+=5;
        else if(col5[0] == lastPlayerChar())
            trump-=5;
        if(col5[1] == currentPlayerChar())
            trump+=8;
        else if(col5[1] == lastPlayerChar())
            trump-=8;
        if(col5[2] == currentPlayerChar())
            trump+=11;
        else if(col5[2] == lastPlayerChar())
            trump-=11;
        if(col5[3] == currentPlayerChar())
            trump+=11;
        else if(col5[3] == lastPlayerChar())
            trump-=11;
        if(col5[4] == currentPlayerChar())
            trump+=8;
        else if(col5[4] == lastPlayerChar())
            trump-=8;
        if(col5[5] == currentPlayerChar())
            trump+=5;
        else if(col5[5] == lastPlayerChar())
            trump-=5;


        //col6
        if(col6[0] == currentPlayerChar())
            trump+=4;
        else if(col6[0] == lastPlayerChar())
            trump-=4;
        if(col6[1] == currentPlayerChar())
            trump+=6;
        else if(col6[1] == lastPlayerChar())
            trump-=6;
        if(col6[2] == currentPlayerChar())
            trump+=8;
        else if(col6[2] == lastPlayerChar())
            trump-=8;
        if(col6[3] == currentPlayerChar())
            trump+=8;
        else if(col6[3] == lastPlayerChar())
            trump-=8;
        if(col6[4] == currentPlayerChar())
            trump+=6;
        else if(col6[4] == lastPlayerChar())
            trump-=6;
        if(col6[5] == currentPlayerChar())
            trump+=4;
        else if(col6[5] == lastPlayerChar())
            trump-=4;

        //col7
        if(col7[0] == currentPlayerChar())
            trump+=3;
        else if(col7[0] == lastPlayerChar())
            trump-=3;
        if(col7[1] == currentPlayerChar())
            trump+=4;
        else if(col7[1] == lastPlayerChar())
            trump-=4;
        if(col7[2] == currentPlayerChar())
            trump+=5;
        else if(col7[2] == lastPlayerChar())
            trump-=5;
        if(col7[3] == currentPlayerChar())
            trump+=5;
        else if(col7[3] == lastPlayerChar())
            trump-=5;
        if(col7[4] == currentPlayerChar())
            trump+=4;
        else if(col7[4] == lastPlayerChar())
            trump-=4;
        if(col7[5] == currentPlayerChar())
            trump+=3;
        else if(col7[5] == lastPlayerChar())
            trump-=3;

        return trump;
    }

    //ToString, to be able to debug our code :'(
    public String toString()
    {
        String s = "";
        for (int i = 5; i>= 0; i--)
        {
            s+=i+": "+"["+getCol(1)[i]+"]"+"["+getCol(2)[i]+"]"+"["+getCol(3)[i]+"]"+"["+getCol(4)[i]+"]"+"["+getCol(5)[i]+"]"+"["+getCol(6)[i]+"]"+"["+getCol(7)[i]+"]\n";
        }
        return s;
    }
}
