import java.util.*;

public class CommandLine extends LTE{
	//This class is used to represent the command line (not the prompt, just the 
	//commands entered by the user.
	//The command line contains a scanner that reads the given command from the console, 
	//a command String which stores the command that was entered, and an ArrayList that holds 
	//the parsed command. It also contains one method, read(), which processes the given command. 
	private Scanner sc = new Scanner(System.in);
	private String command;
	private ArrayList<String> parsedCommand;
	
	public CommandLine(){
		
	}
	
	// method to read the command from command line
	public ArrayList<String> read(String prevCmd) {
		//This method is used to process the command given by the user in the console. 
		//
		//Input: The previous command given. This is used when the previous command is 
		//an insert or quit command. When the previous command is one of these, we still 
		//want to read the subsequent input as a valid command (i.e. the lines that the 
		// user enters to be inserted).
		//
		//Output: An array list containing the parsed command if it is a valid command, 
		//null if the parsed command is a filename or invalid command, and an error message 
		//if the command is invalid 
		ArrayList<String> validCmd = new ArrayList<String>(Arrays.asList("h", "r", "w", "f", "q", "q!", "t", "b", "g", "-", "+", "=", "n", "#","p",
																		"pr", "?", "/", "s", "sr", "d", "dr", "c", "cr", "pa", "pb", "ia", "ic", "ib"));command = sc.nextLine();
		parsedCommand = new ArrayList<String>();
		StringTokenizer tok = new StringTokenizer(command, " ");
		String cmd;
		while(tok.hasMoreTokens()) {
			cmd = tok.nextToken();
			parsedCommand.add(cmd);
		}
		if(validCmd.indexOf(parsedCommand.get(0)) >= 0 || prevCmd.equals("ia") || prevCmd.equals("ib") || prevCmd.equals("ic") || prevCmd.equals("q") || prevCmd.equals("q!")) {
			return parsedCommand;
		}
		else {
			//If given a filename, try to fill the buffer
			boolean tryFill = buffer.fillList(parsedCommand.get(0));
			if(tryFill == true) {
				return null;
			}
			else {
				System.out.println("Enter a Valid Command");
				return null;
			}
			
		}
	}
}
