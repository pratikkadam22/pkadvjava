package edu.pdx.cs410J.pkadam;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AirportNames;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.
 */
public class AirlineServlet extends HttpServlet {
   private ArrayList<Airline> airlinelist = new ArrayList<Airline>();
    Map names = AirportNames.getNamesMap();

    /**
   * Handles an HTTP GET request from a client by writing the airlines from
     * specified source to destination in XML format. If just the
   * "name" parameter is specified, all of the flights in the airline
   * are written to the HTTP response.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter( "airline", request );
      if (airlineName == null) {
          missingRequiredParameter(response, "airline");
          return;
      }

      String source = getParameter( "src", request );
      if (source != null) {
          String sourceuppercase = source.toUpperCase();
          if(!names.containsKey(sourceuppercase)){
              response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The three-letter code for the source is invalid");
          }
      }

      String destination = getParameter( "dest", request );
      if (destination != null){
          String destuppercase = destination.toUpperCase();
          if(!names.containsKey(destuppercase)){
              response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The three-letter code for the destination is invalid");
          }
      }

      Airline getairline = null;
      for (Airline airline : airlinelist) {
          if (airline.getName().equals(airlineName)) {
              getairline = airline;
              break;
          }
      }
      if(getairline == null){
          noairlinefound(airlineName, response);
      } else {
          StringWriter stringWriter = new StringWriter();
          stringWriter.flush();
          XmlDumper dumper = null;
          try {
              dumper = new XmlDumper(stringWriter);
          } catch (TransformerConfigurationException e) {
              e.printStackTrace();
          }
          ArrayList<Flight> arrli = getairline.getFlights();
          if (source == null && destination == null) {
              dumper.dump(getairline);
          } else {
              Airline filteredairline = new Airline();
              filteredairline.setName(airlineName);
              for (Flight flight : arrli) {
                  if (flight.getSource().equals(source) && flight.getDestination().equals(destination)) {
                      filteredairline.addFlight(flight);
                  }
              }
              dumper.dump(filteredairline);
          }
          response.getWriter().println(dumper.generateAsString());
          response.setStatus(HttpServletResponse.SC_OK);
      }
  }

  /**
   * Handles an HTTP POST request by storing the flight entry for the airline.
   */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
  {
      response.setContentType( "text/plain" );

      String aname = getParameter("airline", request );
      if (aname == null) {
          missingRequiredParameter(response, "airline");
          return;
      }
      String flightnumasstring = getParameter("flightnum", request );
      if ( flightnumasstring == null) {
          missingRequiredParameter( response, "flightnum" );
          return;
      }
      String src = getParameter("src", request );
      if ( src == null) {
          missingRequiredParameter( response, "src" );
          return;
      }
      String departasstring = getParameter("depart", request );
      if ( departasstring == null) {
          missingRequiredParameter(response, "depart");
          return;
      }
      String[] departwords = departasstring.split(" ");

      String dest = getParameter("dest", request );
      if ( dest == null) {
          missingRequiredParameter( response, "dest" );
          return;
      }
      String arrvasstring = getParameter("arrv", request );
      if ( arrvasstring == null) {
          missingRequiredParameter( response, "arrv" );
          return;
      }
      String[] arrvwords = arrvasstring.split(" ");

      Airline airline = createOrValidateAirlineForPost(aname);
      Flight flight = new Flight();
      flight.setFlightnum(flightnumasstring);
      flight.setSrc(src);
      flight.setDepart(departwords[0], departwords[1], departwords[2]);
      flight.setDest(dest);
      flight.setArrive(arrvwords[0], arrvwords[1], arrvwords[2]);
      airline.addFlight(flight);
      airlinelist.add(airline);
      response.setStatus( HttpServletResponse.SC_OK);
  }

    /**
     * this method checks whether the airline is already present in the list of airlines or not.
     * @param airlineName the name of airline whose existence is to be checked
     * @return The Airline object of the validated airline
     */
    private Airline createOrValidateAirlineForPost(String airlineName) {
      for(int i=0; i<airlinelist.size(); i++){
          if(airlinelist.get(i).getName().equals(airlineName)){
              Airline ar = airlinelist.get(i);
              airlinelist.remove(i);
              return ar;
          }
      }
      Airline airline = new Airline();
      airline.setName(airlineName);
      return airline;
    }

    /**
     * The error message to be displayed in case the airline does not exist.
     * @param airlineName the name of the airline that does not exist
     * @param response the error message saying that the airline does not exist
     */
    private void noairlinefound(String airlineName, HttpServletResponse response) throws IOException {
        String message = "Airline named as " + airlineName + " does not exist.";
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

//  /**
//   * Handles an HTTP DELETE request by removing all dictionary entries.  This
//   * behavior is exposed for testing purposes only.  It's probably not
//   * something that you'd want a real application to expose.
//   */
//  @Override
//  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//      response.setContentType("text/plain");
//
//      this.airline = null;
//
//      response.setStatus(HttpServletResponse.SC_OK);
//
//  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
      String message = "The required parameter " + parameterName + " is missing!";
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }

  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   *         <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }
}

