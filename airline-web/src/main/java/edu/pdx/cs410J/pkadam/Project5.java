package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.awt.desktop.ScreenSleepEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    /**
     * This is the main method that parses the command line arguments and performs the required operations
     * @param args This is an array of strings of the command line
     */
    public static void main(String... args) throws Exception {

        //This is the case when there are no arguments
        if (args.length == 0) {
            System.err.println("No arguments!");
            System.exit(1);
        }

        //This is the case when -README is included
        for (String arg : args) {
            if (arg.equals("-README")) {
                readmeinfo();
            }
        }

        // This is the case when there are 14 arguments
        if(args.length == 14){
            if((args[0].equals("-host") && args[2].equals("-port"))
                    || (args[0].equals("-port") && args[2].equals("-host"))) {
                for (int i=4; i < args.length; i++) {
                    if (args[i].equals("-host") || args[i].equals("-search") ||
                            args[i].equals("-print") || args[5].equals("-port")) {
                        System.err.println("Please check the arguments");
                        System.exit(1);
                    }
                }
                String hostName = "";
                String portString = "";
                for (int i = 0; i<args.length; i++){
                    if(args[i].equals("-host")){
                        hostName = args[i+1];
                    }
                    if(args[i].equals("-port")){
                        portString = args[i+1];
                    }
                }
                checkhostandport(hostName,portString);
                int portnum = Integer.parseInt(portString);
                AirlineRestClient client = new AirlineRestClient(hostName, portnum);
                String[] flightargs = {args[4], args[5], args[6], args[7], args[8], args[9], args[10],
                        args[11], args[12], args[13]};
                Flight flight = compute(flightargs);
                client.addFlight(args[4], flight);
                System.out.println("Flight added to the airline.");
                System.exit(0);
            }
        }

        // This is the case when there are 14 arguments
        if(args.length == 15){
            if((args[0].equals("-host") && args[2].equals("-port") && args[4].equals("-print"))
                    || (args[0].equals("-port") && args[2].equals("-host") && args[4].equals("-print"))
                    || (args[0].equals("-print") && args[1].equals("-host") && args[3].equals("-port"))
                    || (args[0].equals("-print") && args[1].equals("-port") && args[3].equals("-host"))
                    || (args[0].equals("-host") && args[2].equals("-print") && args[3].equals("-port"))
                    || (args[0].equals("-port") && args[2].equals("-print") && args[3].equals("-host"))) {
                for (int i=5; i < args.length; i++) {
                    if (args[i].equals("-host") || args[i].equals("-search") ||
                            args[i].equals("-print") || args[5].equals("-port")) {
                        System.err.println("Please check the arguments");
                        System.exit(1);
                    }
                }
                String hostName = "";
                String portString = "";
                for (int i = 0; i<args.length; i++){
                    if(args[i].equals("-host")){
                        hostName = args[i+1];
                    }
                    if(args[i].equals("-port")){
                        portString = args[i+1];
                    }
                }
                checkhostandport(hostName,portString);
                int portnum = Integer.parseInt(portString);
                AirlineRestClient client = new AirlineRestClient(hostName, portnum);
                String[] flightargs = {args[5], args[6], args[7], args[8], args[9], args[10], args[11],
                        args[12], args[13], args[14]};
                Flight flight = compute(flightargs);
                client.addFlight(args[5], flight);
                System.out.println("Following flight added to the "+args[5]+" airline.\n" + flight.toString());
                System.exit(0);
            }
        }

        // This is the case when there are 8 arguments
        if(args.length == 8){
            if((args[0].equals("-host") && args[2].equals("-port") && args[4].equals("-search"))
                    || (args[0].equals("-port") && args[2].equals("-host") && args[4].equals("-search"))
                    || (args[0].equals("-search") && args[1].equals("-host") && args[3].equals("-port"))
                    || (args[0].equals("-search") && args[1].equals("-port") && args[3].equals("-host"))
                    || (args[0].equals("-host") && args[2].equals("-search") && args[3].equals("-port"))
                    || (args[0].equals("-port") && args[2].equals("-search") && args[3].equals("-host"))) {
                for (int i=5; i < args.length; i++) {
                    if (args[i].equals("-host") || args[i].equals("-search") ||
                            args[i].equals("-print") || args[5].equals("-port")) {
                        System.err.println("Please check the arguments");
                        System.exit(1);
                    }
                }
                String hostName = "";
                String portString = "";
                for (int i = 0; i<args.length; i++){
                    if(args[i].equals("-host")){
                        hostName = args[i+1];
                    }
                    if(args[i].equals("-port")){
                        portString = args[i+1];
                    }
                }
                checkhostandport(hostName, portString);
                int portnum = Integer.parseInt(portString);
                AirlineRestClient client = new AirlineRestClient(hostName, portnum);
                String prettyAirline = client.getFlightsBetween(args[5], args[6], args[7]);
                System.out.println(prettyAirline);
                System.exit(0);
            }
        }

        // This is the case when there are 6 arguments
        if(args.length == 6){
            if((args[0].equals("-host") && args[2].equals("-port") && args[4].equals("-search"))
                    || (args[0].equals("-port") && args[2].equals("-host") && args[4].equals("-search"))
                    || (args[0].equals("-search") && args[1].equals("-host") && args[3].equals("-port"))
                    || (args[0].equals("-search") && args[1].equals("-port") && args[3].equals("-host"))
                    || (args[0].equals("-host") && args[2].equals("-search") && args[3].equals("-port"))
                    || (args[0].equals("-port") && args[2].equals("-search") && args[3].equals("-host"))) {

                    if (args[5].equals("-host") || args[5].equals("-search")
                            || args[5].equals("-print") || args[5].equals("-port")) {
                        System.err.println("Please check the arguments");
                        System.exit(1);
                    }
                String hostName = "";
                String portString = "";
                for (int i = 0; i<args.length; i++){
                    if(args[i].equals("-host")){
                        hostName = args[i+1];
                    }
                    if(args[i].equals("-port")){
                        portString = args[i+1];
                    }
                }
                checkhostandport(hostName, portString);
                int portnum = Integer.parseInt(portString);
                AirlineRestClient client = new AirlineRestClient(hostName, portnum);
                String prettyAirline = client.getAllFlights(args[5]);
                System.out.println(prettyAirline);
                System.exit(0);
            }
        }
        //This is the case when there are more number of args than required
        System.err.println("Please check the command line arguments");
        System.exit(1);
    }
//-host localhost -port 3232 Project4 1234 CVG 03/03/2017 12:00 pm DRO 05/04/2017 2:00 pm
    public static void checkhostandport(String hostname, String portstring) {
        if (hostname == null) {
            System.err.println("Missing hostname!");
            System.exit(1);

        } else if ( portstring == null) {
            System.err.println("Missing port number!");
            System.exit(1);
        }
        int portnum;
        try {
            portnum = Integer.parseInt( portstring );

        } catch (NumberFormatException ex) {
            System.err.println("Port Number must be an integer!");
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
        flight.setFlightnum(args[1]);
        flight.setSrc(args[2]);
        flight.setDepart(args[3], args[4], args[5]);
        flight.setDest(args[6]);
        flight.setArrive(args[7], args[8], args[9]);
        if(!flight.checkdeparturebeforearrival()){
            System.err.println("The provided flight's arrival time is before its departure time");
            System.exit(1);
        }
        return flight;
    }

    /**
     * This method prints out the README information for this project and exits
     */
    public static void readmeinfo(){
        System.out.println("README for Project 5\nName: Pratik Kadam");
        System.out.println("Project 5 : A REST-ful Airline Web Service");
        System.out.println("This project will extend my airline application to support an airline server that provides REST-ful web services to an airline client.");
        System.out.println("The Airline class extends AbstractAirline and the Flight class extends AbstractFlight");
        System.out.println("Airline class - it has a name and consists of multiple flights.");
        System.out.println("Flight class - it consists of details like flightnumber, source, departure time, destination, arrival time.");
        System.out.println("Project3 class - it consists of the main method that parses the command line, creates an Airline and a FLight as specified.\nIt adds the Flight to the Airline and optionally prints a description of the flight.");
        System.out.println("PrettyPrinter class - it prints the list of flights in an airline in a pretty way");
        System.out.println("XmlDumper class - it converts the airline object into XML format");
        System.out.println("XmlParser class - it converts the airline in XML format into an airline object");
        System.out.println("AirlineRestClient class - it is the REST client via which our program interact with the server");
        System.out.println("AirlineServlet class - it provides REST access to the airline");
        System.exit(0);
    }
}