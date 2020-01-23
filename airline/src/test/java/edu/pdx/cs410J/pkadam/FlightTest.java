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
    flight.setSrc("mum");
    flight.setDepart("10/23/2012", "12:23");
    flight.setDest("pdx");
    flight.setArrive("12/23/2014","23:24");
    assertThat(flight.getArrivalString(), equalTo("12/23/2014 23:24"));
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
    flight.setSrc("Mum");
    assertThat(flight.getSource(), equalTo("Mum"));
  }

  @Test
  public void desttest() {
    Flight flight = new Flight();
    flight.setDest("pdx");
    assertThat(flight.getDestination(), equalTo("pdx"));
  }

  @Test
  public void departurestring() {
    Flight flight = new Flight();
    flight.setDepart("03/03/2017", "12:00");
    assertThat(flight.getDepartureString(), equalTo("03/03/2017 12:00"));
  }

  @Test
  public void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }
  
}
