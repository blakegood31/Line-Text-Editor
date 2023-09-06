import java.util.*;

public class CommandLine extends LTE{
	
	private Scanner sc = new Scanner(System.in);
	private String command;
	private ArrayList<String> parsedCommand;
	
	public CommandLine(){
		
	}
	
	// method to read the command from command line
	public ArrayList<String> read(String prevCmd) {
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
