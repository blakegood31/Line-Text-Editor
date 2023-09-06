import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class LTE {
	//This is the main class for the line text editor, containing all 
	//the buffers, the command line, and other variables used to run
	//the program 
	
		public static CommandLine cmdln = new CommandLine();
		public static Buffer buffer = new Buffer();
		static Buffer clipboard = new Buffer();
		public static boolean lineNum = false;
		public static boolean done = true;
		public static boolean changedName = false;
		public static String prevCmd = "";
		public static ArrayList<String> parsedCmd;
	
	public static void main(String[] args) {
		//Main method for the line text editor. Contains a while loop
		//that reads in each command and calls the associated method to 
		//run the command. The while loop runs until a quit command is given 
		int commandCount = 1; 
		
		// while loop to run text editor
		while(done) {
			parsedCmd = process_command(cmdln, commandCount);
			if(parsedCmd != null) {
			String command = parsedCmd.get(0);
			
			// switch case to call driver method for given command 
			try {
			switch (command) {
			case "h":
				help();
				break;
			case "r":
				readFile(parsedCmd.get(1));
				break;
			case "w":
				writeFile();
				break;
			case "f":
				changeFileName(parsedCmd.get(1));
				break;
			case "q":
				quit();
				break;
			case "q!":
				forceQuit();
				break;
			case "t":
				top();
				break;
			case "b":
				bottom();
				break;
			case "g":
				goToLineNum(Integer.parseInt(parsedCmd.get(1)));
				break;
			case "-":
				prevLine();
				break;
			case "+":
				nextLine();
				break;
			case "=":
				printLineNum();
				break;
			case "n":
				showLineNum();
				break;
			case "#":
				printCounts();
				break;
			case "p":
				printCurrentLine();
				break;
			case "pr":
				printRange(Integer.valueOf(parsedCmd.get(1)), Integer.valueOf(parsedCmd.get(2)));
				break;
			case "?":
				searchBackward(parsedCmd.get(1));
				break;
			case "/":
				searchForward(parsedCmd.get(1));
				break;
			case "s":
				substituteText(parsedCmd.get(1), parsedCmd.get(2));
				break;
			case "sr":
				substituteTextInRange(Integer.parseInt(parsedCmd.get(1)), Integer.parseInt(parsedCmd.get(2)), parsedCmd.get(3), parsedCmd.get(4));
				break;
			case "d":
				cut();
				break;
			case "dr":
				cutRange(Integer.parseInt(parsedCmd.get(1)), Integer.parseInt(parsedCmd.get(2)));
				break;
			case "c":
				copy();
				break;
			case "cr":
				copyRange(Integer.parseInt(parsedCmd.get(1)), Integer.parseInt(parsedCmd.get(2)));
				break;
			case "pa":
				pasteAbove();
				break;
			case "pb":
				pasteBelow();
				break;
			case "ia":
				insertAbove();
				break;
			case "ic":
				prevCmd = "ic";
				insertAt();
				break;
			case "ib":
				insertBelow();
				break;
			}
			}
			catch(java.lang.NumberFormatException e) {
				System.out.println("Enter proper arguments for command " + parsedCmd.get(0));
			}
			catch(java.lang.IndexOutOfBoundsException e) {
				System.out.println("Enter proper arguments for command " + parsedCmd.get(0));
			}
		}
			commandCount++;
	}
}
	
	// method to process given command
	public static ArrayList<String> process_command(CommandLine commandline, int cmdNum) {
		if(lineNum == true) {
			System.out.print("LTE:" + buffer.getFilespec() + ":" + (buffer.text.getIndex() + 1) + ":" + cmdNum + ">_");
		}
		else {
			System.out.print("LTE:" + buffer.getFilespec() + ":" + cmdNum + ">_");
		}
		ArrayList<String> cmdArgs = commandline.read(prevCmd);
		return cmdArgs;
	}

	// method to print out help table 
	public static void help() {
		System.out.printf("%38s%n", "HELP TABLE");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "CMD", "ARGUMENTS", "DESCRIPTION");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "h", "",  "Display help");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "r", "filespec",   "Read a file into the current buffer");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "w", "",    "Write the current buffer to a file on disk");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "f", "filespec",     "Change the name of the current buffer");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "q", "",    "Quit the line editor");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "q!", "",   "Quit the line editor without saving");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "t", "",    "Go to the first line in the buffer");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "b",  "", "Go to the last line in the buffer");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "g",  "num", "Go to line num in the buffer");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "-",  "", "Go to the previous line");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "+",  "", "Go to the next line");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "=",  "", "Print the current line number");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "n",  "", "Toggle line number displayed");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "#",  "", "Print the number of lines and characters");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "",  "", "in the buffer");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "p",  "", "Print the current line");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "pr",  "start stop", "Print several lines");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "?",  "pattern", "Search backwards for a pattern");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "/",  "pattern", "Search forwards for a pattern");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "s",  "text1 text2", "Substitute all occurrences of text 1 with text 2");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "",  "", "on current line");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "sr",  "start stop text1 text2", "Substitute all occurrences of text 1 with text 2");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "",  "", "between start and stop");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "d",  "", "Delete the current line from buffer and copy into");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "",  "", "the clipboard (CUT)");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "dr",  "start stop", "Delete several lines from buffer and copy into");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "",  "", "the clipboard (CUT)");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "c",  "", "Copy current line into clipboard (COPY");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "cr",  "start stop", "Copy lines between start and stop into");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "",  "", "the clipboard (COPY)");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "pa",  "", "Paste the contents of the clipboard above the");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "",  "", "current line (PASTE)");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "pb",  "", "Paste the contents of the clipboard below the");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "",  "", "current line (PASTE)");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "ia",  "", "Insert new lines of text above the current");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "",  "", "line until '.' appears on its own line");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "ic",  "", "Insert new lines of text at the current line until");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "",  "", "'.' appears on its own line (REPLACE current line)");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "ib",  "", "Insert new lines of text after the current");
		System.out.printf("| %-3s | %-22s | %-51s |%n", "",  "", "line until '.' appears on its own line");
		System.out.printf("--------------------------------------------------------------------------------------%n");
		return;
	}
	
	// method to write contents of buffer to a file
	public static void writeFile() {
		if(buffer.text.getSize() == 0) {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
				try {
					String fileName;
					if(changedName == true) {
						fileName = buffer.getFilespec() + ".txt";
						File myFile = new File(fileName);
						myFile.createNewFile();
						System.out.println("File created at location: " + myFile.getAbsolutePath());
					}
					else {
						fileName = buffer.getFilespec();
					}
					FileWriter myWriter = new FileWriter(fileName);
					buffer.text.first();
					for(int i = 0; i<buffer.text.getSize(); i++) {
						myWriter.write(buffer.text.getData() + "\n");
						buffer.text.next();
					}
					myWriter.close();
					buffer.setDirty(false);
					return;
					}
				catch(IOException e) {}
	}
	
	// method to change name of buffer
	public static void changeFileName(String f) {
		buffer.setFilespec(f);
		changedName = true;
		return;
	}
	
	// method to quit the editor
	public static void quit() {
		if(buffer.getDirty() == true) {
			System.out.println("Changes made. Exit without saving? (y or n)");
			ArrayList<String> answer = cmdln.read("q");
			if(answer.get(0).equals("y")) {
				done = false; 
				return;
			}
			else {
				return;
			}
		}
		else {
			done = false;
			return;
		}
	}
	
	// method to force quit the editor without saving
	public static void forceQuit() {
		done = false;
		return;
	}
	
	// method to read a file into the buffer 
	public static void readFile(String filename) {
		
		if(buffer.getDirty() == true) {
			System.out.println("Changes made. Read new file without saving current buffer? (y or n)");
			ArrayList<String> answer = cmdln.read("q");
			if(answer.get(0).equals("y")) {
				if(buffer.text.size > 0) {
					buffer.text.clear();
				}
			
				buffer.fillList(filename);
				buffer.text.seek(0);
				buffer.setDirty(false);
				return;
			}
			else {
				return;
			}
		}
		else {
			if(buffer.text.size > 0) {
				buffer.text.clear();
			}
			buffer.fillList(filename);
			buffer.text.seek(0);
			buffer.setDirty(false);
			return;
		}
	}
	
	// method to go to first line in file 
	public static void top() {
		if(buffer.text.getSize() > 0) {
			buffer.text.first();
			return;
		}
		else {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
	}
	
	// method to go to last line in file
	public static void bottom() {
		if(buffer.text.getSize() > 0) {
			buffer.text.last();
			return;
		}
		else {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
	}
	
	// method to go to a specific line number
	public static void goToLineNum(int n) {
		if(n >= 1 && n <= buffer.text.getSize()) {
			buffer.text.seek(n-1);
			return;
		}
		else {
			System.out.println("==>> RANGE ERROR - num MUST BE [1.." + buffer.text.getSize() +"] <<==");
			return;
		}
	}
	
	// method to go to previous line
	public static void prevLine() {
		if(buffer.text.getSize() > 0 && buffer.text.getIndex() > 0) {
			buffer.text.previous();
			return;
		}
		else if(buffer.text.getSize() == 0) {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
		else {
			System.out.println("==>> ALREADY AT TOP OF BUFFER <<==");
			return;
		}
	}
	
	// method to go to next line
	public static void nextLine() {
		if(buffer.text.getSize() > 0 && buffer.text.getIndex() < (buffer.text.getSize() - 1)) {
			buffer.text.next();
			return;
		}
		else if(buffer.text.getSize() == 0) {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
		else {
			System.out.println("==>> ALREADY AT BOTTOM OF BUFFER <<==");
			return;
		}
	}
	
	// method to print the current line number
	public static void printLineNum() {
		if(buffer.text.getSize() <= 0) {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
		else {
			System.out.println("Current Line: " + (buffer.text.getIndex() + 1));
			return;
		}
		
	}
	
	// method to print lines within a specified range
	public static void printRange(int s, int e) {
		int startIndex = buffer.text.getIndex();
		if(buffer.text.getSize() <= 0) {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
		else if(s < 1 || e > buffer.text.getSize()) {
			System.out.println("==>> RANGE ERROR - num MUST BE [1.." + buffer.text.getSize() +"] <<==");
			return;
		}
		else {
			buffer.text.seek(s-1);
			for(int i = s; i <= e; i++) {
				if(lineNum == true) {
					System.out.println((buffer.text.getIndex()+1) + ": " + buffer.text.getData());
				}
				else {
					System.out.println(buffer.text.getData());
				}
				
				buffer.text.next();
			}
		}
		buffer.text.seek(startIndex);
		return;
	}
	
	// method to print current line in buffer
	public static void printCurrentLine() {
		if(lineNum == true) {
			System.out.println((buffer.text.getIndex()+1) + ": " + buffer.text.getData());
			return;
		}
		else {
			System.out.println(buffer.text.getData());
			return;
		}
		
		
	}
	
	// method to print number of lines and number of characters in buffer
	public static void printCounts() {
		int counter = 0;
		for(int i = 0; i<buffer.text.getSize(); i++) {
			counter = counter + (buffer.text.getData().length());
			buffer.text.next();
		}
		System.out.println("Line Count: " + buffer.text.getSize() + " Character Count: " + counter);
		return;
	}
	
	// method to search backwards for a specific pattern
	public static void searchBackward(String pattern) {
		int originalIndex = buffer.text.getIndex();
		boolean found = false;
		boolean ok = true;
		if(buffer.text.getIndex() == 0) {
			System.out.println("==>> ALREADY AT TOP OF BUFFER <<==");
			return;
		}
		else if(buffer.text.getSize() == 0) {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
		else {
			ok = buffer.text.previous();
			while(ok) {
				for(int i = 0; i<=(buffer.text.getData().length() - pattern.length()); i++) {
					if(i<(buffer.text.getData().length() - pattern.length())) {
						if(buffer.text.getData().substring(i, (i+pattern.length())).equals(pattern)){
							System.out.println("Found at line " + (buffer.text.getIndex()+1) + " column " + (i+1));
							found = true;
							System.out.println("Would you line to continue? (y or n)");
							ArrayList<String> answer = cmdln.read("q");
							if(answer.get(0).equals("y")) {}
							else {
								return;
							}
						}
					}
					else {
						if(buffer.text.getData().substring(i).equals(pattern)) {
							System.out.println("Found at line " + (buffer.text.getIndex()+1) + " column " + (i+1));
							found = true;
							System.out.println("Would you line to continue? (y or n)");
							ArrayList<String> answer = cmdln.read("q");
							if(answer.get(0).equals("y")) {}
							else {
								return;
							}
						}
					}
				}
					ok = buffer.text.previous();
			}
			buffer.text.seek(originalIndex);
			if(found == false) {
				System.out.println("==>> STRING " + pattern + " NOT FOUND <<==");
			}
			
			return;
		}
	}
	
	// method to search forwards for a specific pattern
	public static void searchForward(String pattern) {
		int originalIndex = buffer.text.getIndex();
		boolean ok = true;
		boolean found = false;
		if(buffer.text.getSize() == 0) {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
		else {
			while(ok) {
				for(int i = 0; i<=(buffer.text.getData().length() - pattern.length()); i++) {
					if(i<(buffer.text.getData().length() - pattern.length())) {
						if(buffer.text.getData().substring(i, (i+pattern.length())).equals(pattern)){
							System.out.println("Found at line " + (buffer.text.getIndex()+1) + " column " + (i+1));
							found = true;
							System.out.println("Would you line to continue? (y or n)");
							ArrayList<String> answer = cmdln.read("q");
							if(answer.get(0).equals("y")) {}
							else {
								return;
							}
						}
					}
					else {
						if(buffer.text.getData().substring(i).equals(pattern)) {
							System.out.println("Found at line " + (buffer.text.getIndex()+1) + " column " + (i+1));
							found = true;
							System.out.println("Would you line to continue? (y or n)");
							ArrayList<String> answer = cmdln.read("q");
							if(answer.get(0).equals("y")) {}
							else {
								return;
							}
						}
					}
				}
				ok = buffer.text.next();
			}
			buffer.text.seek(originalIndex);
			if(found == false) {
				System.out.println("==>> STRING " + pattern + " NOT FOUND <<==");
			}
			return;
		}
	}
	
	// method to substitute given pattern with new text
	public static void substituteText(String text1, String text2) {
		String newLine = "";
		String original = buffer.text.getData();
		for(int i = 0; i<=(buffer.text.getData().length() - text1.length()); i++) {
			String add = "";
			if(i<(buffer.text.getData().length() - text1.length())) {
				if(buffer.text.getData().substring(i, i+text1.length()).equals(text1)) {
					i = (i+text1.length())-1;
					add = text2;
					buffer.setDirty(true);
				}
				else {
					add = original.substring(i, i+1);
				}
			}
			else if(i==(buffer.text.getData().length() - text1.length())){
				if(buffer.text.getData().substring(i).equals(text1)) {
					add = text2;
					buffer.setDirty(true);
				}
				else {
					add = original.substring(i);
				}
			}
			newLine = newLine + add;
	}
		buffer.text.setData(newLine);
			return;
	}
	
	// method to substitute text in a give range
	public static void substituteTextInRange(int start, int stop, String text1, String text2) {
		if(start>stop || start<0 || stop > buffer.text.getSize()) {
			System.out.println("==>> INVALID RANGE <<==");
			return;
		}
		
		buffer.text.seek(start-1);
		
		
		for(int j = start; j <= stop; j++) {
			String newLine = "";
			String original = buffer.text.getData();
		for(int i = 0; i<=(buffer.text.getData().length() - text1.length()); i++) {
			String add = "";
			if(i<(buffer.text.getData().length() - text1.length())) {
				if(buffer.text.getData().substring(i, i+text1.length()).equals(text1)) {
					i = (i+text1.length())-1;
					add = text2;
					buffer.setDirty(true);
				}
				else {
					add = original.substring(i, i+1);
				}
			}
			else if(i==(buffer.text.getData().length() - text1.length())){
				if(buffer.text.getData().substring(i).equals(text1)) {
					add = text2;
					buffer.setDirty(true);
				}
				else {
					add = original.substring(i);
				}
			}
			newLine = newLine + add;
	}
		buffer.text.setData(newLine);
		buffer.text.next();
		}
		return;
	}
	 
	// method to delete current line and copy it to clipboard
	public static void cut() {
		if(buffer.text.getSize() == 0) {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
		if(clipboard.text.getSize() != 0) {
			clipboard.text.clear();
		}
			clipboard.text.insertFirst(buffer.text.getData());
			if(buffer.text.getIndex() == 0) {
				buffer.text.deleteFirst();
			}
			else if(buffer.text.getIndex() == buffer.text.getSize()-1) {
				buffer.text.deleteLast();
			}
			else {
				buffer.text.deleteAt();
			}
			buffer.setDirty(true);
			return;
	}
	
	// method to delete range of lines and copy them to clipboard
	public static void cutRange(int start, int stop) {
		int originalIndex = buffer.text.getIndex();
		if(start < 0 || stop > (buffer.text.getSize()-1)) {
			System.out.println("==>> INDICES OUT OF RANGE <<==");
			return;
		}
		else if(buffer.text.getSize() == 0) {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
		if(clipboard.text.getSize() != 0) {
			clipboard.text.clear();
		}
		buffer.text.seek(start-1);
			for(int i = start; i<=stop; i++) {
				clipboard.text.insertLast(buffer.text.getData());
				
				if(buffer.text.getIndex() == 0) {
					buffer.text.deleteFirst();
				}
				else if(buffer.text.getIndex() == buffer.text.getSize()-1) {
					buffer.text.deleteLast();
				}
				else {
					buffer.text.deleteAt();
				}
		}
			buffer.setDirty(true);
			buffer.text.seek(originalIndex);
			return;
	}
	
	// method to copy current line to clipboard
	public static void copy() {
		if(buffer.text.getSize() == 0) {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
		if(clipboard.text.getSize() != 0) {
			clipboard.text.clear();
		}
			clipboard.text.insertFirst(buffer.text.getData());
			return;
	}
	
	// method to copy range of lines to clipboard
	public static void copyRange(int start, int stop) {
		int originalIndex = buffer.text.getIndex();
		if(start < 0 || stop > (buffer.text.getSize())) {
			System.out.println("==>> INDICES OUT OF RANGE <<==");
			return;
		}
		else if(buffer.text.getSize() == 0) {
			System.out.println("==>> BUFFER IS EMPTY <<==");
			return;
		}
		if(clipboard.text.getSize() != 0) {
			clipboard.text.clear();
		}
		buffer.text.seek(start-1);
			for(int i = start; i<=stop; i++) {
				clipboard.text.insertLast(buffer.text.getData());
				buffer.text.next();
			}
			buffer.text.seek(originalIndex);
				return;
	}
	
	// method to toggle line number shown
	public static void showLineNum() {
		if(lineNum == false) {
			lineNum = true;
			return;
		}
		else {
			lineNum = false;
			return;
		}
	}
	
	// method to paste clipboard above current line
	public static void pasteAbove() {
		if(clipboard.text.getSize() == 0) {
			System.out.println("==>> CLIPBOARD EMPTY <<==");
			return;
		}
		else {
			clipboard.text.first();
			buffer.setDirty(true);
			for(int i = 0; i<clipboard.text.getSize(); i++) {
				if(buffer.text.getIndex() == 0) {
					buffer.text.insertFirst(clipboard.text.getData());
				}
				else {
				buffer.text.insertAt(clipboard.text.getData());
				buffer.text.next();
				}
				
				clipboard.text.next();
				
			}
		}
		return;
	}
	
	// method to paste clipboard below current line
	public static void pasteBelow() {
		if(clipboard.text.getSize() == 0) {
			System.out.println("==>> CLIPBOARD EMPTY <<==");
			return;
		}
		else {
			clipboard.text.first();
			buffer.setDirty(true);
			for(int i = 0; i<clipboard.text.getSize(); i++) {
				if(buffer.text.getIndex() == buffer.text.getSize()-1) {
					buffer.text.insertLast(clipboard.text.getData());
					buffer.text.next();
				}
				else {
					buffer.text.next();
					buffer.text.insertAt(clipboard.text.getData());
				}
				clipboard.text.next();
			}
		}
		return;
	}
	
	// method to insert given text above current line
	public static void insertAbove() {
		boolean done = true;
		while(done) {
			String newLine = "";
			ArrayList<String> insert = cmdln.read("ia");
			for(int i = 0; i<insert.size(); i++) {
				newLine = newLine + insert.get(i) + " ";
			}
			if(buffer.text.getIndex() == 0) {
				buffer.text.insertFirst(newLine);
			}
			else {
			buffer.text.insertAt(newLine);
			buffer.text.next();
			}
			for(int i = 0; i<newLine.length(); i++) {
				if(newLine.charAt(i) == '.') {
					buffer.setDirty(true);
					done = false;
					return;
				}
			}
		}
	}
	
	// method to insert given text below the current line
	public static void insertBelow() {
		boolean done = true;
		
		while(done) {
			String newLine = "";
			ArrayList<String> insert = cmdln.read("ib");
			for(int i = 0; i<insert.size(); i++) {
				newLine = newLine + insert.get(i) + " ";
			}
			if(buffer.text.getIndex() == buffer.text.getSize()-1) {
				buffer.text.insertLast(newLine);
			}
			else {
				buffer.text.next();
				buffer.text.insertAt(newLine);
			}
			for(int i = 0; i<newLine.length(); i++) 
				if(newLine.charAt(i) == '.') {
					buffer.setDirty(true);
					done = false;
					buffer.text.seek(buffer.text.getIndex()-1);
					return;
				}
			}
		}
	
	// method to insert given text at the current line
	public static void insertAt() {
		boolean done = true;
		while(done) {
			String newLine = "";
			ArrayList<String> insert = cmdln.read("ic");
			for(int i = 0; i<insert.size(); i++) {
				newLine = newLine + insert.get(i) + " ";
			}
			buffer.text.insertAt(newLine);
			for(int i = 0; i<newLine.length(); i++) {
				if(newLine.charAt(i) == '.') {
					buffer.setDirty(true);
					done = false;
					return;
				}
			}
		}
	}
}
