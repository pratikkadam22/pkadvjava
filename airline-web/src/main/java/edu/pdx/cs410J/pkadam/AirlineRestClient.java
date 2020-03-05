package edu.pdx.cs410J.pkadam;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final String url;
    String finalpretty = "";


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

  /**
   * This method sends a POST request to the server to add the flight in the airline on server
   * @param airline_name the name of airline in which flight is to be added
   * @param flight the object of the flight to be added
   */
  public void addFlight(String airline_name, Flight flight) throws IOException {
    Response response = postToMyURL(Map.of(
            "airline", airline_name,
            "flightnum", String.valueOf(flight.getNumber()),
            "src", flight.getSource(),
            "depart", flight.getDepartureString(),
            "dest", flight.getDestination(),
            "arrv", flight.getArrivalString()));
    throwExceptionIfNotOkayHttpStatus(response);
  }

    /**
     * This method retrive the flights between given source and destination
     * @param airlineName the name of airline in which we want to search for flights
     * @param source the source of the required flight
     * @param destination the destination of the required flight
     * @return the flights between given source and destination in the form of a string
     */
  public String getFlightsBetween(String airlineName, String source, String destination) throws Exception {
    Response response = getFromMyURL(Map.of("name", airlineName, "src", source, "dest", destination));
    if(response.getContent().contains("does not exist")){
      System.err.println(response.getContent());
      System.exit(1);
    }
    throwExceptionIfNotOkayHttpStatus(response);
    XmlParser parser = new XmlParser(loadXMLFromString(response.getContent()), airlineName);
    Airline parsedairline = parser.parse();
    if (parsedairline.getFlights().size() > 0){
      PrettyPrinter pretty = new PrettyPrinter();
      ArrayList<Flight> arrli = parsedairline.getFlights();
      Collections.sort(arrli);
      finalpretty = "";
      for (Flight flight : arrli) {
        finalpretty = finalpretty + pretty.getpretty(flight, parsedairline.getName());
      }
      return finalpretty;
    }
    return "There is no flight from " + source + " to " + destination + " for " + airlineName + " airline";
  }

  /**
   * This method retrives all the flights that belong to the given airline
   * @param airlineName the name of airline of which we want to retrieve the flights
   * @return The flights of the given airline in the form of a string
   */
  public String getAllFlights(String airlineName) throws Exception {
    Response response = getFromMyURL(Map.of("name", airlineName));
    throwExceptionIfNotOkayHttpStatus(response);
    XmlParser parser = new XmlParser(loadXMLFromString(response.getContent()), airlineName);
    Airline parsedairline = parser.parse();
    if (parsedairline.getFlights().size() > 0){
      PrettyPrinter pretty = new PrettyPrinter();
      ArrayList<Flight> arrli = parsedairline.getFlights();
      Collections.sort(arrli);
      finalpretty = "";
      for (Flight flight : arrli) {
        finalpretty = finalpretty + pretty.getpretty(flight, parsedairline.getName());
      }
      return finalpretty;
    }
    return "The airline " + airlineName + " does not exist";
  }

  /**
   * This method builds a Document object from the given XML string
   * @param xml the XML format of airline in the form of a string
   * @return The Document object of the airline
   */
  public static Document loadXMLFromString(String xml) throws Exception
  {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    InputSource is = new InputSource(new StringReader(xml));
    return builder.parse(is);
  }

  /**
   * This method issues a GET request to the servlet
   * @param dictionaryEntries The arguments for the get request
   * @return The response from the servlet
   */
  Response getFromMyURL(Map<String, String> dictionaryEntries) throws IOException {
    return get(this.url, dictionaryEntries);
  }

  /**
   * This method is used to issue a POST request to the servlet
   * @param dictionaryEntries the arguments for the POST request
   * @return The response from the servlet
   */
  @VisibleForTesting
  Response postToMyURL(Map<String, String> dictionaryEntries) {
    try{
      return post(this.url, dictionaryEntries);
    }
    catch (Exception e){
      System.err.println("Please check the connection parameters for the client");
      System.exit(1);
    }
    return null;
  }

  /**
   * This method handles the response if there is an HTTP error
   * @param response the HTTP error response
   * @return The Response object of the error response
   */
  private Response throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code != HTTP_OK) {
      throw new AirlineRestException(code);
    }
    return response;
  }

  /**
   * This method handles the runtime exception for the REST client
   */
  @VisibleForTesting
  static
  class AirlineRestException extends RuntimeException {
    AirlineRestException(int httpStatusCode) {
      super("Got an HTTP Status Code of " + httpStatusCode);
    }
  }
}
