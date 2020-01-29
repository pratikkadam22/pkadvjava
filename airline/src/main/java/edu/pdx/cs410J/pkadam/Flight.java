package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractFlight;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class extends the AbstractFlight superclass and implement all its methods
 */
public class Flight extends AbstractFlight {
    int flightnum;
    String src, dst, dprt, arrv;

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
      String codex = "[a-zA-Z]{3}";
      Pattern pattern = Pattern.compile(codex);
      Matcher matcher = pattern.matcher(source);
      if (matcher.matches()) {
          this.src = source;
      }
      else {
          System.err.println("Please check the code for the Source!");
          System.exit(1);
      }
  }

    /**
     * This method is used to verify whether the date and time are in the correct format.
     * @param date the arrival or departure date of the flight in the form of a string
     * @param time the arrival or departure time of the flight in the form of a string
     * @return This returns boolean True if date and time are correct and returns boolean False if format is wrong
     */
  public boolean datetimevalidator(String date, String time) {
      String dateregex = "^(1[0-2]|0[1-9]|[1-9])/(3[01]|[12][0-9]|0[1-9]|[1-9])/[0-9]{4}$";
      Pattern pattern1 = Pattern.compile(dateregex);
      Matcher matcher1 = pattern1.matcher(date);
      String timeregex = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
      Pattern pattern2 = Pattern.compile(timeregex);
      Matcher matcher2 = pattern2.matcher(time);
      return (matcher1.matches() && matcher2.matches());
  }

    /**
     * This method sets the departure date and time of a flight from the source
     * @param date The date when the flight departs
     * @param time The date when the flight arrives
     */
  public void setDepart(String date, String time) {
      boolean bool = datetimevalidator(date, time);
      if(bool) {
        String depart = date + " " + time;
        this.dprt = depart;
      } else {
          System.err.println("Please verify the format for departing date and time: MM/DD/YYY HH:MM");
        System.exit(1);
      }
  }

    /**
     * This method sets the destination of the flight
     * @param dest The three letter code of the destination of the flight
     */
  public void setDest(String dest) {
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
     */
  public void setArrive(String date, String time) {
      boolean bool = datetimevalidator(date, time);
      if(bool) {
          String arrival = date + " " + time;
          this.arrv = arrival;
      } else {
          System.err.println("Please verify the format for arrival date and time: MM/DD/YYY HH:MM");
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
    return this.dprt;
    //throw new UnsupportedOperationException("This method is not implemented yet");
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
    return this.arrv;
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }
}
