package edu.pdx.cs410J.pkadam;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {

  @Test
  public void getArrivalStringNeedsToBeImplemented() {
    Flight flight = new Flight();
    flight.setFlightnum("42");
    flight.setSrc("iah");
    flight.setDepart("10/23/2012", "12:23", "am");
    flight.setDest("iad");
    flight.setArrive("12/23/2014","2:24", "pm");
    assertThat(flight.getArrivalString(), equalTo("12/23/2014 2:24 pm"));
  }

  @Test
  public void flightnumbertest() {
    Flight flight = new Flight();
    flight.setFlightnum("123");
    assertThat(flight.getNumber(), equalTo(123));
  }

  @Test
  public void sourcetest() {
    Flight flight = new Flight();
    flight.setSrc("iah");
    assertThat(flight.getSource(), equalTo("iah"));
  }

  @Test
  public void desttest() {
    Flight flight = new Flight();
    flight.setDest("iad");
    assertThat(flight.getDestination(), equalTo("iad"));
  }

  @Test
  public void departurestring() {
    Flight flight = new Flight();
    flight.setDepart("03/03/2017", "12:00", "pm");
    assertThat(flight.getDepartureString(), equalTo("03/03/2017 12:00 pm"));
  }

  @Test
  public void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }
  
}
