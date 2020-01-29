package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.ParserException;

import java.io.IOException;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  /**
   * This is the main method that parses the command line arguments and performs the required operations
   * @param args This is an array of strings of the command line
   */
  public static void main(String[] args) throws IOException, ParserException {
    //This is the case when there are no arguments
    if (args.length == 0) {
      System.err.println("No arguments!");
      System.exit(1);
    }

    //This is the case when -README is included
    if (args[0].equals("-README") || args[1].equals("-README") || args[2].equals("-README")) {
      readmeinfo();
    }

    //This is the case when there are less arguments
    if (args.length < 8) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    //This is the case when there are 9 arguments
    if (args.length == 9) {
      if (args[0].equals("-print")) {
        for (int i=1; i < args.length; i++) {
          if (args[i].equals("-print") || args[i].equals("-README") || args[i].equals("-textFile") || args[i].contains(".txt")) {
            System.err.println("Please check the arguments");
            System.exit(1);
          }
        }
        //for(int j = 0; j < 1; j++){
          int k;
          String first;
          first = args[0];
          for(k = 0; k < args.length-1; k++){
            args[k] = args[k+1];
          }
          args[k] = first;
        //}
        Object[] objs = compute(args);
        System.out.println(objs[1].toString());
        System.exit(0);
      }
      else {
        System.err.println("Please check the arguments");
        System.exit(1);
      }
    }

    //This is the case when there are 8 arguments
    if (args.length == 8) {
      for (String arg : args) {
        if (arg.equals("-print") || arg.equals("-README") || arg.equals("-textFile") || arg.contains(".txt")) {
          System.err.println("Please check the arguments");
          System.exit(1);
        }
      }
      Object[] objs = compute(args);
      System.exit(0);
    }

    //This is the case when there are 10 arguments
    if (args.length == 10) {
      if (args[0].equals("-textFile") && args[1].contains(".txt")) {
        for (int i=2; i < args.length; i++) {
          if (args[i].equals("-print") || args[i].equals("-README") || args[i].equals("-textFile") || args[i].contains(".txt")) {
            System.err.println("Please check the arguments");
            System.exit(1);
          }
        }
        int k;
        String first, second;
        first = args[0];
        second = args[1];
        for(k = 0; k < args.length-2; k++){
          args[k] = args[k+2];
        }
        args[k] = first;
        args[k+1] = second;
        Object[] objs = compute(args);
        TextParser parser = new TextParser();
        TextDumper dumper = new TextDumper();
        dumper.setFilename(args[9]);
        parser.setnames(args[9], args[0]);
        AbstractAirline parsedairline = parser.parse();
        parsedairline.addFlight((AbstractFlight) objs[1]);
        System.out.println(parsedairline.getName());
        dumper.dump(parsedairline);
        System.out.println(objs[1].toString());
        System.exit(0);
      }
      else {
        System.err.println("Please check the arguments");
        System.exit(1);
      }
    }

    //This is the case when there are 11 arguments
    if(args.length == 11) {
      if((args[0].equals("-textFile") && args[1].contains(".txt") && args[2].equals("-print")) || (args[0].equals("-print") && args[1].equals("-textFile") && args[2].contains(".txt"))) {
        int k;
        String first, second, third;
        first = args[0];
        second = args[1];
        third = args[2];
        for(k = 0; k < args.length-3; k++){
          args[k] = args[k+3];
        }
        args[k] = first;
        args[k+1] = second;
        args[k+2] = third;
        Object[] objs = compute(args);
        TextParser parser = new TextParser();
        TextDumper dumper = new TextDumper();
        if(args[8].equals("-textFile")){
          dumper.setFilename(args[9]);
          parser.setnames(args[9], args[0]);
        }
        else {
          dumper.setFilename(args[10]);
          parser.setnames(args[10], args[0]);
        }
        AbstractAirline parsedairline = parser.parse();
        parsedairline.addFlight((AbstractFlight) objs[1]);
        System.out.println(parsedairline.getName());
        dumper.dump(parsedairline);
        System.out.println(objs[1].toString());
        System.exit(0);
      }
      else {
        System.err.println("Please check the arguments");
        System.exit(1);
      }
    }

    //This is the case when there are more number of args than required
    System.err.println("Too many arguments!");
    System.exit(1);
  }

  /**
   * This method creates an Airline and a Flight and adds the Flight to the Airline
   * @param args These are the command line arguments in the form of an array of strings
   * @return The Flight object that was added to the Airline
   */
  public static Object[] compute(String[] args) {
    Flight flight = new Flight();
    Airline airline = new Airline();
    airline.setName(args[0]);
    flight.setFlightnum(args[1]);
    flight.setSrc(args[2]);
    flight.setDepart(args[3], args[4]);
    flight.setDest(args[5]);
    flight.setArrive(args[6], args[7]);
    airline.addFlight(flight);

    //return flight;
    Object[] objs = new Object[2];
    objs[0] = airline;
    objs[1] = flight;
    return objs;
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