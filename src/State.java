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
    char[] col1 = new char[6];
    char[] col2 = new char[6];
    char[] col3 = new char[6];
    char[] col4 = new char[6];
    char[] col5 = new char[6];
    char[] col6 = new char[6];
    char[] col7 = new char[6];
    boolean currentPlayer = true;
    int lastMove;
    int lastMoveY;

    public State()
    {

    }
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
        System.out.println(i);
        State newState = new State(this);
        newState.currentPlayer = !newState.currentPlayer;
        newState.lastMove = i;
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


    public List<State> successorStates()
    {
        ArrayList<State> states = new ArrayList<State>();
        for(int action : getLegalActions())
        {
            states.add(successorState(action));
        }
        return states;
    }

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
    private int EW()
    {
        int count = 1;
        for(int i = 1; lastMove+i<= 7;i++){
            if(getCol(lastMove+i)[lastMoveY] == lastPlayerChar())
                count++;
            else
                break;
        }
        for(int i = 1; lastMove-i>= 0;i++){
            if(getCol(lastMove-i)[lastMoveY] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return count;
    }
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

/*
    private int NE()
    {
        int count = 0;
        for(int i = 1; lastMove+i<= 7 && lastMoveY+i <= 5;i++){
            if(getCol(lastMove+i)[lastMoveY+i] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return 0;
    }


    private int E()
    {
        int count = 0;
        for(int i = 1; lastMove+i<= 7;i++){
            if(getCol(lastMove+i)[lastMoveY] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return 0;
    }


    private int SE()
    {
        int count = 0;
        for(int i = 1; lastMove+i<= 7 && lastMoveY-i >= 0;i++){
            if(getCol(lastMove+i)[lastMoveY-i] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return 0;
    }


    private int S()
    {
        int count = 0;
        for(int i = 1; lastMoveY-i>= 0;i++){
            if(getCol(lastMove)[lastMoveY-i] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return 0;
    }


    private int SW()
    {
        int count = 0;
        for(int i = 1; lastMove-i>= 1 && lastMoveY-i >= 0;i++){
            if(getCol(lastMove-i)[lastMoveY-i] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return 0;
    }


    private int W()
    {
        int count = 0;
        for(int i = 1; lastMove-i>= 0;i++){
            if(getCol(lastMove-i)[lastMoveY] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return 0;
    }


    private int NW()
    {
        int count = 0;
        for(int i = 1; lastMove-i>= 1 && lastMoveY+i <= 5;i++){
            if(getCol(lastMove-i)[lastMoveY+i] == lastPlayerChar())
                count++;
            else
                return count;
        }
        return 0;
    }
*/
    private int points(int count)
    {
        if(count == 1)
            return 2;
        if(count == 2)
            return 4;
        if(count == 3)
            return 8;
        if(count >= 4)
            return Integer.MAX_VALUE;

        return 0;
    }

    public int evaluate()
    {
        int valueTown = 0;

        valueTown = Math.max(Math.max(points(NESW()),points(NWSE())),Math.max(points(S()),points(EW())));

        return valueTown;
    }

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
