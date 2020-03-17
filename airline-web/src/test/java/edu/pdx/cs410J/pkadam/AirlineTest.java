package edu.pdx.cs410J.pkadam;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Airline} class.
 */
public class AirlineTest {
    @Test
    public void airlinenametest() {
        Airline airline = new Airline();
        airline.setName("emirates");
        assertThat(airline.getName(), equalTo("emirates"));
    }

    @Test
    public void airlineaddflighttest() {
        Airline airline = new Airline();
        Flight flight = new Flight();
        airline.setName("emirates");
        flight.setFlightnum("42");
        flight.setSrc("iah");
        flight.setDepart("10/23/2012", "12:23", "am");
        flight.setDest("iad");
        flight.setArrive("12/23/2014","23:24", "pm");
        airline.addFlight(flight);
        assertThat(airline.getFlights().size(), equalTo(1));
    }
}

