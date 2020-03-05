package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.pkadam.PrettyPrinter;
import edu.pdx.cs410J.pkadam.Airline;
import edu.pdx.cs410J.pkadam.Flight;
import org.junit.Test;

import java.io.IOException;

public class PrettyPrinterTest
{
    @Test
    public void testPretty() throws IOException {
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        Airline airline = new Airline();
        airline.setName("emirates");
        Flight flight = new Flight();
        flight.setFlightnum("123");
        flight.setSrc("PDX");
        flight.setDest("ORD");
        flight.setDepart("01/07/2019", "07:00","am");
        flight.setArrive("01/07/2019", "09:00","am");
        airline.addFlight(flight);

        //airline = new Airline("Emirates");
        flight = new Flight();
        flight.setFlightnum("245");
        flight.setSrc("PDX");
        flight.setDest("ORD");
        flight.setDepart("02/07/2019", "07:00","pm");
        flight.setArrive("03/07/2019", "09:00","pm");
        airline.addFlight(flight);
        prettyPrinter.dump(airline);
    }
}
