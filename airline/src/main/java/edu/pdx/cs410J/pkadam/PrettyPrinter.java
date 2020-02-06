package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirlineDumper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the AirlineDumper interface to dump the prettily printed airline to a text file
 */
public class PrettyPrinter implements AirlineDumper {
    String filename;

    /**
     * This method formats a single flight in a pretty way
     * @param flight the object of the class AbstractFlight
     * @param aname the name of the airline to which the Flight object belongs to
     * @return It returns prettily formatted details of a Flight in the form of a string
     */
    public String getpretty(AbstractFlight flight, String aname) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        long diff = flight.getArrival().getTime() - flight.getDeparture().getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        String prettytext = "\nAirline : " + aname +
                "\nFlightnumber : " + flight.getNumber() +
                "\nSource : " + flight.getSource() +
                "\nDate of Departure : " + formatter.format(flight.getDeparture()) +
                "\nTime of Departure : " + formatter.getTimeInstance(DateFormat.SHORT).format(flight.getDeparture()) +
                "\nDestination : " + flight.getDestination() +
                "\nDate of Arrival : " + formatter.format(flight.getArrival()) +
                "\nTime of Arrival : " + formatter.getTimeInstance(DateFormat.SHORT).format(flight.getArrival()) +
                "\nDuration (minutes) : " + minutes + "\n" ;
        return prettytext;
    }

    /**
     * This method dumps all the flights of the airline prettily in a text file
     * @param abstractAirline the Airline object to dump in the text file in a pretty way
     */
    @Override
    public void dump(AbstractAirline abstractAirline) throws IOException {
        ArrayList arrli = (ArrayList) abstractAirline.getFlights();
        String[] flights = new String[arrli.size()];
        Collections.sort(arrli);
        Scanner sc = null;
        try {
            sc = new Scanner(new File(this.filename));
        } catch (FileNotFoundException e) {
            System.out.println("Pretty Print file with given name does not exist. File created.");
            if(!this.filename.contains("/")){
                PrintWriter out = null;
                try {
                    out = new PrintWriter(this.filename);
                } catch (FileNotFoundException ex) {
                    System.out.println("Pretty Print file is not present.");
                }
                out.write("");
                for(int i=0; i < arrli.size(); i++){
                    String x = getpretty((AbstractFlight) arrli.get(i), abstractAirline.getName());
                    out.write(x);
                }
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
                    out.write("");
                    for(int i=0; i < arrli.size(); i++){
                        String x = getpretty((AbstractFlight) arrli.get(i), abstractAirline.getName());
                        out.write(x);
                    }
                    out.close();
                } else {
                    File folder = new File(v);
                    if (folder.mkdir()) {
                        try {
                            out = new PrintWriter(this.filename);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        out.write("");
                        for(int i=0; i < arrli.size(); i++){
                            String x = getpretty((AbstractFlight) arrli.get(i), abstractAirline.getName());
                            out.write(x);
                        }
                        out.close();
                    } else {
                        System.out.println("Could not create directory");
                        System.exit(1);
                    }
                }
            }
        }
    }

    /**
     * This method sets the class variable "filename" which is later used in the dump method
     * @param filename the name of the prettyprint text file
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
