/**
 * The main class that contains the DynamicLinkedList implementation and a main method for testing.
 */
public class LinkedList {
	/**
	 * An implementation of a linked list that can store objects of any type.
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
		/** A stateful node used by the `nextFrontTraversal()` method to iterate through the list from the head. */
		private Node frontNode;
		/** A stateful node used by the `nextRearTraversal()` method to iterate through the list from the tail. */
        private Node rearNode;

		/**
		 * Finds the node with the maximum value in the list based on the data's hash code.
		 * It uses a two-pointer approach, traversing from both head and tail.
		 * @return The {@code Node} containing the maximum value. Returns {@code head} if the list is empty or has one element.
		 */
        public Node maxVlueNode(){
			// Initialize two pointers: one at the head (start) and one at the tail (end).
			Node currNodeHead = head;
			Node currNodeTail = tail;
            Node maxValNode = head;
			while (currNodeHead!=null && currNodeTail!=null){
				// Termination condition: pointers have met (for odd-length lists) or are adjacent (for even-length lists).
				if (currNodeHead==currNodeTail || currNodeHead.next==currNodeTail){
					// Final check at the meeting point.
					if(currNodeHead.data.hashCode() > maxValNode.data.hashCode()){
						maxValNode = currNodeHead;
					}
					else if (currNodeTail.data.hashCode() > maxValNode.data.hashCode()){
						maxValNode = currNodeTail;
					}
					// If not found at the meeting point, the object is not in the list.
					break;
				}
				// Check if the data at the current head-side or tail-side pointer matches.
				else if (currNodeHead.data.hashCode() > maxValNode.data.hashCode()){
					maxValNode = currNodeHead;
				}
				else if (currNodeTail.data.hashCode() > maxValNode.data.hashCode()){
					maxValNode = currNodeTail;
				}
				// If no match is found, move the pointers one step closer to the center.
				currNodeHead = currNodeHead.next;
				currNodeTail = currNodeTail.prev;
            }
            return maxValNode;
        }
		/**
		 * Finds the node with the minimum value in the list based on the data's hash code.
		 * It uses a two-pointer approach, traversing from both head and tail.
		 * @return The {@code Node} containing the minimum value. Returns {@code head} if the list is empty or has one element.
		 */
		public Node minVlueNode(){
			// Initialize two pointers: one at the head (start) and one at the tail (end).
			Node currNodeHead = head;
			Node currNodeTail = tail;
            Node minValNode = head;
			while (currNodeHead!=null && currNodeTail!=null){
				// Termination condition: pointers have met (for odd-length lists) or are adjacent (for even-length lists).
				if (currNodeHead==currNodeTail || currNodeHead.next==currNodeTail){
					// Final check at the meeting point.
					if(currNodeHead.data.hashCode() < minValNode.data.hashCode()){
						minValNode = currNodeHead;
					}
					else if (currNodeTail.data.hashCode() < minValNode.data.hashCode()){
						minValNode = currNodeTail;
					}
					// If not found at the meeting point, the object is not in the list.
					break;
				}
				// Check if the data at the current head-side or tail-side pointer matches.
				else if (currNodeHead.data.hashCode() < minValNode.data.hashCode()){
					minValNode = currNodeHead;
				}
				else if (currNodeTail.data.hashCode() < minValNode.data.hashCode()){
					minValNode = currNodeTail;
				}
				// If no match is found, move the pointers one step closer to the center.
				currNodeHead = currNodeHead.next;
				currNodeTail = currNodeTail.prev;
            }
            return minValNode;
		}
		/**
		 * Sorts the list in descending order based on the data's hash code.
		 * This is an inefficient selection sort implementation (O(n^2)) that repeatedly finds the max element,
		 * removes it from the original list, and appends it to a new sorted list.
		 * The original list is then replaced by the new sorted list.
		 */
		public void sort(){
			Node headNode = null;
			Node tailNode = null;
			for (int i=0 ; i<length ; i++){
				Node maxValNode = maxVlueNode(); // Finds the current maximum value node
				removeByReferance(maxValNode);
				Node newNode = new Node(maxValNode.data);
	            if (headNode==null){
	                headNode = newNode;
	                tailNode = headNode;
	            }
				else{
    	            newNode.prev = tailNode;
                    tailNode.next = newNode;
			        tailNode = newNode;
				}
			}
			head = headNode; // Replace the old list with the new sorted list
			tail = tailNode;
		}
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
		 * Inserts a new node with the given object at a specific index.
		 * It uses a two-pointer approach to find the insertion point efficiently.
		 * @param index The index at which to insert the new node.
		 * @param object The data to be stored in the new node.
		 */
		public void insert(int index, Object object){
			Node newNode = new Node(object);
			if (index>=length || index<0){
				System.out.println("the index out of range!");
				return;
			}
			Node currHeadNode = head;
			Node currTailNode = tail;
			// Handle insertion at the beginning of the list
			if (index==0){
				newNode.next=currHeadNode;
				currHeadNode.prev = newNode;
				head = newNode;
			}
			else{
				currHeadNode = currHeadNode.next;
				// Use two pointers to find the insertion point faster
				int tailIndex;
				for (int headIndex=1 ; headIndex<length ; headIndex++) {
				    tailIndex = length-headIndex;
					// If the index is closer to the head
				    if (headIndex==index) {
					    newNode.next = currHeadNode;
					    newNode.prev = currHeadNode.prev;
					    currHeadNode.prev.next = newNode;
						currHeadNode.prev = newNode;
					    break;
				    }
				    else if (tailIndex==index){
					    newNode.next = currTailNode;
					    newNode.prev = currTailNode.prev;
					    currTailNode.prev.next = newNode;
						currTailNode.prev = newNode;
					    break;
				    }
				    currHeadNode = currHeadNode.next;
				    currTailNode = currTailNode.prev;
	    		}
			}
			length++;
		}
		/**
		 * Removes the first occurrence of a node with the given value.
		 * @param object The value of the node to be removed.
		 */
		public void removeByValue(Object object){
			Node nodeToDelete = search(object);
			if (nodeToDelete==null){
				System.out.println(object+" not on the list!");
				return;
			}
			if (nodeToDelete==tail || nodeToDelete==head){
				// If the node to delete is the tail (and not also the head)
				if (nodeToDelete!=head){
					nodeToDelete.prev.next = null;
					nodeToDelete = nodeToDelete.prev;
					tail = nodeToDelete;
				}
				// If the node to delete is the head (and not also the tail)
				else if (nodeToDelete!=tail){
					nodeToDelete.next.prev = null;
					nodeToDelete = nodeToDelete.next;
					head = nodeToDelete;
				}
				else{
					head = tail = null;
				}
			}
			// If the node is in the middle of the list
			else if (nodeToDelete!=head && nodeToDelete!=tail){
				nodeToDelete.next.prev = nodeToDelete.prev;
				nodeToDelete.prev.next = nodeToDelete.next;
			}
			length--;
		}
		/**
		 * Removes a node from the list given a direct reference to it.
		 * @param nodeToDelete The node to be removed.
		 */
		public void removeByReferance(Node nodeToDelete){
			if (nodeToDelete==tail || nodeToDelete==head){
				// If the node to delete is the tail (and not also the head)
				if (nodeToDelete!=head){
					nodeToDelete.prev.next = null;
					nodeToDelete = nodeToDelete.prev;
					tail = nodeToDelete;
				}
				// If the node to delete is the head (and not also the tail)
				else if (nodeToDelete!=tail){
					nodeToDelete.next.prev = null;
					nodeToDelete = nodeToDelete.next;
					head = nodeToDelete;
				}
				else{
					head = tail = null;
				}
			}
			// If the node is in the middle of the list
			else if (nodeToDelete!=head && nodeToDelete!=tail){
				nodeToDelete.next.prev = nodeToDelete.prev;
				nodeToDelete.prev.next = nodeToDelete.next;
			}
		}
		/**
		 * Searches for an object in the list using a two-pointer approach,
		 * traversing from both the head and the tail simultaneously.
		 *
		 * This method initializes one pointer at the start and another at the end,
		 * moving them towards the center with each iteration. This can find elements
		 * near the end of the list faster than a simple head-to-tail scan.
		 *
		 * Time Complexity: O(≈ n / 2).this is a clever optimization
		 * @param object The object to search for in the list.
		 * @return {@code true} if the object is found, {@code false} otherwise.
		 */
		public boolean contains(Object object){
			// Initialize two pointers: one at the head (start) and one at the tail (end).
			Node currNodeHead = head;
			Node currNodeTail = tail;
			// Loop until the pointers meet or cross, checking from both ends.
			// int t = 1;
			while (currNodeHead!=null && currNodeTail!=null){
				// System.out.println(t++);
				// Termination condition: pointers have met (for odd-length lists) or are adjacent (for even-length lists).
				if (currNodeHead==currNodeTail || currNodeHead.next==currNodeTail){
					// Final check at the meeting point.
					if(currNodeHead.data.equals(object) || currNodeTail.data.equals(object)){
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
		 * Searches for an object and returns the node containing it.
		 * @param object The object to search for.
		 * @return The {@code Node} containing the object, or {@code null} if not found.
		 */
		private Node search(Object object){
			// Initialize two pointers: one at the head (start) and one at the tail (end).
			Node currNodeHead = head;
			Node currNodeTail = tail;
			// Loop until the pointers meet or cross, checking from both ends.
			// int t = 1;
			while (currNodeHead!=null && currNodeTail!=null){
				// System.out.println(t++);
				// Termination condition: pointers have met (for odd-length lists) or are adjacent (for even-length lists).
				if (currNodeHead==currNodeTail || currNodeHead.next==currNodeTail){
					// Final check at the meeting point.
					if(currNodeHead.data.equals(object)){
						return currNodeHead;
					}
					else if (currNodeTail.data.equals(object)){
						return currNodeTail;
					}
					// If not found at the meeting point, the object is not in the list.
					return null;
				}
				// Check if the data at the current head-side or tail-side pointer matches.
				else if (currNodeHead.data.equals(object)){
					return currNodeHead;
				}
				else if (currNodeTail.data.equals(object)){
					return currNodeTail;
				}
				// If no match is found, move the pointers one step closer to the center.
				currNodeHead = currNodeHead.next;
				currNodeTail = currNodeTail.prev;
			}
			// This fallback is reached if the list is empty or if the loop completes unexpectedly.
			return null;
		}
		/**
		 * Iterates through the list from head to tail, returning the data of one node per call.
		 * This method is **stateful**. It uses an internal pointer (`frontNode`) to keep track
		 * of the current position in the traversal. The first call initializes the traversal
		 * at the head of the list.
		 * Important Considerations:
		 * The traversal state is part of the list instance. There is no public method
		 * to reset the traversal; a new list instance would be needed to start over.
		 * Modifying the list (e.g., using `add`, `remove`) during traversal can lead
		 * to unpredictable behavior or `NullPointerException`s, as the internal pointer
		 * may become invalid.
		 * This method is not thread-safe.</li>
		 * @return The data of the current node in the forward traversal, or {@code null} if the end of the list has been reached.
		 */
		public Object nextFrontTraversal(){
			if (frontNode==null){
                frontNode = head;
            }
			Node currnode = frontNode;
			if (currnode == null) return null; // End of traversal
            frontNode = frontNode.next;
			return currnode.data;
		}
		/**
		 * Iterates through the list from tail to head, returning the data of the next node in sequence.
		 * This method is stateful. On the first call, it returns data from the tail.
		 * @return The data of the next node in the reverse iteration. Returns null if traversal goes out of bounds.
		 */
        public Object nextRearTraversal(){
            if (rearNode==null){
                rearNode = tail;
            }
			Node currnode = rearNode;
			if (currnode == null) return null; // End of traversal
            rearNode = rearNode.prev;
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
        public void printReverseList(){
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
        list.append(5);
		list.append(1);
		list.append(2);
		list.append(3);
        list.append(9);
		list.printList();
		list.removeByValue(4);
		list.removeByValue(0);

		list.printList();
		list.printReverseList();
		System.out.println(list.length);
		
		list.append(2);
		list.append(8);
		list.printList();
		list.printReverseList();
		System.out.println(list.length);
		
        list.removeByValue(2);

		list.printList();
		list.printReverseList();
		System.out.println(list.length);

		list.sort();
		list.insert(0,10);
		list.printList();
		list.printReverseList();
		System.out.println(list.length);

		System.out.println(list.maxVlueNode().data);
		System.out.println(list.minVlueNode().data);

	}
}