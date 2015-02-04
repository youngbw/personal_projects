package print.simulation;

/**
 * List.java
 * A class that creates a List to be used as a stack, queue, or otherwise. With
 * methods to insert and remove Objects as nodes from either the back or the
 * front of the list.
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
 * @see ListNode
 */
public class List
{
    /**
     * The first Object node in the List.
     * @see ListNode
     */
    protected ListNode firstNode;
    
    /**
     * The last Object node in the List.
     * @see ListNode
     */
    protected ListNode lastNode;
    
    /**
     * The name of this List, commonly set as the type of data structure
     * being used.
     * @see Queue
     * @see #List(java.lang.String) 
     */
    private String name; // String like "list" used in printing

    /**
     * Default Constructor: Construct an empty List with "list" as the name.
     * @see #List(java.lang.String) 
    */
    public List()
    {
        this( "list" );
    }

    /**
     * Constructor: Construct an empty List with s as the name
     * @see ListNode
     * @see #name
     */
    public List( String s )
    {
        name = s;
        firstNode = lastNode = null;
    }

    /**
     * Insert an Object at the front of the List
     * If List is empty, firstNode and lastNode will refer to
     * the same object. Otherwise, firstNode refers to new node.
     * @see ListNode
     * @see #isEmpty() 
     * @see #insertAtBack(java.lang.Object) 
     */
    public synchronized void insertAtFront( Object insertItem )
    {
        if ( isEmpty() )
            firstNode = lastNode = new ListNode( insertItem );
        else
            firstNode = new ListNode( insertItem, firstNode );
    }

    /**
     * Insert an Object at the end of the List
     * If List is empty, firstNode and lastNode will refer to
     * the same Object. Otherwise, lastNode's next instance
     * variable refers to new node.
     * @see ListNode
     * @see #isEmpty()
     * @see #insertAtFront(java.lang.Object) 
     */
    public synchronized void insertAtBack( Object insertItem )
    {
    if ( isEmpty() )
        firstNode = lastNode = new ListNode( insertItem );
    else
        lastNode = lastNode.next = new ListNode( insertItem );
    }
    
    /**
     * Remove the first node from the List.
     * @see ListNode
     * @see #removeFromBack() 
     */
    public synchronized Object removeFromFront() throws EmptyListException
    {
        Object removeItem = null;
        if ( isEmpty() )
            throw new EmptyListException( name );
        removeItem = firstNode.data; // retrieve the data
        // reset the firstNode and lastNode references
        if ( firstNode.equals( lastNode ) )
            firstNode = lastNode = null;
        else
            firstNode = firstNode.next;
        return removeItem;
    }
    
    /**
     * Remove the last node from the List.
     * @see ListNode
     * @see #removeFromFront() 
     */
    public synchronized Object removeFromBack() throws EmptyListException
    {
        Object removeItem = null;
        if ( isEmpty() )
            throw new EmptyListException( name );
        removeItem = lastNode.data; // retrieve the data
        // reset the firstNode and lastNode references
        if ( firstNode.equals( lastNode ) )
            firstNode = lastNode = null;
        else
        {
            ListNode current = firstNode;
            while ( current.next != lastNode ) // not last node
            current = current.next; // move to next node
            lastNode = current;
            current.next = null;
        }
        return removeItem;
    }
    
    /**
     * Return true if the List is empty.
     * @return boolean whether or not the List has size of 0
     * @see Queue.#isEmpty() 
     */
    public synchronized boolean isEmpty()
    { 
        return firstNode == null;
    }
    
    /**
     * Output the List contents, if the List is empty returns a string saying
     * such, otherwise prints the jobs it currently contains.
     * @return String representing the contents of the List
     * @see Queue.#toString() 
     */
    public synchronized String print()
    {
        if ( isEmpty() )
        {
            return "The " + name + " is now empty.\n";
            
        } else {
            
        }
        String line = "";
        line += "The " + name + " contains " + PrintSimulationGUI.queueSize
                     + " jobs: [";
        ListNode current = firstNode;
        while ( current != null )
        {
            line +=current.data.toString();
            if (current != lastNode) line += ", ";
            current = current.next;
        }
        line += "]\n" ;
        return line;
    }
}