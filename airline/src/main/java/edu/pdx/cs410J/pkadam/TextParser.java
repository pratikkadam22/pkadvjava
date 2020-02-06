package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the class that implements the AirlineParser interface
 */
public class TextParser implements AirlineParser {
    String filename, airlinename;

    /**
     * This method sets the class variables to be used in the parse() method
     * @param fname the name of the text file to parse
     * @param aname the name of the airline passed in the command line
     */
    public void setnames(String fname, String aname) {
        this.filename = fname;
        this.airlinename = aname;
    }

    /**
     * This method parses the text file and creates the airline with its associated flights
     * @return the Airline object created from the details in the text file
     */
    @Override
    public AbstractAirline parse() throws ParserException {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(this.filename));
        } catch (FileNotFoundException e) {
            System.out.println("Text Dump File with given name does not exist. File created.");
            if(!this.filename.contains("/")){
                PrintWriter out = null;
                try {
                    out = new PrintWriter(this.filename);
                } catch (FileNotFoundException ex) {
                    System.out.println("File is not present.");
                }
                out.write(this.airlinename);
                out.close();
            }
            else {
                PrintWriter out = null;
                File f = null;
                File f1 = null;
                String v;
                boolean bool = false;
                f = new File(this.filename);
                f1 = f.getParentFile();
                v = f1.getAbsolutePath();
                bool = f1.exists();
                //check if directory exists or not
                if (bool) {
                    try {
                        out = new PrintWriter(this.filename);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    out.write(this.airlinename);
                    out.close();
                } else {
                    File folder = new File(v);
                    if (folder.mkdir()) {
                        try {
                            out = new PrintWriter(this.filename);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        out.write(this.airlinename);
                        out.close();
                    } else {
                        System.out.println("Could not create directory");
                        System.exit(1);
                    }
                }
            }
        }
        try {
            sc = new Scanner(new File(this.filename));
        } catch (FileNotFoundException e) {
            System.out.println("File is not present.");
        }
        ArrayList<String> lines = new ArrayList<String>();
        assert sc != null;
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        Airline airline = new Airline();
        airline.setName(lines.get(0));
        //This for loop checks each and every detail of every flight in the text file
        for(int i = 1; i < lines.size(); i++) {
            String[] words = lines.get(i).split(" ");
            if(words.length != 14) {
                System.err.println("The text file is not formatted properly.");
                System.exit(1);
            }
            checkFlightnum(words[1]);
            checkairportcode(words[3]);
            checkairportcode(words[9]);
            checkdatetime(words[5], words[6], words[7]);
            checkdatetime(words[11], words[12], words[13]);
            Flight flight = new Flight();
            flight.setFlightnum(words[1]);
            flight.setSrc(words[3]);
            flight.setDepart(words[5], words[6], words[7]);
            flight.setDest(words[9]);
            flight.setArrive(words[11], words[12], words[13]);
            flight.checkdeparturebeforearrival();
            airline.addFlight(flight);
        }

        //Checks if the airline name in the file is similar to that in the command line or not
        if(!this.airlinename.equals(lines.get(0))){
            System.err.println("The airline name is different than in the file");
            System.exit(1);
        }
        return airline;
    }

    /**
     * This method checks whether the flightnumbers in the text file are correct or not
     * @param number the flightnumber of a flight
     */
    public static void checkFlightnum(String number) {
        int num = 0;
        try {
            num = Integer.parseInt(number);
        }
        catch(NumberFormatException e) {
            System.err.println("Invalid flightnumbers in the text file!");
            System.exit(1);
        }
        String numeric = "[0-9]+";
        Pattern pattern = Pattern.compile(numeric);
        Matcher matcher = pattern.matcher(Integer.toString(num));
        if (matcher.matches()) {
            return;
        }
        else {
            System.out.println("Invalid flightnumbers in the text file!");
            System.exit(1);
        }
    }

    /**
     * This method checks whether the airport codes in the text file are correct or not
     * @param source the airport code of a source or a destination
     */
    public static void checkairportcode(String source) {
        String sourceuppercase = source.toUpperCase();
        Map names = AirportNames.getNamesMap();
        if(!names.containsKey(sourceuppercase)){
            System.err.println("The three-letter airport code is invalid in the text file");
            System.exit(1);
        }
        return;
    }

    /**
     * This method checks whether the dates and times in the text file are correct or not
     * @param date the departure or arrival date of a flight
     * @param time the departure or arrival time of a flight
     * @param ampm The time of the day (am or pm)
     */
    public static void checkdatetime(String date, String time, String ampm) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String finaldatetime = date + " " + time + " " + ampm;
        try{
            Date d = formatter.parse(finaldatetime);
        }
        catch (ParseException e){
            System.err.println("Please verify the format for datetime in the text file");
            System.exit(1);
        }
    }
}
