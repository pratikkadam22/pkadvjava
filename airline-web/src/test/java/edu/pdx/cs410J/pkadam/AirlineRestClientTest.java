package edu.pdx.cs410J.pkadam;

import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class AirlineRestClientTest {
    @Test(expected = AirlineRestClient.AirlineRestException.class)
    public void addFlight() throws Exception {
        Airline airline = new Airline();
        airline.setName("emirates");
        Flight flight = new Flight();
        flight.setFlightnum("123");
        flight.setSrc("PDX");
        flight.setDest("ORD");
        flight.setDepart("01/07/2019", "07:00","am");
        flight.setArrive("01/07/2019", "09:00","am");
        airline.addFlight(flight);

        AirlineRestClient client = new AirlineRestClient("localhost", 8080);
        client.addFlight(airline.getName(), flight);
        client.getAllFlights(airline.getName());
    }

    @Test(expected = AirlineRestClient.AirlineRestException.class)
    public void getFlights() throws Exception {
        Airline airline = new Airline();
        airline.setName("emirates");
        Flight flight = new Flight();
        flight.setFlightnum("123");
        flight.setSrc("PDX");
        flight.setDest("ORD");
        flight.setDepart("01/07/2019", "07:00","am");
        flight.setArrive("01/07/2019", "09:00","am");
        airline.addFlight(flight);

        AirlineRestClient client = new AirlineRestClient("localhost", 8080);
//        client.addFlight(airline.getName(), flight);
        client.getFlightsBetween(airline.getName(), flight.getSource(), flight.getDestination());
    }

    @Test(expected = AirlineRestClient.AirlineRestException.class)
    public void getAirline() throws Exception {
        Airline airline = new Airline();
        airline.setName("emirates");
        Flight flight = new Flight();
        flight.setFlightnum("123");
        flight.setSrc("PDX");
        flight.setDest("ORD");
        flight.setDepart("01/07/2019", "07:00","am");
        flight.setArrive("01/07/2019", "09:00","am");
        airline.addFlight(flight);

        AirlineRestClient client = new AirlineRestClient("localhost", 8080);
//        client.addFlight(airline.getName(), flight);
        client.getAllFlights(airline.getName());
    }
}
