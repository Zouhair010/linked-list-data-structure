/**
 * The main class that contains the DynamicLinkedList implementation and a main method for testing.
 * Note: The main method has a compilation error as it tries to call methods on the outer `LinkedList` class
 * instead of the inner `DynamicLinkedList` class where the logic is implemented.
 */
public class LinkedList {
	/**
	 * An implementation of a singly linked list that can store objects of any type.
	 */
	public static class DynamicLinkedList{

		/**
		 * Inner class representing a node in the linked list.
		 * Each node contains data and a reference to the next node.
		 */
	    private static class Node{
			/** The data stored in the node. Can be any object. */
    	    public Object data;
			/** A reference to the next node in the list. */
    	    public Node next;

			/**
			 * Constructs a new Node with the given data.
			 * @param data The data to be stored in this node.
			 */
    	    public Node( Object data){
    	        this.data = data;
    	    }
	    }
		/** A reference to the first node (head) of the list. */
	    private Node front;
		/** A reference to the last node (tail) of the list for efficient additions. */
	    private Node rear;
		/** The current number of elements in the list. */
	    public int length = 0;
		/** A stateful node used by the `next()` method to iterate through the list. */
		private Node trackerNode;

		/**
		 * Adds a new element to the end of the list.
		 * @param object The object to be added to the list.
		 */
	    public void add(Object object){
			// If the list is empty, the new node is both the front and the rear.
	        if (front==null){
	            front = new Node(object);
	            rear = front;
	            length++;
	            return;
	        }
			// Otherwise, add the new node after the current rear and update the rear.
	        rear.next = new Node(object);
			rear = rear.next;
			length++;
	    }

		/**
		 * Deletes the first occurrence of a specified object from the list.
		 * Note: This method uses value equality (.equals()).
		 * @param object The object to be deleted.
		 */
	    public void delete(Object object){
			// If the list is empty, there's nothing to delete.
			if (isEmpty()){
				System.out.println("the list is empty!");
				return;
			}
	        Node currNode = front;
			// Case 1: The node to be deleted is the front node.
	        if (currNode!=null && currNode.data.equals(object)){
	            currNode = currNode.next;
				front = currNode;
	            length--;
	            return;
	        }
			// Case 2: The node to be deleted is not the front node.
			// Traverse the list to find the node *before* the one to be deleted.
	        while (currNode!=null) {
				// Check if the *next* node is the one to delete.
	            if (currNode.next!=null && currNode.next.data.equals(object)){
					// Bypass the node to be deleted.
	                currNode.next=currNode.next.next;
	                length--;
	                return;
	            }
				// Move to the next node.
	            currNode=currNode.next;
	        }
	    }

		/**
		 * Returns the data of the next element in a stateful iteration.
		 * Resets to the front if called for the first time.
		 * Throws NullPointerException if called on an empty list or after the end of the list is reached.
		 * @return The data of the next node in the sequence.
		 */
		public Object next(){
			// If trackerNode is null (first call), start from the front. Otherwise, move to the next node.
			trackerNode = (trackerNode==null)?front:trackerNode.next;
			Node currnode = trackerNode;
			return currnode.data;
		}

		/**
		 * Checks if the list is empty.
		 * @return true if the list has no elements, false otherwise.
		 */
		public boolean isEmpty(){
			if (length == 0){
				return true;
			}
			return false;
		}

		/**
		 * Prints the contents of the list to the console in a formatted way (e.g., [item1 ,item2]).
		 */
	    public void printList(){
	        Node currnode = front;
	        System.out.print("[");
	        while (currnode!=null) {
	            System.out.print(typeFormat(currnode.data));
	            if (currnode.next!=null){
	                System.out.print(" ,");
	            }
	            currnode = currnode.next;
	        }
			System.out.print("]");
	        System.out.println();
	    }

		/**
		 * A helper method to format an object as a String for printing.
		 * It adds quotes for Characters and Strings.
		 * @param item The object to format.
		 * @return A string representation of the object.
		 */
		private static String typeFormat(Object item){
            String string;
            switch (item.getClass().getName()) {
                    case "java.lang.Character":
                        string = "\'"+item+"\'";
                        break;
                    case "java.lang.String":
                        string = "\""+item+"\"";
                        break;
                    default:
                        string = ""+item;
                        break;
            }
            return string;
        }
    }

	/**
	 * The main entry point for the program.
	 * NOTE: This method will not compile as written. It creates an instance of `LinkedList`
	 * but should create an instance of `DynamicLinkedList` to access the list methods.
	 * For example: `DynamicLinkedList list = new DynamicLinkedList();`
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		// The line below is incorrect and will cause a compilation error.
		// It should be: DynamicLinkedList list = new DynamicLinkedList();
		DynamicLinkedList list = new DynamicLinkedList();
		list.add(4); // Add integer 4
		list.add('3'); // Add character '3'
		list.add(2); // Add integer 2
		list.add("1"); // Add string "1"
		list.printList(); // Expected: [4 ,'3' ,2 ,"1"]
		System.out.println(list.length); // Expected: 4
		list.delete(4); // Delete integer 4
		list.printList(); // Expected: ['3' ,2 ,"1"]
		System.out.println(list.length); // Expected: 3
		list.delete(1); // Tries to delete integer 1, but the list contains string "1". No change.
		list.printList(); // Expected: ['3' ,2 ,"1"]
		System.out.println(list.length); // Expected: 3
		list.delete(2); // Delete integer 2
		list.printList(); // Expected: ['3' ,"1"]
		System.out.println(list.length); // Expected: 2
		list.delete(3); // Tries to delete integer 3, but the list contains character '3'. No change.
		list.printList(); // Expected: ['3' ,"1"]
		System.out.println(list.length); // Expected: 2
		list.delete(3); // Tries again, no change.
		for (int i=0 ; i<list.length ; i++){
			System.out.print(list.next()+", ");
		}
	}
}