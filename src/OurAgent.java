import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Snorri
 * Date: 15.2.2014
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */
public class OurAgent implements Agent
{
    private Random random = new Random();

    private String role;
    private int playclock;
    private boolean myTurn;
    private State currentState;
    private static long startTime;
    private static int MAX_TIME;
    /*
        init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "WHITE" or "RED" and playclock is the number of seconds after which nextAction must return.
    */
    public void init(String role, int playclock) {
        this.MAX_TIME = (playclock * 1000000000) - 20000;
        this.role = role;
        this.playclock = playclock;
        myTurn = role.equals("WHITE");
        //Initialization
        currentState = new State();
        currentState.currentPlayer = myTurn;
    }

    // lastDrop is 0 for the first call of nextAction (no action has been executed),
    // otherwise it is a number n with 0<n<8 indicating the column that the last piece was dropped in by the player whose turn it was
    public String nextAction(int lastDrop) {
        // TODO: 1. update your internal world model according to the action that was just executed
        System.out.println(currentState.toString());
        if(lastDrop != 0)
        {
            State drasl = currentState.successorState(lastDrop);
            currentState = drasl;
            myTurn = !myTurn;
        }
        if (myTurn) {
            // TODO: 2. run alpha-beta search to determine the best move
            int move = 0;
            startTime = System.nanoTime();

            try
            {
                int depth = 1;
                while (true)
                {
                    System.out.println("NEGAMAX START, Depth:" + depth);
                    move = AlphaBetaNegaMax(depth, currentState, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                    System.out.println("NEGAMAX END, Depth:" + depth);
                    depth++;
                }
            }
            catch (TimeOverExeption e)
            {
                System.out.println("Exeption: " + e.getMessage() + "\nMove made: "+ move);
            }

            return "(DROP " + move + ")";
        }
        else
        {
            return "NOOP";
        }
    }

    private static int bestmove;
    private int AlphaBetaNegaMax (int depth, State s, int alpha, int beta, boolean first) throws TimeOverExeption
    {
        long timeNow = System.nanoTime();
        //System.out.println("TIME: "+(timeNow - startTime) +" > " + MAX_TIME );
        if((timeNow - startTime) > MAX_TIME)
        {
            throw new TimeOverExeption("Time Over!");
        }

        if ( s.isGoal() || depth <= 0 )
        {
            if(!s.currentPlayer){
                return s.evaluate();
            }
            else
                return -s.evaluate();
        }

        int bestValue = Integer.MIN_VALUE;

        for(State successor : s.successorStates())
        {
            int value;
            //if (first)
            //    value = AlphaBetaNegaMax((depth - 1), successor, -beta, -alpha, false); //Note: switch and negate bounds
            //else
            value = -AlphaBetaNegaMax((depth - 1), successor, -beta, -alpha, false); //Note: switch and negate bounds


            //System.out.println("Depth: " + depth + " Move: " + successor.lastMove + " Value: " + value);

            if(first)
            {
                if(value > bestValue)
                {
                    bestmove = successor.lastMove;
                }
            }

            bestValue = Math.max(value, bestValue);

            if ( bestValue > alpha )
            {
                alpha = bestValue; //adjust the lower bound
                //if ( alpha >= beta ) break; //beta cutoff
            }
        }

        if(first)
        {
            System.out.println("*************************************Best move is: " + bestmove);
            return bestmove;
        }
        return bestValue;
    }






}