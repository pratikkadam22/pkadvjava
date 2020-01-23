package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many arguments!"));
    }
}