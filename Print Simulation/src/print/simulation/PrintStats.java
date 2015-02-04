package print.simulation;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * PrintStats.java
 * A class that facilitates some common operations carried out when calculating
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
 * @see Server
 * @see PrintSimulationGUI
 * @see Queue
 * @see Random
 */
public class PrintStats {
    
    /**
     *ArrayList of Objects to gather stats on.
     * @see PrintSimulationGUI.#finished
     */
    ArrayList<Object> list = new ArrayList<>();
    
    /**
     * Temporary Client reference to be used wen iterating through the passed
     * ArrayList
     * @see #list
     */
    Client temp;
    
    /**
     * Constructor for the PrintStats Object, accepts an ArrayList of Objects
     * to gather stats on.
     * @param foo List of objects on which stat operations will be performed
     * @see #list
     */
    public PrintStats(ArrayList foo) {
        list.addAll(foo);
        
    }
    
    /**
     * Returns the amount of jobs that were completed in this simulation.
     * @return int representing the total number of finished print tasks
     * @see PrintSimulationGUI.#finished
     */
    public int totalJobsFinished() {
        return list.size();
    }
    
    /**
     * Finds the average amount of time in seconds a print task took to be
     * served.
     * @return int mean service time between all jobs
     * @see Client.#getServiceTime() 
     */
    public int findMeanServiceTime() {
        int clientCount = 0;
        int serviceTimeCount = 0;
        if (!this.isEmpty()) {
            Iterator<Object> iter = list.iterator();
            while (iter.hasNext()) {
                temp = (Client)iter.next();
                clientCount++;
                serviceTimeCount += temp.getServiceTime();
            }
            serviceTimeCount /= clientCount;
            return serviceTimeCount;
        } else {
            return 0;
        }
    }
    
    /**
     * Finds the maximum time in seconds it took for a job to be served.
     * @return int maximum time it took to finish a particular print task
     * @see Client.#getServiceTime() 
     */
    public int findMaxServiceTime() {
        int max = 0;
        if (!this.isEmpty()) {
            Iterator<Object> iter = list.iterator();
            while (iter.hasNext()) {
                temp = (Client)iter.next();
                int currentServiceTime = temp.getServiceTime();
                if (currentServiceTime > max) {
                    max = currentServiceTime;
                } 
            }
            return max;
        } else {
            return 0;
        }
    }
    
    /**
     * Finds the minimum time in seconds it took to serve a particular print task.
     * @return int seconds it took to finish the quickest print task.
     * @see Client.#getServiceTime() 
     */
    public int findMinServiceTime() {
        int min = 0;
        if (!this.isEmpty()) {
            Iterator<Object> iter = list.iterator();
            while (iter.hasNext()) {
                temp = (Client)iter.next();
                int currentServiceTime = temp.getServiceTime();
                if (currentServiceTime < min) {
                    min = currentServiceTime;
                }
            }
            return min;
        } else {
            return 0;
        }
    }
    
    /**
     * Finds the average time all jobs spent in the queue.
     * @return int mean number of seconds all jobs spent in the queue.
     * @see PrintSimulationGUI.#printQueue
     * @see Client.#getWaitTime() 
     */
    public int findMeanWaitTime() {
        int waitTimeCount = 0;
        int clientCount = 0;
        if (!this.isEmpty()) {
            Iterator<Object> iter = list.iterator();
            while (iter.hasNext()) {
                temp = (Client)iter.next();
                clientCount++;
                waitTimeCount += temp.waitTime;
            }
            return waitTimeCount /= clientCount;
        } else {
            return 0;
        }
    }
    
    /**
     * Finds the maximum time in seconds a particular print task spent in the
     * queue.
     * @return int maximum amount of time in seconds a job spent in the queue.
     * @see PrintSimulationGUI.#printQueue
     * @see Client.#getWaitTime() 
     */
    public int findMaxWaitTime() {
        int max = 0;
        if (!this.isEmpty()) {
            Iterator<Object> iter = list.iterator();
            while (iter.hasNext()) {
                temp = (Client)iter.next();
                int currentWaitTime = temp.waitTime;
                if (currentWaitTime > max) {
                    max = currentWaitTime;
                }
            }
            return max;
        } else {
            return 0;
        }
    }
    
    /**
     * Finds the minimum time in seconds a particular print task spent in the
     * queue.
     * @return int minimum amount of time in seconds a job spent in the queue.
     * @see PrintSimulationGUI.#printQueue
     * @see Client.#getWaitTime() 
     */
    public int findMinWaitTime() {
        int min = 0;
        int clientCount = 0;
        if (!this.isEmpty()) {
            Iterator<Object> iter = list.iterator();
            while (iter.hasNext()) {
                temp = (Client)iter.next();
                clientCount++;
                int currentWaitTime = temp.getWaitTime();
                if (clientCount == 1 || currentWaitTime < min) {
                    min = currentWaitTime;
                }
            }
            return min;
        } else {
            return 0;
        }
    }
    
    /**
     * Finds the average amount of time in seconds all jobs spent in the system,
     * between both the queue and the time spent in service.
     * @return int mean time in seconds a job spent in the system
     * @see Client.#getServiceTime() 
     * @see Client.#getWaitTime() 
     */
    public int findAverageTimeSpent() {
        int avgTimeSpent = 0;
        if (!this.isEmpty()) {
           Iterator<Object> iter = list.iterator();
            while (iter.hasNext()) {
                temp = (Client)iter.next();
                int totalInd = temp.getServiceTime() + temp.getWaitTime();
                avgTimeSpent += totalInd;
            }
            avgTimeSpent /= list.size();
            return avgTimeSpent; 
        } else {
            return 0;
        }
        
    }
    
    /**
     * Finds whether or not the passed ArrayList has size of 0 by calling the
     * super.isEmpty method.
     * @return boolean true if the ArrayList list has size 0
     * @see #list
     * @see List.#isEmpty() 
     */
    private boolean isEmpty() {
        return list.isEmpty();
    }
    
    /**
     * Overridden toString method detailing what the purpose of the class is.
     * @return String representing the purpose of this class.
     * @see String
     */
    public String toString() {
        return "Statistics representing  the last print simulation:\n";
    }
}
