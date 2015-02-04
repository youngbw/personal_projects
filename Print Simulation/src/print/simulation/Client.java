package print.simulation;

/**
 * Client.java
 * A class representing a print task with specific job size in pages and
 * a particular service rate at which it can be serviced.
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
 * @see Queue
 * @see Random
 */
public class Client {
    
    /**
     * Average job size in pages set by user, individual sizes determined by Gaussian
     * distribution.
     * @see Random.#nextGaussian() 
     * @see Server.#randomServiceRate
     * @see PrintSimulationGUI.#meanJobSizeJTextField
     */
    protected static int meanJobSize;
    
    /**
     * static variable that sets the job ID for the upcoming print task.
     * @see #id
     */
    protected static int nextId = 1;
    
    /**
     * Random object used to create the individual job sizes in pages for each
     * print task.
     * @see Random.#nextExponential() 
     * @see #meanJobSize
     */
    protected static Random randomJobSize;
    
    /**
     * Job ID for a particular print task.
     * @see #nextId
     */
    protected int id;
    
    /**
     * Actual individual job size in pages for a particular print task.
     * @see #meanJobSize
     * @see Random.#nextGaussian() 
     */
    private int jobSize;
    
    /**
     * Time in seconds that service was begun on a particular print task.
     * @see PrintSimulationGUI
     * @see #beginService(print.simulation.Server, int) 
     */
    protected int timeBegan;
    
    /**
     * Time in seconds that service was ended on a particular print task.
     * @see Server
     * @see #endService(int) 
     */
    protected int timeEnded;
    
    /**
     * Time in seconds that a print task arrived in the queue.
     * @see Queue
     * @see PrintSimulationGUI
     * @see PrintSimulationGUI.#simOneSecond(int) 
     */
    protected int timeArrived;
    
    /**
     * Individual service rate for a particular print task.
     * @see Server
     * @see Server.#individualServiceRate
     */
    double individualServiceRate;
    
    /**
     * Amount of time in seconds a particular print task will take to complete
     * once service has begun.
     * @see Server
     * @see #getServiceTime() 
     */
    double serviceTime;
    
    /**
     * Amount of time in seconds a print task spends in the queue waiting to
     * begin service.
     * @see Queue
     * @see PrintSimulationGUI
     * @see PrintSimulationGUI.#simOneSecond(int) 
     */
    protected int waitTime = 0;
    
    /**
     * Server reference, specifying which server in the simulation will be
     * handling the service of this print task.
     * @see Server
     * @see PrintSimulationGUI.#simOneSecond(int) 
     */
    Server myServer;
    
    /**
     * Constructor for Client object, sets client ID and a randomly generated
     * job size in pages.
     * @param time number of seconds that have passed in the simulation.
     * @see Server
     * @see PrintSimulationGUI
     */
    public Client(int time) {
        this.id = nextId;
        nextId++;
        randomJobSize = new Random(meanJobSize);
        this.jobSize = (int)randomJobSize.nextExponential();
        timeArrived = time;

        //print client job arrival
        PrintSimulationGUI.line += "Job #" + id 
                + " has arrived at time " + timeArrived + " with " + jobSize + "pages.\n";
        PrintSimulationGUI.outJTextArea.setText(PrintSimulationGUI.line);
        
        
    }
    
    /**
     * Returns the job size for this client object
     * @return number of pages this client has to print.
     * @see #jobSize
     */
    public int getJobSize() {
        return jobSize;
    }
    
    /**
     * Returns the amount of time this client spent in the queue.
     * @return number of seconds this client spends in the queue.
     * @see PrintSimulationGUI.#simOneSecond(int) 
     */
    public int getWaitTime() {
        return waitTime;
    }
    
    /**
     * Sets the amount of time in seconds that this client will take once
     * service begins to finish printing.
     * @return number of seconds this job will take to endService once service
     * has begun
     * @see #serviceTime
     */
    public int getServiceTime() {
        int service = (int)Math.ceil(this.getJobSize()/this.individualServiceRate);
        if (service < 1) service = 1;
        return service;
    }
    
    /**
     * Begins the service on this client by setting the server reference and
     * the number of seconds it will take to finish serving.
     * @param server printer that will serve this client
     * @param time number of seconds that have passed in this simulation
     * @see Server
     * @see PrintSimulationGUI
     * @see #getServiceTime() 
     */
    public void beginService(Server server, int time) {
        timeBegan = time;
        myServer = server;
        this.serviceTime = getServiceTime();
        
        //print begin service
        PrintSimulationGUI.line += "Printer " + server.id + "(" + 
                Math.round(myServer.meanServiceRate) + "%, " + 
                Math.round(individualServiceRate * 100)+ "%) begins Job #" + id
                + " at time " + time + ".";
        PrintSimulationGUI.outJTextArea.setText(PrintSimulationGUI.line); 
    }
    
    /**
     * Ends service on this Client when the current time in seconds is equal
     * to timeBegan + serviceTime
     * @param time number of seconds that have passed in this simulation
     * @see #beginService(print.simulation.Server, int) 
     * @see #timeBegan
     * @see #serviceTime
     */
    public void endService(int time) {
        PrintSimulationGUI.line += "Printer " + myServer.id + "(" + 
                Math.round(myServer.meanServiceRate) + "%, " + 
                Math.round(individualServiceRate * 100) 
                + "%) ends Job #" + id + " at time " + time + ".\n";
        PrintSimulationGUI.outJTextArea.setText(PrintSimulationGUI.line);
        timeEnded = time;
        PrintSimulationGUI.finished.add(this); //for stats later
//        myServer = null; // not nullified for detailed stats
    }
    
    /**
     * Overridden toString method detailing this Client's ID and individual
     * job size.
     * @return String showing Client details
     */
    @Override
    public String toString() {
        return "#" + id + "(" + jobSize + ")";
    }
}
