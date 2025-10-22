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
		 * Constructs a new DynamicLinkedList and initializes it with the provided objects.
		 * The objects are appended to the list in the order they are given.
		 * @param objects A variable number of objects to be added to the list upon creation.
		 */
		public DynamicLinkedList(Object... objects){
			for (Object object : objects){
				append(object);
			}
		}

		/**
		 * Counts the occurrences of a specific object in the list.
		 * It uses a two-pointer approach, traversing from both head and tail towards the center.
		 * This allows checking two nodes per iteration.
		 * @param object The object to count in the list.
		 * @return The number of times the object appears in the list.
		 */
		public int count(Object object){
			Node start = head;
			Node end = tail;
			int startIndex = 0;
			int endIndex = length-1;
			int counter = 0;
			while (startIndex<=endIndex && start!=null && end!=null){
				// Check the node from the start
				if (start.data.equals(object)){
					counter++;
				}
				// Check the node from the end, avoiding double-counting the middle element
				if (end.data.equals(object) && end!=start){
					counter++;
				}
				start = start.next;
				end = end.prev;
				// Move indices towards the center
				startIndex++;
				endIndex--;
			}
			return counter;
		}
		/**
		 * Finds the node with the maximum value in the list based on the data's hash code.
		 * It uses a two-pointer approach, traversing from both head and tail.
		 * @return The {@code Node} containing the maximum value. Returns {@code head} if the list is empty or has one element.
		 */
        public Node maxVlueNode(){
            Node maxValNode = head;
			Node start = head;
			Node end = tail;
			int startIndex = 0;
			int endIndex = length-1;
			while (startIndex<=endIndex && start!=null && end!=null){
				if (start.data.hashCode() > maxValNode.data.hashCode()){
					maxValNode = start;
				}
			    if (end.data.hashCode() > maxValNode.data.hashCode()){
					maxValNode = end;
				}
				// If no match is found, move the pointers one step closer to the center.
				start = start.next;
				end = end.prev;
				startIndex++;
				endIndex--;
            }
            return maxValNode;
        }
		/**
		 * Finds the node with the minimum value in the list based on the data's hash code.
		 * It uses a two-pointer approach, traversing from both head and tail.
		 * @return The {@code Node} containing the minimum value. Returns {@code head} if the list is empty or has one element.
		 */
		public Node minVlueNode(){
            Node minValNode = head;
			Node start = head;
			Node end = tail;
			int startIndex = 0;
			int endIndex = length-1;
			while (startIndex<=endIndex && start!=null && end!=null){
				if (start.data.hashCode() < minValNode.data.hashCode()){
					minValNode = start;
				}
			    if (end.data.hashCode() < minValNode.data.hashCode()){
					minValNode = end;
				}
				// If no match is found, move the pointers one step closer to the center.
				start = start.next;
				end = end.prev;
				startIndex++;
				endIndex--;
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
			Node start = null;
			Node end = null;
			for (int i=0 ; i<length ; i++){
				Node maxValNode = maxVlueNode(); // Finds the current maximum value node
				removeByReferance(maxValNode);
				Node newNode = new Node(maxValNode.data);
	            if (start==null){
	                start = newNode;
	                end = start;
	            }
				else{
    	            newNode.prev = end;
                    end.next = newNode;
			        end = newNode;
				}
			}
			head = start; // Replace the old list with the new sorted list
			tail = end;
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
			Node start = head;
			Node end = tail;
			// Handle insertion at the beginning of the list
			if (index==0){
				newNode.next=start;
				start.prev = newNode;
				head = newNode;
			}		
			else{
				start = start.next;
				// Use two pointers to find the insertion point faster
				int endIndex;
				for (int startIndex=1 ; startIndex<length ; startIndex++) {
				    endIndex = length-startIndex;
					// If the index is closer to the head
				    if (startIndex==index) {
					    newNode.next = start;
					    newNode.prev = start.prev;
					    start.prev.next = newNode;
						start.prev = newNode;
					    break;
				    }
				    else if (endIndex==index){
					    newNode.next = end;
					    newNode.prev = end.prev;
					    end.prev.next = newNode;
						end.prev = newNode;
					    break;
				    }
				    start = start.next;
				    end = end.prev;
	    		}
			}
			length++;
		}
		/**
		 * Removes and returns the element at the specified index.
		 * It uses a two-pointer approach to find the node at the index efficiently,
		 * starting from both the head and the tail.
		 * @param index The index of the element to be removed.
		 * @return The data of the removed node, or {@code null} if the index is out of range.
		 */
		public Object pop(int index){
			if (index>=length || index<0){
				System.out.println("the index out of range!");
				return null;
			}
			Node start = head;
			Node end = tail;
			int startIndex = 0;
			int endIndex = length-1;
			while (startIndex<=endIndex){
				if (startIndex==index) {
					// Found the node from the start
				    removeByReferance(start);
					length--;
				    return start.data;
			    }
			    else if (endIndex==index){
					// Found the node from the end
				    removeByReferance(end);
					length--;
				    return end.data;
			    }
			    start = start.next;
			    end = end.prev;
				startIndex++;
				endIndex--;
	    	}
			return null;
		}
		/**
		 * Updates the data of the node at a specific index with a new value.
		 * It uses a two-pointer approach to find the node efficiently.
		 * @param index The index of the node to update.
		 * @param value The new value to be stored in the node.
		 */
		public void update(int index, Object value){
			if (index>=length || index<0){
				System.out.println("the index out of range!");
				return;
			}
			Node start = head;
			Node end = tail;
			int startIndex = 0;
			int endIndex = length-1;
			while (startIndex<=endIndex){
				if (startIndex==index) {
					// Found the node from the start
				    start.data = value;
				    return ;
			    }
			    else if (endIndex==index){
					// Found the node from the end
				    end.data = value;
				    return;
			    }
			    start = start.next;
			    end = end.prev;
				startIndex++;
				endIndex--;
	    	}
		}
		/**
		 * Removes the first occurrence of a node with the given value.
		 * @param object The value of the node to be removed.
		 */
		public void removeByValue(Object object){
			Node nodeToDelete = find(object);
			// This fallback is reached if the list is empty or if the loop completes unexpectedly.
			// return null;
			if (nodeToDelete==null){
				System.out.println(object+" not on the list!");
				return;
			}
			removeByReferance(nodeToDelete);
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
			Node start = head;
			Node end = tail;
			int startIndex = 0;
			int endIndex = length-1;
			while (startIndex<=endIndex){
				if (start.data.equals(object) || end.data.equals(object)){
					return true;
				}
				// If no match is found, move the pointers one step closer to the center.
				start = start.next;
				end = end.prev;
				startIndex++;
				endIndex--;
			}
			// This fallback is reached if the list is empty or if the loop completes unexpectedly.
			return false;
		}
		/**
		 * Retrieves the element at the specified index in the list.
		 * It uses a two-pointer approach, traversing from both head and tail,
		 * to find the element more efficiently depending on its position.
		 * @param index The index of the element to retrieve.
		 * @return The data at the specified index, or {@code null} if the index is out of range.
		 */
		public Object get(int index){
			if (index>=length || index<0){
				System.out.println("the index out of range!");
				return null;
			}
			Node start = head;
			Node end = tail;
			int startIndex = 0;
			int endIndex = length-1;
			while (startIndex<=endIndex){
				if (startIndex==index) {
					// Found from the start
				    return start.data;
			    }
			    else if (endIndex==index){
					// Found from the end
				    return end.data;
			    }
			    start = start.next;
			    end = end.prev;
				startIndex++;
				endIndex--;
	    	}
			return null;
		}
		/**
		 * Searches for an object and returns the node containing it.
		 * @param object The object to search for.
		 * @return The {@code Node} containing the object, or {@code null} if not found.
		 */
		public Node find(Object object){
			Node node = null;
			Node start = head;
			Node end = tail;
			int startIndex = 0;
			int endIndex = length-1;
			while (startIndex<=endIndex){
				if (start.data.equals(object)){
					node = start;
					break;
				}
				else if (end.data.equals(object)){
					node = end;
				}
				start = start.next;
				end = end.prev;
				startIndex++;
				endIndex--;
			}
			return node;
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
		 * This method is not thread-safe.
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
		/**
		 * Checks if the given object is a String or Character that represents a numeric digit.
		 * @param obj The object to check.
		 * @return {@code true} if the object is a string or character representing a digit,
		 *         {@code false} otherwise.
		 */
		public boolean isDigit(Object obj){
            if (obj instanceof String){
                try{
                    Integer n =  Integer.valueOf(obj.toString());
                    if(n.toString().equals(obj)){
                        return true;
                    }
                }
                catch (NumberFormatException e){
                    return false;
                }
            }
            else if (obj instanceof Character){
                try{
                    Integer n = Integer.valueOf(obj.toString());
                    Character c = n.toString().toCharArray()[0];
                    if(c.equals(obj)){
                        return true;
                    }
                }
                catch (NumberFormatException e){
                    return false;
                }
            }
            return false;
        }
    }
	public static void main(String[] args) {
		DynamicLinkedList list = new DynamicLinkedList(0,5,1,2);
		list.printList();
		list.printReverseList();
		System.out.println(list.length); 

		list.append(3);
        list.append(9);
		list.printList();
		list.printReverseList();
		System.out.println(list.length); 

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
		list.append(8);
		list.printList();
		list.printReverseList();
		System.out.println(list.length);

		list.sort();
		list.insert(0,10);
		list.printList();
		list.printReverseList();
		System.out.println(list.length);

		System.out.println("poped value: "+list.pop(5));
		System.out.println(list.maxVlueNode().data);
		System.out.println(list.minVlueNode().data);
		list.printList();
		list.printReverseList();
		System.out.println(list.length);

		System.out.println("8 acures: "+list.count(8));
		list.update(0, "10");
		list.printList();
		list.printReverseList();
		System.out.println(list.length);
		System.out.println(list.contains(10));

		list.append(8);
		list.removeByValue(8);
		list.printList();
		list.printReverseList();
		System.out.println(list.length);
		System.out.println(list.contains(8));
		System.out.println("8 acures: "+list.count(8));
		System.out.println(list.get(6));
	}
}