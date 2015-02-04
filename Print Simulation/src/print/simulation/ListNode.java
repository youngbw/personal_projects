package print.simulation;

/**
 * ListNode.java
 * A class that represents one node of a List object, that contains an Object
 * data.
 * statistics.
 * <pre>
 *    Project: print.simulation
 *    Platform: jdk 1.6.0_14; NetBeans IDE 6.8; Mac OSx
 *    Course: CS 143
 *    Hours: 2 hours
 *    Created on May 25th, 2013, 10:00:00 PM
 * </pre>
 *
 * @author	brent.young@go.shoreline.edu
 * @version 	%1% %1%
 * @see PrintSimulationGUI
 * @see Queue
 * @see List
 */
class ListNode
{
    // package access data so class List can access it directly
    /**
     * Object to be inserted and removed from List.
     * @see List.#insertAtBack(java.lang.Object) 
     */
    Object data;
    
    /**
     * Object representing the next node of the List.
     * @see List
     */
    ListNode next;
    
    /**
     * Constructor: Create a ListNode that refers to Object o.
     * @see List
     * @see List.#List(java.lang.String) 
     */
    ListNode( Object o )
    {
        this( o, null );
    }
    /** Constructor: Create a ListNode that refers to Object o and
     * to the next ListNode in the List.
     * @see List
     * @see #next
     * @see #data
     */
    ListNode( Object o, ListNode nextNode )
    {
        data = o; // this node refers to Object o
        next = nextNode; // set next to refer to next
    }
    
    /**
     * Return a reference to the Object in this node.
     * @return reference to the Object in this node.
     * @see #data
     */
    Object getObject()
    {
        return data;
    }
    
    /**
     * Return the next node.
     * @return the next node, following the data node.
     * @see #next
     */
    ListNode getNext()
    {
        return next;
    }
}