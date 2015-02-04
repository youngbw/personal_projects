package print.simulation;

/**
 * Queue.java
 * A class that represents a queue object and extends the List class.
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
 * @see Server
 * @see PrintSimulationGUI
 * @see Client
 * @see Random
 */
public class Queue extends List
{
    /**
     * Constructor for a queue object, call the List constructor, setting the
     * name as "queue"
     * @see List
     * @see List.#List(java.lang.String) 
     */
    public Queue()
    {
        super( "queue" );
    }
    
    /**
     * Enters an object into the queue by placing at the back of the list.
     * @param o Object to be stored
     * @see List
     * @see List.#insertAtBack(java.lang.Object) 
     */
    public synchronized void enqueue( Object o )
    {
        insertAtBack( o );
    }

    /**
     * Removes an object from the queue by removing it from the front of the
     * List.
     * @return first Object in the List
     * @throws EmptyListException if List is Empty
     * @see List.#removeFromFront() 
     */
    public synchronized Object dequeue() throws EmptyListException
    {
        return removeFromFront();
    }
    
    /**
     * Returns whether or not this Queue is empty.
     * @return boolean value representing if this queue is empty.
     * @see List.#isEmpty() 
     */
    public synchronized boolean isEmpty()
    {
        return super.isEmpty();
    }
    
    /**
     * Overridden print method that calls List's print method.
     * @return String representing the contents of this queue.
     * @see List.#print() 
     */
    @Override
    public synchronized String print()
    {
        return super.print();
    }
    
    /**
     * Overridden toString method that calls List's print method.
     * @return String representing the contents of this queue.
     * @see #print() 
     * @see List.#print() 
     */
    @Override
    public synchronized String toString() {
        return super.print();
    }
}