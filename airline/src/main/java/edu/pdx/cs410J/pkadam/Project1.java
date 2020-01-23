package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  /**
   * This is the main method that parses the command line arguments and performs the required operations
   * @param args This is an array of strings of the command line
   */
  public static void main(String[] args) {
    //This is the case when there are no arguments
    if (args.length == 0) {
      System.err.println("No arguments!");
      System.exit(1);
    }

    //This is the case when -README is included
    if (args[0].equals("-README") || args[1].equals("-README")) {
      readmeinfo();
    }

    //This is the case when there are less arguments
    if (args.length < 8) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    //This is the case when -print maybe included
    if (args.length == 9) {
      if (args[0].equals("-print")) {
        for (int i=1; i < args.length; i++) {
          if (args[i].equals("-print") || args[i].equals("-README")) {
            System.err.println("Please check the arguments");
            System.exit(1);
          }
        }
        for(int j = 0; j < 1; j++){
          int k;
          String first;
          first = args[0];
          for(k = 0; k < args.length-1; k++){
            args[k] = args[k+1];
          }
          args[k] = first;
        }
        Flight flight = compute(args);
        System.out.println(flight.toString());
        System.exit(0);
      }
      else {
        System.err.println("Please check the arguments");
        System.exit(1);
      }
    }

    //This is the case when there are 8 arguments
    if (args.length == 8) {
      for (int i=0; i < args.length; i++) {
        if (args[i].equals("-print") || args[i].equals("-README")) {
          System.err.println("Please check the arguments");
          System.exit(1);
        }
      }
      Flight flight = compute(args);
      System.exit(0);
    }

    //This is the case when there are more number of args than required
    if (args.length > 9) {
      System.err.println("Too many arguments!");
      System.exit(1);
    }
  }

  /**
   * This method creates an Airline and a Flight and adds the Flight to the Airline
   * @param args These are the command line arguments in the form of an array of strings
   * @return The Flight object that was added to the Airline
   */
  public static Flight compute(String[] args) {
    Flight flight = new Flight();
    Airline airline = new Airline();
    airline.setName(args[0]);
    flight.setFlightnum(args[1]);
    flight.setSrc(args[2]);
    flight.setDepart(args[3], args[4]);
    flight.setDest(args[5]);
    flight.setArrive(args[6], args[7]);
    airline.addFlight(flight);
    return flight;
  }

  /**
   * This method prints out the README information for this project and exits
   */
  public static void readmeinfo(){
    System.out.println("README for Project 1\nName: Pratik Kadam");
    System.out.println("Project 1 : Designing an Airline Application");
    System.out.println("The goal of this project was to extend classes that I did not write and perform more complex command line parsing.");
    System.out.println("The Airline class extends AbstractAirline and the Flight class extends AbstractFlight");
    System.out.println("Airline class - it has a name and consists of multiple flights.");
    System.out.println("Flight class - it consists of details like flightnumber, source, departure time, destination, arrival time.");
    System.out.println("Project1 class - it consists of the main method that parses the command line, creates an Airline and a FLight as specified.\nIt adds the Flight to the Airline and optionally prints a description of the flight.");
    System.exit(0);
  }
}