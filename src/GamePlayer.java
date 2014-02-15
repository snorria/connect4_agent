import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;


public class GamePlayer extends NanoHTTPD {
	
	private Agent agent;

	public GamePlayer(int port, Agent agent) throws IOException {
		super(port);
		this.agent = agent;
	}

	/**
	 * this method is called when a new match begins
	 */
	protected void commandStart(String msg) {
		// msg="(START <MATCH ID> <ROLE> <GAME DESCRIPTION> <STARTCLOCK> <PLAYCLOCK>)
		// e.g. msg="(START tictactoe1 white ((role white) (role black) ...) 1800 120)" means:
		//       - the current match is called "match0815"
		//       - your role is "white",
		//       - after at most 1800 seconds, you have to return from the commandStart method
		//       - for each move you have 120 seconds
		String role = null;
		int playclock = 0;
		Matcher m=Pattern.compile("\\s*\\(\\s*START\\s+([^\\s]+)\\s+([^\\s]+).*\\s([0-9]+)\\s+([0-9]+)\\s*\\)\\s*\\z", Pattern.DOTALL).matcher(msg);
		if (m.lookingAt()) {
			role = m.group(2);
			playclock = Integer.parseInt(m.group(4));
			System.out.println("role: " + role + ", playclock: " + playclock);
		} else {
			System.err.println("unrecognized message format:" + msg);
			System.exit(-1);
		}
		agent.init(role, playclock);
	}

	/**
	 * this method is called once for each move
	 * @return the move of this player
	 */
	protected String commandPlay(String msg){
		// msg="(PLAY <MATCHID> <LASTMOVES>)"
		int lastDrop = 0;
		try{
			Matcher m=Pattern.compile("\\(\\s*DROP\\s+([0-9]+)\\s*\\)", Pattern.DOTALL).matcher(msg);
			if (m.find()) {
				lastDrop = Integer.parseInt(m.group(1));
			} else {
				lastDrop = 0;
			}
		}catch(Exception ex){
			System.err.println("Pattern to detect moves does not match!");
			ex.printStackTrace();
		}
		return agent.nextAction(lastDrop);
	}

	/**
	 * this method is called if the match is over
	 */
	protected void commandStop(String msg){
		// msg="(STOP <MATCH ID> <JOINT MOVE>)
		
		// TODO:
		//    - clean up the GamePlayer for the next match
		//    - be happy if you have won, think about what went wrong if you have lost ;-)
	}

	public Response serve( String uri, String method, Properties header, Properties parms, String data )
	{
		try{
			String response_string=null;
			if(data!=null){
				data=data.toUpperCase();
				System.out.println(DateFormat.getTimeInstance(DateFormat.FULL).format(Calendar.getInstance().getTime()));
				System.out.println("Command: " + data);
				String command=getCommand(data);
				if(command==null){
					throw(new IllegalArgumentException("Unknown message format"));
				}else if(command.equals("START")){
					response_string="READY";
					commandStart(data);
				}else if(command.equals("PLAY")){
					response_string=commandPlay(data);
/*				}else if(command.equals("replay")){
					response_string=commandReplay(data);*/
				}else if(command.equals("STOP")){
					response_string="DONE";
					commandStop(data);
				}else{
					throw(new IllegalArgumentException("Unknown command:"+command));
				}
			}else{
				throw(new IllegalArgumentException("Message is empty!"));
			}
			System.out.println(DateFormat.getTimeInstance(DateFormat.FULL).format(Calendar.getInstance().getTime()));
			System.out.println("Response:"+response_string);
			if(response_string!=null && response_string.equals("")) response_string=null;
			return new Response( HTTP_OK, "text/acl", response_string );
		}catch(IllegalArgumentException ex){
			System.err.println(ex);
			ex.printStackTrace();
			return new Response( HTTP_BADREQUEST, "text/acl", "NIL" );
		}
	}

	private String getCommand(String msg){
		String cmd=null;
		try{
			Matcher m=Pattern.compile("\\s*\\(\\s*([^\\s]*)\\s").matcher(msg);
			if(m.lookingAt()){
				cmd=m.group(1);
			}
			cmd=cmd.toUpperCase();
		}catch(Exception ex){
			System.err.println("Pattern to extract command did not match!");
			ex.printStackTrace();
		}
		return cmd;
	}

	public void waitForExit(){
		try {
			server_thread.join(); // wait for server thread to exit
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}