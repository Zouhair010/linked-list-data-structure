
public class LinkedList {
	/**
	 * An implementation of a doubly linked list that can store objects of any type.
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
			/** A reference to the previous node in the list. */
            public Node prev;
			/**
			 * Constructs a new Node with the given data.
			 * @param data The data to be stored in this node.
			 */
    	    public Node( Object data){
    	        this.data = data;
    	    }
	    }
		/** A reference to the first node (head) of the list. */
	    private Node head;
		/** A reference to the last node (tail) of the list for efficient additions. */
	    private Node tail;
		/** The current number of elements in the list. */
	    public int length = 0;
		/** A stateful node used by the `next()` method to iterate through the list. */
		private Node nextNode;

		/**
		 * Appends a new node with the given object to the end of the list.
		 * @param object The data to be stored in the new node.
		 */
	    public void append(Object object){
			Node newNode = new Node(object);
	        if (head==null){
	            head = newNode;
	            tail = head;
	            length++;
	            return;
	        }
	        newNode.prev = tail;
            tail.next = newNode;
			tail = newNode;
			length++;
	    }

		/**
		 * Deletes the first occurrence of a node containing the specified object.
		 * If the list is empty, it prints a message to the console.
		 * @param object The object to be deleted from the list.
		 */
	    public void delete(Object object){
			// Start traversal from the head of the list.
			Node currNode= head;

			// Case 1: The list is empty. Print a message and do nothing.
			if (isEmpty()){
				System.out.println("the list is empty!");
				// The return is commented out, so the method will continue to the next block,
				// but the conditions will prevent any further action.
				// return;
			}
			// Case 2: The node to be deleted is the head of the list.
			else if(currNode!=null && currNode.data.equals(object)){
				// Move the head reference to the next node.
				currNode = currNode.next;
				// If the list has more than one node, update the new head's prev pointer.
				if(currNode!=null){
					currNode.prev = null;
				}
				// If the list had only one node, the list is now empty, so update the tail.
				else{
					tail = null;
				}			
				// Officially set the new head of the list.
				head = currNode;
				length--;
				return;
			}
			// Case 3: The node to be deleted is not the head. Traverse the list to find it.
			while(currNode!=null){
				// Check if the *next* node is the one to delete.
				if (currNode.next!=null && currNode.next.data.equals(object)){
					// Subcase 3a: The node to delete is the tail.
					if(currNode.next==tail){
						// Update the tail to be the current node.
			    		tail = tail.prev;
					}
					// Subcase 3b: The node to delete is in the middle of the list.
					else{
						// Update the 'prev' pointer of the node *after* the one being deleted.
						currNode.next.next.prev = currNode;
					}
					// Bypass the node to be deleted by updating the 'next' pointer.
	                currNode.next = currNode.next.next;
	                length--;
	                return;
				}
				// Move to the next node to continue searching.
	            currNode=currNode.next;
			}
	    }

		/**
		 * Iterates through the list, returning the data of the next node in sequence.
		 * This method is stateful and relies on an internal tracker.
		 * On the first call, it returns the data from the head of the list.
		 * Subsequent calls return data from subsequent nodes.
		 * @return The data of the next node in the iteration.
		 */
		public Object next(){
			if (nextNode==null){
                nextNode = head;
            }
			Node currnode = nextNode;
            nextNode = nextNode.next;
			return currnode.data;
		}
		/**
		 * Searches for an object in the list using a two-pointer approach,
		 * traversing from both the head and the tail simultaneously.
		 *
		 * This method initializes one pointer at the start and another at the end,
		 * moving them towards the center with each iteration. This can find elements
		 * near the end of the list faster than a simple head-to-tail scan.
		 *
		 * Time Complexity: O(≈ n / 2).

		 *
		 * @param object The object to search for in the list.
		 * @return {@code true} if the object is found, {@code false} otherwise.
		 */
		public boolean search(Object object){
			// Initialize two pointers: one at the head (start) and one at the tail (end).
			Node currNodeHead = head;
			Node currNodeTail = tail;
			// Loop until the pointers meet or cross, checking from both ends.
			// The loop condition `i < length` is a safeguard; the logic inside should return earlier.
			for (int i=0 ; i<length ; i++){
				// Termination condition: pointers have met (for odd-length lists) or are adjacent (for even-length lists).
				if ((currNodeHead.next==currNodeTail || currNodeHead==currNodeTail)){
					// Final check at the meeting point. BUG: currNodeHead.next can be null here.
					if(currNodeHead.data.equals(object) || currNodeHead.next.data.equals(object)){
						return true;
					}
					// If not found at the meeting point, the object is not in the list.
					return false;
				}
				// Check if the data at the current head-side or tail-side pointer matches.
				else if (currNodeHead.data.equals(object) || currNodeTail.data.equals(object)){
					return true;
				}
				// If no match is found, move the pointers one step closer to the center.
				currNodeHead = currNodeHead.next;
				currNodeTail = currNodeTail.prev;
			}
			// This fallback is reached if the list is empty or if the loop completes unexpectedly.
			return false;
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
	        Node currnode = head;
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
		 * Prints the contents of the list to the console in reverse order,
		 * starting from the tail, in a formatted way (e.g., [item2 ,item1]).
		 */
        public void reversePrintList(){
	        Node currnode = tail;
	        System.out.print("[");
	        while (currnode!=null) {
	            System.out.print(typeFormat(currnode.data));
	            if (currnode.prev!=null){
	                System.out.print(" ,");
	            }
	            currnode = currnode.prev;
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
	public static void main(String[] args) {
		DynamicLinkedList list = new DynamicLinkedList();
		list.append(0);
		list.append(1);
		list.append(2);
		list.append(3);
		list.append(4);
		list.append(5);
		list.append(6);
		list.append(7);
		list.printList();
		// list.reversePrintList();
		// System.out.println(list.length);
		System.out.println(list.search(7));
	}
}