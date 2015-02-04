package shipping.hub;

import java.util.Random;

/**
*  Class:	<b>Parcel</b>
*  File:	Parcel.java
* <pre>
*  Description:	This concrete class stores information about a particular parcel
* and its recipient information. Offers only get methods for fields, as well as
* a toString method for printing of Parcel and recipient information.
*  @author 	<i>Brent Young<i/>
*  Environment:	Mac OSx, jdk1.7_17, NetBeans 7.3
*  Date:	4/17/2013
*  @version	%1% %0%
*  @see         ShippingHubGUI
*  @see         Parcel
*  @see         UtilPane
*  @see         java.util.Random
* </pre>
*  History Log:	Created on April 1, 2013, 1:12:19 AM
*/

public class Parcel {
    
    /**
     * Class variable, built to keep track of an ever-increasing list of parcel
     * ID's without having to change individual parcel information.
     * @see #parcelID
     * @see #getParcelID() 
     */
    protected static int overParcelID;
    
    /**
     * Variable containing the time of arrival, when the parcel is scanned
     * into the application.
     * @see java.util.Date
     * @see Parcel
     * @see #getArrivalTime()
     * @see #zip
     */
    private String arrivalTime;
    
    /**
     * Variable containing the parcel identification number for a particular
     * parcel. This field can be used to locate a parcel with ease.
     * @see #overParcelID
     * @see java.util.Iterator
     * @see java.util.ArrayList
     * @see #getParcelID()
     * @see #overParcelID
     */
    private int parcelID;
    
    /**
     * Variable containing the recipient name for a particular parcel.
     * @see Parcel
     * @see #getName()
     * @see #parcelID
     */
    private String name;
    
    /**
     * Variable containing the recipient address for a particular parcel.
     * @see Parcel
     * @see #getAddress()
     * @see #name
     */
    private String address;
    
    /**
     * Variable containing the recipient city for a particular parcel.
     * @see #getCity()
     * @see Parcel
     * @see #address
     */
    private String city;
    
    /**
     * Variable containing the recipient state for a particular parcel.
     * @see #getState()
     * @see Parcel
     * @see #city
     */
    private String state;
    
    /**
     * Variable containing the 5 digit zipcode for a particular parcel.
     * @see Parcel
     * @see ShippingHubGUI
     * @see #getZip()
     * @see #state
     */
    private int zip;
    
    /**
     * Creates new Parcel with null value for all fields, calling the overloaded
     * constructor and passing values of 0 for ints and null for Strings.
     * @see Parcel
     * @see ShippingHubGUI
     * @see UtilPane
     * @see Search
     */
    public Parcel() {
        this("", 0, "", "", "", "", 0);
    }

    /**
     * Builds a new Parcel using recipient information, parcel ID, and Arrival
     * Time.
     * @param arrivalTime current time at moment of scan into application
     * @param parcelID identification number for parcel
     * @param name recipient name
     * @param address recipient address
     * @param city recipient city
     * @param state recipient state
     * @param zip  recipient zipcode
     * @see ShippingHubGUI
     * @see UtilPane
     * @see Search
     * @see #overParcelID
     * @see javax.swing.JFrame
     */
    public Parcel (String arrivalTime, int parcelID, String name,
            String address, String city, String state, int zip) {
        this.arrivalTime = arrivalTime;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.parcelID = parcelID;
    }
    
    /**
     * Returns the name of the recipient for a particular parcel.
     * @return recipient's name
     * @see #name
     * @see Parcel
     * @see ShippingHubGUI
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the address in String form of the recipient for a particular
     * parcel.
     * @return recipient's address
     * @see #getName()
     * @see #address
     * @see Parcel
     * @see ShippingHubGUI
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * Returns the city of the recipient of a particular parcel.
     * @return recipient's city
     * @see #getAddress()
     * @see #city
     * @see Parcel
     * @see ShippingHubGUI
     */
    public String getCity() {
        return city;
    }
    
    /**
     * Returns the state of the recipient of a particular parcel.
     * @return recipient's state
     * @see Parcel
     * @see #state
     * @see ShippingHubGUI
     * @see #getCity()
     */
    public String getState() {
        return state;
    }
    
    /**
     * Returns the zip code of the recipient of a particular parcel.
     * @return recipient's zip code
     * @see #getState()
     * @see Parcel
     * @see ShippingHubGUI
     */
    public int getZip() {
        return zip;
    }
    
    /**
     * Returns the parcel identification number of a particular parcel.
     * @return parcel identification number
     * @see #getZip()
     * @see Parcel
     * @see ShippingHubGUI
     * @see UtilPane
     * @see Search
     * @see #overParcelID
     * @see #parcelID
     */
    public int getParcelID() {
        return parcelID;
    }
    
    /**
     * Returns the current/Arrival Time of a particular parcel.
     * @return Arrival Time (current time at moment of scan into application)
     * @see Parcel
     * @see ShippingHubGUI
     * @see UtilPane
     * @see Search
     * @see #getParcelID()
     * @see #arrivalTime
     */
    public String getArrivalTime() {
        return this.arrivalTime;
    }

    /**
     * Overrides the Object toString method, presenting the parcel information
     * in an easy to understand fashion.
     * @return all parcel information in String form
     * @see Parcel
     * @see ShippingHubGUI
     * @see java.lang.String
     */
    @Override
    public String toString() {
        return "Parcel ID: " + parcelID + " \nArrived At: " + arrivalTime + 
                " \n " + " \nRecipient Info: \n" + " \nName: " + name.toUpperCase() + 
                " \nAddress: " + address.toUpperCase() + " \nCity: " + city.toUpperCase() + 
                "\tState: " + state.toUpperCase() + "\tZipcode: " + zip;
    }
}
