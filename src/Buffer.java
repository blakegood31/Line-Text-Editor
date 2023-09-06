import java.io.*;

public class Buffer {
	
	//public class MainBuffer{
		private String filespec;
		private boolean dirty;
		public DLList <String> text;
		
		// constructor
		public Buffer() {
			filespec = "";
			dirty = false;
			text = new DLList<String>();
			
		}
		
		// method to set filespec of buffer
		public void setFilespec(String name) {
			filespec = name;
		}
		
		// method to get filespec of buffer
		public String getFilespec() {
			return filespec;
		}
		
		// method to set dirtybit
		public void setDirty(boolean tf) {
			dirty = tf;
		}
		
		// method to get dirtybit
		public boolean getDirty() {
			return dirty;
		}
		
		// method to fill DLList with contents of file
		public boolean fillList(String filename){
			try {
				BufferedReader in = new BufferedReader(new FileReader(filename));
				String line;
				text.clear();
				while((line = in.readLine()) != null) {
					text.insertLast(line);
				}
				filespec = filename;
				return true;
			}
			catch(IOException e) {
				System.out.println("==>> FILE DOES NOT EXIST<<==");
			}
			return false;
		}
}


