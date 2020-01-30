package edu.pdx.cs410J.pkadam;

import com.sun.tools.javac.Main;
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
 * Unit tests for the {@link Project2} main class.
 */
public class Project2Test extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testnoarguments(){
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("No arguments!"));
    }

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
    public void wrongflightnumber(){
        MainMethodResult result = invokeMain(new String[] {"-print", "emirates", "ewrdf2", "pdx", "03/03/2017", "12:00", "dbo", "09/09/2017", "16:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Please enter a numeric flightnumber!"));
    }

    @Test
    public void filenotexist(){
        File file = new File("xyz.txt");
        MainMethodResult result = null;
        if(file.delete()) {
            result = invokeMain(new String[]{"-textFile", "xyz.txt", "-print", "emirates", "23451", "pdx", "03/03/2017", "12:00", "dbo", "09/09/2017", "16:00"});
        }
        else{
            result = invokeMain(new String[]{"-textFile", "xyz.txt", "-print", "emirates", "23451", "pdx", "03/03/2017", "12:00", "dbo", "09/09/2017", "16:00"});
        }
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("File with given name does not exist. File created."));
    }

    @Test
    public void toomanyCommandLineArguments(){
        MainMethodResult result = invokeMain(new String[] {"-print", "emirates", "123", "pdx", "03/03/2017", "12:00", "dubai", "09/09/2017", "16:00", "dubai"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Please check the arguments"));
    }

    @Test
    public void invalidarguments(){
        MainMethodResult result = invokeMain(new String[] {"emirates", "-print", "pdx", "03/03/2017", "12:00", "dbo", "09/09/2017", "16:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Please check the arguments"));
    }

    @Test
    public void testnewairline(){
        File file = new File("pratik.txt");
        MainMethodResult result = invokeMain(new String[] {"-textFile", "pratik.txt", "emirates", "123", "pdx", "03/03/2017", "12:00", "dbo", "09/09/2017", "16:00"});
        assertThat(result.getExitCode(), equalTo(0));
        assertTrue(file.exists());
    }

    @Test
    public void testnewairlinewithprint(){
        File file = new File("pratik.txt");
        MainMethodResult result = invokeMain(new String[] {"-textFile", "pratik.txt", "-print", "emirates", "123", "pdx", "03/03/2017", "12:00", "dbo", "09/09/2017", "16:00"});
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 123 departs pdx at 03/03/2017 12:00 arrives dbo at 09/09/2017 16:00"));
        assertTrue(file.exists());
    }

    @Test
    public void testinvalidtimeinfile(){
        File file = new File("src/test/resources/edu/pdx/cs410J/pkadam/invalidtime.txt");
        MainMethodResult result = invokeMain(new String[] {"-textFile", "src/test/resources/edu/pdx/cs410J/pkadam/invalidtime.txt", "emirates", "123", "pdx", "03/03/2017", "12:00", "dbo", "09/09/2017", "16:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Invalid time format in the text file!"));
    }

    @Test
    public void testinvalidformatinfile(){
        File file = new File("src/test/resources/edu/pdx/cs410J/pkadam/invalidformat.txt");
        MainMethodResult result = invokeMain(new String[] {"-textFile", "src/test/resources/edu/pdx/cs410J/pkadam/invalidformat.txt", "emirates", "123", "pdx", "03/03/2017", "12:00", "dbo", "09/09/2017", "16:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The text file is not formatted properly."));
    }

}
