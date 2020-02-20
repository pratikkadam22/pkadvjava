package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class converts the text file representation of flights to xml representation
 */
public class Converter {

    /**
     * This method converts text file to xml file
     * @param args these are the names of text file and xml file
     */
    public static void main(String[] args) throws ParserException, IOException {

        File file = new File(args[0]);
        if(!file.exists()){

            System.err.println("No text file exists with the given filename");
            System.exit(1);
        }
        if(file.length() == 0) {
            System.err.println("Given text file is empty");
            System.exit(1);
        }
        Scanner sc = new Scanner(new File(args[0]));
        ArrayList<String> lines = new ArrayList<String>();
        assert sc != null;
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        TextParser parser = new TextParser();
        XmlDumper xdumper = new XmlDumper(args[1]);
        parser.setnames(args[0], lines.get(0));
        AbstractAirline parsedairline = parser.parse();
        xdumper.dump(parsedairline);
        System.out.println("Text file was successfully converted to XML file.");
        System.exit(0);
    }
}
