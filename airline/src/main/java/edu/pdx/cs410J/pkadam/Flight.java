package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class extends the AbstractFlight superclass and implement all its methods
 */
public class Flight extends AbstractFlight implements Comparable<Flight>{
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Map names = AirportNames.getNamesMap();
    int flightnum;
    String src, dst;
    Date dprt, arrv;

    /**
     * This method sets the flightnumber for the Flight
     * @param number the number for the flight parsed from command line
     */
  public void setFlightnum(String number) {
      int num = 0;
      try {
          num = Integer.parseInt(number);
      }
      catch(NumberFormatException e) {
          System.err.println("Please enter a numeric flightnumber!");
          System.exit(1);
      }
      String numeric = "[0-9]+";
      Pattern pattern = Pattern.compile(numeric);
      Matcher matcher = pattern.matcher(Integer.toString(num));
      if (matcher.matches()) {
          this.flightnum = num;
      }
      else {
          System.out.println("Please check the flightnumber!");
          System.exit(1);
      }
  }

    /**
     * This method sets the source of the flight
     * @param source the three letter code of the source of flight
     */
  public void setSrc(String source) {
      String sourceuppercase = source.toUpperCase();
      if(!names.containsKey(sourceuppercase)){
          System.err.println("The three-letter code for the source is invalid");
          System.exit(1);
      }
      this.src = source;
  }

    /**
     * This method sets the departure date and time of a flight from the source
     * @param date The date when the flight departs
     * @param time The date when the flight arrives
     * @param ampm The time of the day (am or pm)
     */
  public void setDepart(String date, String time, String ampm) {
      String finaldatetime = date + " " + time + " " + ampm;
      try{
          this.dprt = formatter.parse(finaldatetime);
      }
      catch (ParseException e){
          System.err.println("Please verify the format for departing date and time");
          System.exit(1);
      }
  }

    /**
     * This method sets the destination of the flight
     * @param dest The three letter code of the destination of the flight
     */
  public void setDest(String dest) {
      String destuppercase = dest.toUpperCase();
      if(!names.containsKey(destuppercase)){
          System.err.println("The three-letter code for the source is invalid");
          System.exit(1);
      }
      String codex = "[a-zA-Z]{3}";
      Pattern pattern = Pattern.compile(codex);
      Matcher matcher = pattern.matcher(dest);
      if (matcher.matches()) {
          this.dst = dest;
      }
      else {
          System.err.println("Please check the code for the destination!");
          System.exit(1);
      }
  }

    /**
     * This method sets the arrival date and time of a flight at the destination
     * @param date The date when the flight arrives
     * @param time The time when the flight arrives
     * @param ampm The time of the day (am or pm)
     */
  public void setArrive(String date, String time, String ampm) {
      String finaldatetime = date + " " + time + " " + ampm;
      try{
          this.arrv = formatter.parse(finaldatetime);
      }
      catch (ParseException e){
          System.err.println("Please verify the format for arriving date and time");
          System.exit(1);
      }
  }

    /**
     * This method ensures that the departure time is before arrival time
     */
  public void checkdeparturebeforearrival(){
      if(this.dprt.compareTo(this.arrv) > 0){
          System.err.println("The flight's arrival time is before its departure time.");
          System.exit(1);
      }
  }

    /**
     * This method is used to retrieve the flight number
     * @return the flight number which is an integer
     */
  @Override
  public int getNumber() {
      return this.flightnum;
  }

    /**
     * This method is used to retrieve the source of the flight
     * @return This returns the three letter code of source of the flight
     */
  @Override
  public String getSource() {
    return this.src;
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

    /**
     * This method is used to retrieve the departure date and time
     * @return This returns the departing date and time in the form of a string
     */
  @Override
  public String getDepartureString() {
      SimpleDateFormat dater = new SimpleDateFormat("MM/dd/yyyy");
      String datestring = dater.format(this.dprt);
      String timestring = formatter.getTimeInstance(DateFormat.SHORT).format(this.dprt);
      return datestring + " " + timestring;
  }

    /**
     * This method is used to return the destination of the flight
     * @return This returns the three letter code of destination of the flight
     */
  @Override
  public String getDestination() {
    return this.dst;
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

    /**
     * This method is used to retrieve the arrival date and time
     * @return This returns the arrival date and time in the form of a string
     */
  @Override
  public String getArrivalString() {
      SimpleDateFormat dater = new SimpleDateFormat("MM/dd/yyyy");
      String datestring = dater.format(this.arrv);
      String timestring = formatter.getTimeInstance(DateFormat.SHORT).format(this.arrv);
      return datestring + " " + timestring;
  }

    /**
     * This method return the arrival date and time of a flight
     * @return the arrival date in the form of a Date object
     */
    @Override
    public Date getArrival() {
        return this.arrv;
    }

    /**
     * This method return the departure date and time of a flight
     * @return the departure date in the form of a Date object
     */
    @Override
    public Date getDeparture() {
        return this.dprt;
    }

    /**
     * This method compares the current object to the specified object.
     * This method is used mainly to sort the flights by their source. If the source is same, it sorts by departure time.
     * @param o the Flight object to compare the current object to
     * @return it returns a positive integer if current object is greater than specified object,
                it returns a negative integer if current object is lesser than specified object,
                it returns 0 integer if current object is equal to specified object
     */
    public int compareTo(Flight o) {
      if(this.getSource().compareToIgnoreCase(o.getSource()) == 0){
          try {
              Date d1 = formatter.parse(this.getDepartureString());
              Date d2 = formatter.parse(o.getDepartureString());
              return d1.compareTo(d2);
          } catch (ParseException e) {
              e.printStackTrace();
              System.exit(1);
          }
      }
        return this.getSource().compareToIgnoreCase(o.getSource());
    }
}
