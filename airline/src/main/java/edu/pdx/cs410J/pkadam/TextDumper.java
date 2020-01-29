package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import javax.lang.model.element.Element;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TextDumper implements AirlineDumper {
    String filename;
    @Override
    public void dump(AbstractAirline abstractAirline) throws IOException {
        ArrayList arrli = (ArrayList) abstractAirline.getFlights();
        String[] flights = new String[arrli.size()];
        //String fileContent = "Hello Learner !! Welcome to howtodoinjava.com.";
        File f = new File(filename);
        PrintWriter out = new PrintWriter(filename);
        out.write("");
        out.write(abstractAirline.getName());
        for(int i=0; i < arrli.size(); i++){
            flights[i] = arrli.get(i).toString();
            out.write("\n");
            out.write(flights[i]);
        }
        out.close();
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
