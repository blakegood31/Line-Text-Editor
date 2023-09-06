//=========================================
// Java Templated Doubly Linked List Class 
//=========================================

//=========================================
//DLList Class
//=========================================
public class DLList <T>{
	
	//=========================================
	// Internal DLListNode Class
	//=========================================
	private class DLListNode<T>{
		//Data Members 
		public T data; //Data field
		public DLListNode<T> previous;
		public DLListNode<T> next;
		
		//Overloaded Constructor 
		DLListNode(T value){
			data = value;
			previous = null;
			next = null;
		}
	}
	
	
	//=========================================
	//Data Members 
	//=========================================
	private DLListNode<T> front;
	private DLListNode<T> back;
	private DLListNode<T> current;
	int size; 
	int index;

	
	//=========================================
	// Member Functions (Methods) 
	//=========================================

	//default constructor
	public DLList() {
		clear();
	}
	
	//copy constructor (deep copy)
	public DLList(DLList<T> other) {
		
	}
	
	//clear list method (sets front, back, and current to null, size to 0, and index to -1)
	public void clear() {
		front = null;
		back = null;
		current = null;
		size = 0; 
		index = -1;
	}
	
	//get size method
	public int getSize() {
		return size;
	}
	
	//get index method 
	public int getIndex() {
		return index;
	}
	
	//is empty method 
	public boolean isEmpty() {
		return getSize() == 0; 
	}
	
	//is at first method 
	public boolean atFirst() {
		return getIndex() == 0;
	}
	
	//is at last method 
	public boolean atLast() {
		return (getIndex() == (getSize()-1));
	}
	
	// get data at current method 
	public T getData() {
		if(!isEmpty()) {
			return current.data;
		}
		else {
			return null;
		}
	}
	
	// set data at current method 
	public T setData(T x) {
		if(!isEmpty()) {
			current.data = x;
			return x;
		}
		else {
			return null;
		}
	}
	
	// seek to first node method 
	public boolean first() {
		return (seek(0));
	}
	
	// seek to next node method 
	public boolean next() {
		return(seek(getIndex()+1));
	}
	
	// seek to previous node method 
	public boolean previous() {
		return (seek(getIndex()-1));
	}
	
	// seek to the last node method 
	public boolean last() {
		return (seek(getSize()-1));
	}
	
	// seek method 
	public boolean seek(int loc) {
		// local variables
		boolean retval = false;

		// is the list empty 
		if(isEmpty()) {
			retval = false;
		}
		
		// is loc in range
		else if(loc < 0 || loc >= getSize()) {
			retval = false;
		}
		
		// is loc == 0
		else if(loc == 0) {
			current = front;
			index = 0;
			retval = true;
		}
		
		// is loc == last index
		else if(loc == getSize()-1) {
			current = back;
			index = getSize()-1;
			retval = true;
		}
		
		// is loc < current index 
		else if(loc < getIndex()) {
			for(; getIndex() != loc; index--) {
				current = current.previous;
			}
			retval = true;
		}
		
		// is loc > current index 
		else if(loc > getIndex()) {
			for(; getIndex() != loc; index++) {
				current = current.next;
			}
			retval = true;
		}
		
		// else ... loc is at the current index 
		else {
			retval = true;
		}
		return retval;
	}
	
	// intsert first method 
	public boolean insertFirst(T item) {
		DLListNode newFirst = new DLListNode(item);
		if(size>0) {
			front.previous = newFirst;
			newFirst.next = front;
			front = newFirst;
			size++;
			index++;
			return true;
		}
		else {
			front = newFirst;
			back = newFirst;
			current = newFirst;
			size++;
			index = 0;
			return true;
		}
	}
	
	// insert at current location method 
	public boolean insertAt(T item) {
		DLListNode newNode = new DLListNode(item);
		if(size > 1) {
			current.previous.next = newNode;
			newNode.previous = current.previous;
			newNode.next = current;
			current.previous = newNode;
			current = newNode;
			size++;
			return true;
		}
		else {
			return false;
		}
		
	}
	
	// insert last method 
	public boolean insertLast(T item) {
		DLListNode newNode = new DLListNode(item);
		if(size == 0) {
			current = newNode;
			front = current;
			back = current;
			size = 1;
			index = 0;
			return true;
		}
		else {
			back.next = newNode;
			newNode.previous = back;
			back = newNode;
			size++;
			return true;
		}
		
	}

	// delete first method
	public boolean deleteFirst() {
		if(size == 0) {
			return false;
		}
		else if(size ==1) {
			current = null;
			front = null;
			back = null;
			index = -1;
			size = 0;
			return true;
		}
		else {
			current = current.next;
			current.previous = null;
			front.next = null;
			front = current;
			size --;
			return true;
		}
		
	}
	
	// delete at method 
	public boolean deleteAt() {
		
		if(size == 0) {
			return false;
		}
		else if(size ==1) {
			current = null;
			front = null;
			back = null;
			index = -1;
			size = 0;
			return true;
		}
		else {
			current.previous.next = current.next;
			current.next.previous = current.previous;
			DLListNode delRef = current;
			current = current.next;
			delRef.next = null;
			delRef.previous = null;
			delRef = null;
			size --;
			return true;
		}
		
	}
	
	// delete last method
	public boolean deleteLast() {
		if(size == 0) {
			return false;
		}
		else if(size ==1) {
			current = null;
			front = null;
			back = null;
			index = -1;
			size = 0;
			return true;
		}
		else {
			current = current.previous;
			current.next = null;
			back.previous = null;
			back = current;
			size --;
			index --;
			return true;
		}
			
	}
}
