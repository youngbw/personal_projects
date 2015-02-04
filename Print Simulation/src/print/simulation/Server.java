package print.simulation;

/**
 * Server.java
 * A class representing a printer that can service one print task at a time
 * using Gaussian distributions to mirror realistic parameters that could be
 * passed to a printer.
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
 * @see Client
 * @see PrintSimulationGUI
 * @see Queue
 * @see Random
 */
public class Server {
    
    /**
     * Random object used to determine the average service rate for each
     * @see Random
     */
    public static Random randomMeanServiceRate = new Random(100, 20);
    
    /**
     * Random object used to determine service rates for individual print tasks.
     * @see Client
     * @see #Server() 
     */
    public static Random randomServiceRate;
    
    /**
     * Average service rate of a particular printer.
     * @see #randomServiceRate
     */
    double meanServiceRate;
    
    /**
     * Counter for server ID's.
     * @see #id
     */
    static char nextId = 'A';
    
    /**
     * ID for a particular server object.
     * @see #nextId
     */
    char id;
    
    /**
     * A value in seconds that determines when the endServing method will be
     * called to halt service on a particular print task.
     * @see Client
     * @see #endServing(int) 
     * @see Client.#getServiceTime() 
     */
    private int timeServiceEnds;
    
    /**
     * Individual service rates for each print task. Used to portray
     * interference  in the network.
     * @see #randomServiceRate
     */
    double individualServiceRate;
    
    /**
     * Client object representing the current print task.
     * @see Client
     * @see #beginServing(print.simulation.Client, int) 
     */
    protected Client myClient;
    
    /**
     * Number of print tasks finished. Used for statistics gathering.
     * @see PrintStats
     * @see PrintSimulationGUI.#statsJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    protected int numFinished;
    
    /**
     * Total amount of time print tasks take to be served over all servers.
     * Used for statistics gathering.
     * @see #numFinished
     */
    protected int totalServiceTime;
    
    /**
     * Number of seconds a particular server is idle (is not currently serving
     * a print task). Used for statistics gathering.
     */
    protected double isIdle;
    
    /**
     * Constructor for a server object, sets the ID and the servers average
     * service rate.
     * @see #meanServiceRate
     * @see #id
     */
    public Server() {
        this.id = nextId;
        nextId++;
        this.meanServiceRate = randomMeanServiceRate.nextGaussian();
        randomServiceRate = new Random(meanServiceRate, 10);
    }
    
    /**
     * Returns the current print task.
     * @return Client print task currently being served.
     * @see Client
     */
    public Client getClient() {
        return myClient;
    }
    
    /**
     * Method: beginServing(Client, int)
     * Starts service on a single print task, sets the Client reference, and
     * sets the timeServiceEnds local variable.
     * @param client print task to begin service on.
     * @param time number of seconds the simulation has been running.
     * @see Client
     * @see Client.#getServiceTime() 
     * @see Client.#beginService(print.simulation.Server, int) 
     */
    public void beginServing(Client client, int time) {
       this.myClient = client;
       individualServiceRate = randomServiceRate.nextGaussian() / 100;
       this.myClient.individualServiceRate = this.individualServiceRate;
       this.myClient.beginService(this, time);
       this.timeServiceEnds = client.getServiceTime() + time;
       totalServiceTime += client.getServiceTime();
            //System.out.println("Service will end: " + timeServiceEnds);
    }
    
    /**
     * Method: endServing(int)
     * Stops service on a single print task and nullifies the Client reference,
     * allowing the isFree method to return true and the server to accept
     * another print task.
     * @param time number of seconds the simulation has been running.
     * @see Client
     * @see Client.#endService(int) 
     */
    public void endServing(int time) {
        numFinished++;
        this.myClient.endService(time);
        //timeServiceEnds = 0;
        this.myClient = null;
    }
    
    /**
     * Method: getTimeServiceEnds()
     * Returns the time that the current print task will finish.
     * @return int which second of the simulation the server will finish 
     * the current print task.
     * @see Client
     * @see Client.#getServiceTime() 
     * @see Client.#serviceTime
     */
    public int getTimeServiceEnds() {
        return timeServiceEnds;
    }
    
    /**
     * Method: isFree()
     * Returns whether or not the client reference is null, or in other words
     * whether or not this server is currently servicing a client.
     * @return boolean true if client reference is null.
     * @see Client
     * @see #endServing(int) 
     */
    public boolean isFree() {
        if (this.myClient == null) return true;
        return false;
    }
    
    /**
     * Method: toString()
     * Overridden toString method that returns the server ID and both its
     * overall average service rate as well as its most recent individual
     * print task's service rate.
     * @return String server ID and service rates
     * @see #individualServiceRate
     * @see #meanServiceRate
     */
    @Override
    public String toString() {
        return id + "(" + Math.round(meanServiceRate) + ", " + Math.round(individualServiceRate * 100) + "%)";
    }
    
}
