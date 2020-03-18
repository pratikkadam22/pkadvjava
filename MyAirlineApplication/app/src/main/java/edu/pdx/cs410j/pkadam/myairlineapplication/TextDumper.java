package edu.pdx.cs410j.pkadam.myairlineapplication;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.*;

/**
 * This class implements the AirlineDumper interface to dump the airline in text file
 */
public class TextDumper implements AirlineDumper {
    String filename;
    File dir;

    /**
     * This method sets the class variable "filename" which is later used in the dump method
     * @param nameoffile name of the file to store airline in
     */
    public TextDumper (File directory, String nameoffile){
        this.dir = directory;
        this.filename = nameoffile;
    }
    /**
     * This method dumps the airline with its associated flights into a text file
     * @param abstractAirline The object of the class AbstractAirline to dump in text file
     */
    @Override
    public void dump(AbstractAirline abstractAirline) throws IOException {
        ArrayList arrli = (ArrayList) abstractAirline.getFlights();
        String[] flights = new String[arrli.size()];
        PrintWriter out = new PrintWriter(new File(dir, filename));
        out.write("");
        out.write(abstractAirline.getName());
        Collections.sort(arrli);
        for(int i=0; i < arrli.size(); i++){
            flights[i] = arrli.get(i).toString();
            out.write("\n");
            out.write(flights[i]);
        }
        out.close();
    }
}

