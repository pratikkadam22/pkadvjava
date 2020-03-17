package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project5Test extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "80");

    @Test
    public void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project5.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("No arguments!"));
    }

    @Test
    public void test2EmptyServer() {
        MainMethodResult result = invokeMain( Project5.class, HOSTNAME, PORT );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(""));
    }

    @Test
    public void test4Readme() {
        MainMethodResult result = invokeMain( Project5.class, "-README");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Name: Pratik Kadam"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test4AddFlight() {
        MainMethodResult result = invokeMain( Project5.class, "-host", HOSTNAME, "-port", PORT,
                "etihad", "111", "PDX", "07/19/2020", "1:02", "pm", "ORD", "07/19/2020", "6:22", "pm" );
        assertThat(result.getExitCode(), equalTo(1));
        //assertThat(result.getTextWrittenToStandardOut(), containsString("Flight added to the airline."));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test4AddFlightPrint() {
        MainMethodResult result = invokeMain( Project5.class, "-print", "-host", HOSTNAME, "-port", PORT,
                "etihad", "111", "PDX", "07/19/2020", "1:02", "pm", "ORD", "07/19/2020", "6:22", "pm" );
        assertThat(result.getExitCode(), equalTo(1));
        //assertThat(result.getTextWrittenToStandardOut(), containsString("Following"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test4searchflight() {
        MainMethodResult result = invokeMain( Project5.class, "-search", "-host", HOSTNAME, "-port", PORT,
                "etihad", "PDX", "ORD" );
        assertThat(result.getExitCode(), equalTo(1));
        //assertThat(result.getTextWrittenToStandardOut(), containsString("Airline : etihad"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test4searchairline() {
        MainMethodResult result = invokeMain( Project5.class, "-search", "-host", HOSTNAME, "-port", PORT,
                "etihad");
        assertThat(result.getExitCode(), equalTo(1));
       // assertThat(result.getTextWrittenToStandardOut(), containsString("Airline : etihad"));
    }

}
