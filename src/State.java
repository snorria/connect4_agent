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

    public State()
    {

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
        State newState = new State();
        newState.currentPlayer = !newState.currentPlayer;
        newState.lastMove = i;
        newState.setCol(i,getCol(i).clone());
        for (int j = 0; j < newState.getCol(i).length; j++) {
            if(newState.getCol(i)[j] == '\u0000')
            {
                if(currentPlayer)
                {
                    newState.getCol(i)[j] = 'w';
                }
                else
                {
                    newState.getCol(i)[j] = 'b';
                }
            }
        }
        return newState;
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



    public boolean isGoal()
    {
        return false;
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
}
