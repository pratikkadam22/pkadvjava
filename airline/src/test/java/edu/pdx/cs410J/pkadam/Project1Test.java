package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * Unit tests for the {@link Project1} main class.
 */
public class Project1Test extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testreadme() {
        MainMethodResult result = invokeMain(new String[] {"-README"});
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Name: Pratik Kadam"));
    }

    @Test
    public void testprint() {
        MainMethodResult result = invokeMain(new String[] {"-print", "emirates", "123", "pdx", "03/03/2017", "12:00", "dbo", "09/09/2017", "16:00"});
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 123 departs pdx at 03/03/2017 12:00 arrives dbo at 09/09/2017 16:00"));
    }

    @Test
    public void testNoCommandLineArguments(){
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("No arguments!"));
    }

    @Test
    public void missingCommandLineArguments(){
        MainMethodResult result = invokeMain(new String[] {"-print", "emirates", "123", "pdx"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void toomanyCommandLineArguments(){
        MainMethodResult result = invokeMain(new String[] {"-print", "emirates", "123", "pdx", "03/03/2017", "12:00", "dubai", "09/09/2017", "16:00", "dubai"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Please check the arguments"));
    }

    @Test
    public void createnewairline(){
//        File file = new File("pratik.txt");
//        assertFalse(!file.exists());
        MainMethodResult result = invokeMain(new String[] {"-textFile","pratik.txt","-print","emirates","123","pdx","03/03/2017","12:00","dbo","09/09/2017","16:00"});
        assertThat(result.getExitCode(), equalTo(0));
//        assertTrue(file.exists());
//        TextParser parser = new TextParser();
//        parser.setnames("pratik.txt", "emirates");
//        AbstractAirline parsedairline = parser.parse();
//        String expected = "Flight 123 departs pdx at 03/03/2017 12:00 arrives dbo at 09/09/2017 16:00";
//        assertTrue(parsedairline.getFlights().toString().contains(expected));
//        assertThat(result.getTextWrittenToStandardError(), containsString("Please check the arguments"));
    }
//
//    @Test
//    public void filecontentformatiswrong() throws FileNotFoundException {
//        File file = new File("pratik.txt");
//        PrintWriter out = new PrintWriter(file);
//        out.write("ageaesdgdgea\ndfdsfadsdf\nsdaff");
//        MainMethodResult result = invokeMain(new String[] {"-textFile", "pratik.txt", "-print", "emirates", "123", "pdx", "03/03/2017", "12:00", "dbo", "09/09/2017", "16:00"});
//        assertThat(result.getExitCode(), equalTo(1));
//        assertThat(result.getTextWrittenToStandardError(), containsString("The text file is not formatted properly."));
//    }
}