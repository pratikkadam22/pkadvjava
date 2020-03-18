package edu.pdx.cs410j.pkadam.myairlineapplication;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the AirlineDumper interface to dump the prettily printed airline to a text file
 */
public class PrettyPrinter implements AirlineDumper {
    /**
     * This method formats a single flight in a pretty way
     * @param flight the object of the class AbstractFlight
     * @return It returns prettily formatted details of a Flight in the form of a string
     */
    public String getpretty(Flight flight, String aname) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        long diff = flight.getArrival().getTime() - flight.getDeparture().getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        String prettytext = "\nAirline : " + aname +
                "\nFlightnumber : " + flight.getNumber() +
                "\nSource : " + flight.getSource() +
                "\nDate of Departure : " + formatter.format(flight.getDeparture()) +
                "\nTime of Departure : " + formatter.getTimeInstance(DateFormat.SHORT).format(flight.getDeparture()) +
                "\nDestination : " + flight.getDestination() +
                "\nDate of Arrival : " + formatter.format(flight.getArrival()) +
                "\nTime of Arrival : " + formatter.getTimeInstance(DateFormat.SHORT).format(flight.getArrival()) +
                "\nDuration (minutes) : " + minutes + "\n" ;
        return prettytext;
    }

    /**
     * This method dumps all the flights of the airline prettily in a text file
     * @param abstractAirline the Airline object to dump in the text file in a pretty way
     */
    @Override
    public void dump(AbstractAirline abstractAirline) {
    }
}

