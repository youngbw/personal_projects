package print.simulation;

/**
 * Random.java
 * A class that generates random double values using either the Gaussian
 * or Poisson distributions.
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
 * @see Client
 * @see Server
 * @see Queue
 * @see ListNode
 */
public class Random extends java.util.Random {
    
    /**
     * Mean around which the distributions will operate.
     * @see #nextGaussian() 
     */
    private double mean;
    
    /**
     * Standard Deviation around which the distributions will be generated,
     * using the mean as a pivot point.
     * @see #mean
     */
    private double standardDeviation;
    
    /**
     * Constructor: sets both the mean and standard deviation as the passed value.
     * @param mean double mean around which the distributions will be centered
     * @see #mean
     * @see #standardDeviation
     */
    public Random(double mean)
    {
    this.mean = mean;
    this.standardDeviation = mean;
    }
    
    /**
     * Constructor: sets the mean and standard deviation as the two separate
     * passed values.
     * @param mean double value around which the distributions will be centered
     * @param standardDeviation double value representing where percentages of
     * the randomly generated values will fall around the mean.
     * @see #nextGaussian() 
     * @see #mean
     * @see #standardDeviation
     */
    public Random(double mean, double standardDeviation)
    {
    this.mean = mean;
    this.standardDeviation = standardDeviation;
    }
    
    @Override
    /**
     * Returns a double value that falls in a Gaussian curve based on the mean.
     * @return a double random number that is normally distributed with
     * the given mean and standard deviation.
     * @see #mean
     * @see #nextExponential()
     */
    public double nextGaussian()
    {
    double x = super.nextGaussian(); // x = normal(0.0, 1.0)
    return x*standardDeviation + mean;
    }
    
    /**
    * Returns a double value that is exponentially distributed around the mean.
    * @return a double random number that is exponentially distributed
    * with the given mean
    * @see  #mean
    * @see #nextGaussian() 
    * @see #intNextExponential() 
    */
    public double nextExponential() 
    {
        return -mean*Math.log(1.0 - nextDouble());
    }
    
    /**
    * Returns an int value that is exponentially distributed around the mean.
    * @return an int random number that is exponentially distributed
    * with the given mean
    * @see  #nextGaussian() 
    * @see #nextExponential() 
    */
    public int intNextExponential()
    {
        return (int)Math.ceil(nextExponential());
    }
}

