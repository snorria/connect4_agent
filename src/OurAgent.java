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
    /*
        init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "WHITE" or "RED" and playclock is the number of seconds after which nextAction must return.
    */
    public void init(String role, int playclock) {
        this.role = role;
        this.playclock = playclock;
        myTurn = !role.equals("WHITE");
        //Initialization
        currentState = new State();
        currentState.currentPlayer = myTurn;
    }

    // lastDrop is 0 for the first call of nextAction (no action has been executed),
    // otherwise it is a number n with 0<n<8 indicating the column that the last piece was dropped in by the player whose turn it was
    public String nextAction(int lastDrop) {
        // TODO: 1. update your internal world model according to the action that was just executed
        currentState = currentState.successorState(lastDrop);
        myTurn = !myTurn;
        // TODO: 2. run alpha-beta search to determine the best move

        if (myTurn) {
            return "(DROP " + (random.nextInt(7)+1) + ")";
        } else {
            return "NOOP";
        }
    }

}