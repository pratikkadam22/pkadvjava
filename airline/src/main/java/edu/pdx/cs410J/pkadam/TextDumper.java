package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import javax.lang.model.element.Element;
import java.io.*;
import java.util.*;

/**
 * This class implements the AirlineDumper interface to dump the airline in text file
 */
public class TextDumper implements AirlineDumper {
    String filename;

    /**
     * This method dumps the airline with its associated flights into a text file
     * @param abstractAirline The object of the class AbstractAirline to dump in text file
     */
    @Override
    public void dump(AbstractAirline abstractAirline) throws IOException {
        ArrayList arrli = (ArrayList) abstractAirline.getFlights();
        String[] flights = new String[arrli.size()];
        File f = new File(filename);
        PrintWriter out = new PrintWriter(filename);
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

    /**
     * This method sets the class variable "filename" which is later used in the dump method
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
