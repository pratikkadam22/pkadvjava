package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The main class for the CS410J airline Project
 */
public class Project4 {

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

    boolean x = false, y = false;
    for(String ar:args){
      if(ar.equals("-textFile")){
        x = true;
      }
      if(ar.equals("-xmlFile")){
        y = true;
      }
    }
    if(x && y){
      System.err.println("The arguments contain both, -textFile and -xmlFile");
      System.exit(1);
    }

    //This is the case when -README is included
    if (args[0].equals("-README") || args[1].equals("-README") || args[2].equals("-README")
            || args[3].equals("-README") || args[4].equals("-README") || args[5].equals("-README")
            || args[6].equals("-README") || args[7].equals("-README") ) {
      readmeinfo();
    }

    //This is the case when there are less arguments
    if (args.length < 10) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    //This is the case when there are 11 arguments (-print)
    if (args.length == 11) {
      if (args[0].equals("-print")) {
        for (int i=1; i < args.length; i++) {
          if (args[i].equals("-print") || args[i].equals("-README") || args[i].equals("-textFile") || args[i].contains(".txt")) {
            System.err.println("Please check the arguments");
            System.exit(1);
          }
        }
        int k;
        String first;
        first = args[0];
        for(k = 0; k < args.length-1; k++){
          args[k] = args[k+1];
        }
        args[k] = first;
        Object[] objs = compute(args);
        System.out.println(objs[1].toString());
        System.exit(0);
      }
      else {
        System.err.println("Please check the arguments");
        System.exit(1);
      }
    }

    //This is the case when there are 10 arguments (no options)
    if (args.length == 10) {
      for (String arg : args) {
        if (arg.equals("-print") || arg.equals("-README") || arg.equals("-textFile")
                || arg.contains(".txt") || arg.contains("-pretty") || arg.contains("-")
                || arg.contains(".xml") || arg.equals("-xmlFile")) {
          System.err.println("Please check the arguments");
          System.exit(1);
        }
      }
      Object[] objs = compute(args);
      System.exit(0);
    }

    //This is the case when there are 12 arguments (-textFile and -File included or -pretty and - or -pretty and -file)
    if (args.length == 12) {
      if (args[0].equals("-textFile") && args[1].contains(".txt")) {
        for (int i=2; i < args.length; i++) {
          if (args[i].equals("-print") || args[i].equals("-README") || args[i].equals("-textFile")
                  || args[i].contains(".txt") || args[i].equals("-pretty") || args[i].contains("-") ||
                  args[i].equals("-xmlFile") || args[i].contains(".xml")) {
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
        dumper.setFilename(args[11]);
        parser.setnames(args[11], args[0]);
        AbstractAirline parsedairline = parser.parse();
        parsedairline.addFlight((AbstractFlight) objs[1]);
        dumper.dump(parsedairline);
        System.exit(0);
      }
      else if (args[0].equals("-pretty") && args[1].contains("-")) {
        for (int i=2; i < args.length; i++) {
          if (args[i].equals("-print") || args[i].equals("-README") || args[i].equals("-textFile")
                  || args[i].contains(".txt") || args[i].equals("-pretty") || args[i].contains("-") ||
                  args[i].equals("-xmlFile") || args[i].contains(".xml")) {
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
        Airline a = (Airline) objs[0];
        PrettyPrinter printer = new PrettyPrinter();
        System.out.println(printer.getpretty((AbstractFlight) objs[1], a.getName() ));
        System.exit(0);
      }
      else if(args[0].equals("-pretty") && args[1].contains(".txt")){
        for (int i=2; i < args.length; i++) {
          if (args[i].equals("-print") || args[i].equals("-README") || args[i].equals("-textFile")
                  || args[i].contains(".txt") || args[i].equals("-pretty") || args[i].contains("-") ||
                  args[i].equals("-xmlFile") || args[i].contains(".xml")) {
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
        PrettyPrinter printer = new PrettyPrinter();
        printer.setFilename(args[11]);
        printer.dump((AbstractAirline) objs[0]);
        System.exit(0);
      }
      else if(args[0].equals("-xmlFile") && args[1].contains(".xml")){
        for (int i=2; i < args.length; i++) {
          if (args[i].equals("-print") || args[i].equals("-README") || args[i].equals("-textFile")
                  || args[i].contains(".txt") || args[i].equals("-pretty") || args[i].contains("-") ||
                  args[i].equals("-xmlFile") || args[i].contains(".xml")) {
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
        XmlParser xmlparser = new XmlParser(args[11], args[0]);
        Airline parsedairline = xmlparser.parse();
        parsedairline.addFlight((AbstractFlight) objs[1]);
        XmlDumper xdumper = new XmlDumper(args[11]);
        xdumper.dump((parsedairline));
        System.exit(0);
      }
      else {
        System.err.println("Please check the arguments");
        System.exit(1);
      }
    }

    //This is the case when there are 13 arguments (-textfile -File and -print included)
    if(args.length == 13) {
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
        if(args[10].equals("-textFile")){
          dumper.setFilename(args[11]);
          parser.setnames(args[11], args[0]);
        }
        else {
          dumper.setFilename(args[12]);
          parser.setnames(args[12], args[0]);
        }
        AbstractAirline parsedairline = parser.parse();
        parsedairline.addFlight((AbstractFlight) objs[1]);
        dumper.dump(parsedairline);
        System.out.println(objs[1].toString());
        System.exit(0);
      }
      else if((args[0].equals("-xmlFile") && args[1].contains(".xml") && args[2].equals("-print")) || (args[0].equals("-print") && args[1].equals("-xmlFile") && args[2].contains(".xml"))){
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
        XmlParser xmlParser;
        XmlDumper xmlDumper;
        if(args[10].equals("-xmlFile")){
          xmlParser = new XmlParser(args[11], args[0]);
          xmlDumper = new XmlDumper(args[11]);
        }
        else {
          xmlParser = new XmlParser(args[12], args[0]);
          xmlDumper = new XmlDumper(args[12]);
        }
        Airline parsedairline = xmlParser.parse();
        parsedairline.addFlight((AbstractFlight) objs[1]);
        xmlDumper.dump((parsedairline));
        System.out.println(objs[1].toString());
        System.exit(0);
      }
      else {
        System.err.println("Please check the arguments");
        System.exit(1);
      }
    }

    // This is the case when there are 14 arguments
    if(args.length == 14){
      if((args[0].equals("-textFile") && args[1].contains(".txt") && args[2].equals("-pretty") && args[3].equals("-"))
              || (args[0].equals("-pretty") && args[1].equals("-") && args[2].equals("-textFile") && args[3].contains(".txt"))) {
        for (int i=4; i < args.length; i++) {
          if (args[i].equals("-print") || args[i].equals("-README") || args[i].equals("-textFile")
                  || args[i].contains(".txt") || args[i].equals("-pretty") || args[i].contains("-") ||
                  args[i].equals("-xmlFile") || args[i].contains(".xml")) {
            System.err.println("Please check the arguments");
            System.exit(1);
          }
        }
        int k;
        String first, second, third, fourth;
        first = args[0];
        second = args[1];
        third = args[2];
        fourth = args[3];
        for(k = 0; k < args.length-4; k++){
          args[k] = args[k+4];
        }
        args[k] = first;
        args[k+1] = second;
        args[k+2] = third;
        args[k+3] = fourth;
        Object[] objs = compute(args);
        TextParser parser = new TextParser();
        TextDumper dumper = new TextDumper();
        if(args[10].equals("-textFile")){
          dumper.setFilename(args[11]);
          parser.setnames(args[11], args[0]);
        }
        else {
          dumper.setFilename(args[13]);
          parser.setnames(args[13], args[0]);
        }
        AbstractAirline parsedairline = parser.parse();
        parsedairline.addFlight((AbstractFlight) objs[1]);
        dumper.dump(parsedairline);
        PrettyPrinter printer = new PrettyPrinter();
        for(Object f:parsedairline.getFlights()){
          System.out.println(printer.getpretty((AbstractFlight) f, parsedairline.getName() ));
        }
        System.exit(0);
      }
      else if((args[0].equals("-textFile") && args[1].contains(".txt") && args[2].equals("-pretty") && args[3].contains(".txt"))
                || (args[0].equals("-pretty") && args[1].contains(".txt") && args[2].equals("-textFile") && args[3].contains(".txt"))) {
          for (int i=4; i < args.length; i++) {
            if (args[i].equals("-print") || args[i].equals("-README") || args[i].equals("-textFile")
                    || args[i].contains(".txt") || args[i].equals("-pretty") || args[i].contains("-") ||
                    args[i].equals("-xmlFile") || args[i].contains(".xml")) {
              System.err.println("Please check the arguments");
              System.exit(1);
            }
          }
        int k;
        String first, second, third, fourth;
        first = args[0];
        second = args[1];
        third = args[2];
        fourth = args[3];
        for(k = 0; k < args.length-4; k++){
          args[k] = args[k+4];
        }
        args[k] = first;
        args[k+1] = second;
        args[k+2] = third;
        args[k+3] = fourth;
        Object[] objs = compute(args);
        TextParser parser = new TextParser();
        TextDumper dumper = new TextDumper();
        PrettyPrinter printer = new PrettyPrinter();
        if(args[10].equals("-textFile")){
          dumper.setFilename(args[11]);
          parser.setnames(args[11], args[0]);
          printer.setFilename(args[13]);
        }
        else {
          dumper.setFilename(args[13]);
          parser.setnames(args[13], args[0]);
          printer.setFilename(args[11]);
        }
        AbstractAirline parsedairline = parser.parse();
        parsedairline.addFlight((AbstractFlight) objs[1]);
        dumper.dump(parsedairline);
        printer.dump(parsedairline);
        System.exit(0);
      }
      else if((args[0].equals("-xmlFile") && args[1].contains(".xml") && args[2].equals("-pretty") && args[3].equals("-"))
              || (args[0].equals("-pretty") && args[1].equals("-") && args[2].equals("-xmlFile") && args[3].contains(".xml"))) {
        for (int i=4; i < args.length; i++) {
          if (args[i].equals("-print") || args[i].equals("-README") || args[i].equals("-textFile")
                  || args[i].contains(".txt") || args[i].equals("-pretty") || args[i].contains("-") ||
                  args[i].equals("-xmlFile") || args[i].contains(".xml")) {
            System.err.println("Please check the arguments");
            System.exit(1);
          }
        }
        int k;
        String first, second, third, fourth;
        first = args[0];
        second = args[1];
        third = args[2];
        fourth = args[3];
        for(k = 0; k < args.length-4; k++){
          args[k] = args[k+4];
        }
        args[k] = first;
        args[k+1] = second;
        args[k+2] = third;
        args[k+3] = fourth;
        Object[] objs = compute(args);
        XmlParser xmlParser;
        XmlDumper xmlDumper;
        if(args[10].equals("-xmlFile")){
          xmlParser = new XmlParser(args[11], args[0]);
          xmlDumper = new XmlDumper(args[11]);
        }
        else {
          xmlParser = new XmlParser(args[13], args[0]);
          xmlDumper = new XmlDumper(args[13]);
        }
        AbstractAirline parsedairline = xmlParser.parse();
        parsedairline.addFlight((AbstractFlight) objs[1]);
        xmlDumper.dump(parsedairline);
        PrettyPrinter printer = new PrettyPrinter();
        for(Object f:parsedairline.getFlights()){
          System.out.println(printer.getpretty((AbstractFlight) f, parsedairline.getName() ));
        }
        System.exit(0);
      }
      else if((args[0].equals("-xmlFile") && args[1].contains(".xml") && args[2].equals("-pretty") && args[3].contains(".txt"))
              || (args[0].equals("-pretty") && args[1].contains(".txt") && args[2].equals("-xmlFile") && args[3].contains(".xml"))) {
        for (int i=4; i < args.length; i++) {
          if (args[i].equals("-print") || args[i].equals("-README") || args[i].equals("-textFile")
                  || args[i].contains(".txt") || args[i].equals("-pretty") || args[i].contains("-") ||
                  args[i].equals("-xmlFile") || args[i].contains(".xml")) {
            System.err.println("Please check the arguments");
            System.exit(1);
          }
        }
        int k;
        String first, second, third, fourth;
        first = args[0];
        second = args[1];
        third = args[2];
        fourth = args[3];
        for(k = 0; k < args.length-4; k++){
          args[k] = args[k+4];
        }
        args[k] = first;
        args[k+1] = second;
        args[k+2] = third;
        args[k+3] = fourth;
        Object[] objs = compute(args);
        XmlParser xmlParser;
        XmlDumper xmlDumper;
        PrettyPrinter printer = new PrettyPrinter();
        if(args[10].equals("-xmlFile")){
          xmlParser = new XmlParser(args[11], args[0]);
          xmlDumper = new XmlDumper(args[11]);
          printer.setFilename(args[13]);
        }
        else {
          xmlParser = new XmlParser(args[13], args[0]);
          xmlDumper = new XmlDumper(args[13]);
          printer.setFilename(args[11]);
        }
        AbstractAirline parsedairline = xmlParser.parse();
        parsedairline.addFlight((AbstractFlight) objs[1]);
        xmlDumper.dump(parsedairline);
        printer.dump(parsedairline);
        System.exit(0);
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
    flight.setDepart(args[3], args[4], args[5]);
    flight.setDest(args[6]);
    flight.setArrive(args[7], args[8], args[9]);
    if(flight.checkdeparturebeforearrival()){
      airline.addFlight(flight);
    }
    else{
      System.err.println("The provided flight's arrival time is before its departure time");
      System.exit(1);
    }

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
    System.out.println("Project3 class - it consists of the main method that parses the command line, creates an Airline and a FLight as specified.\nIt adds the Flight to the Airline and optionally prints a description of the flight.");
    System.out.println("PrettyPrinter class - it prints the list of flights in an airline in a pretty way");
    System.exit(0);
  }
}