package edu.pdx.cs410j.pkadam.myairlineapplication;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class extends the AbstractAirline superclass and implement all its methods
 */
public class Airline extends AbstractAirline {
    ArrayList<Flight> arrli = new ArrayList<Flight>();
    String air_name;

    /**
     * This method is used to set the name of the airline
     * @param aname the name of the airline
     */
    public void setName(String aname) {
        this.air_name = aname;
    }

    /**
     * This method is used to retrieve the name of the airline
     * @return This returns the name of the airline
     */
    @Override
    public String getName() {
        return this.air_name;
    }

    /**
     * This method is used to add the Flight to the Airline
     * @param abstractFlight The object of the class AbstractFlight
     */
    @Override
    public void addFlight(AbstractFlight abstractFlight) {
        arrli.add((Flight)abstractFlight);
    }

    /**
     * This method is used to retrieve the flights added to the airline
     * @return This returns a list of flights that have been added to the airline
     */
    @Override
    public ArrayList<Flight> getFlights() {
        Collections.sort(arrli);
        return arrli;
    }
}

