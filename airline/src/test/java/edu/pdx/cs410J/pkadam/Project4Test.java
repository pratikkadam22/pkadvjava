package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * Unit tests for the {@link Project4} main class.
 */
public class Project4Test extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project4} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project4.class, args );
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
        MainMethodResult result = invokeMain(new String[] {"-print", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 123 departs iah at 03/03/2017 12:00 am arrives iad at 03/03/2017 4:00 pm"));
    }

    @Test
    public void testNoCommandLineArguments(){
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("No arguments!"));
    }

    @Test
    public void missingCommandLineArguments(){
        MainMethodResult result = invokeMain(new String[] {"emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void wrongflightnumber(){
        MainMethodResult result = invokeMain(new String[] {"-print", "emirates", "xxx", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Please enter a numeric flightnumber!"));
    }

    @Test
    public void filenotexist(){
        File file = new File("xyz.txt");
        MainMethodResult result = null;
        if(file.delete()) {
            result = invokeMain(new String[]{"-textFile", "xyz.txt", "-print", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        }
        else{
            result = invokeMain(new String[]{"-textFile", "xyz.txt", "-print", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
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
        MainMethodResult result = invokeMain(new String[] {"-print", "emirates", "-print", "pdx", "03/03/2017", "12:00", "pm", "dbo", "09/09/2017", "16:00", "am"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Please check the arguments"));
    }

    @Test
    public void testnewairline(){
        File file = new File("pratik.txt");
        if(file.exists()){
            file.delete();
            MainMethodResult result = invokeMain(new String[]{"-textFile", "pratik.txt", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
            assertThat(result.getExitCode(), equalTo(0));
            assertTrue(file.exists());
        }
        else {
            MainMethodResult result = invokeMain(new String[]{"-textFile", "pratik.txt", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
            assertThat(result.getExitCode(), equalTo(0));
            assertTrue(file.exists());
        }
    }

    @Test
    public void testnewairlinewithprint(){
        File file = new File("pratikk.txt");
        if(file.exists()){
            file.delete();
            MainMethodResult result = invokeMain(new String[] {"-textFile", "pratikk.txt", "-print", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
            assertThat(result.getExitCode(), equalTo(0));
            assertTrue(file.exists());
        }
        else {
            MainMethodResult result = invokeMain(new String[]{"-textFile", "pratikk.txt", "-print", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
            assertThat(result.getExitCode(), equalTo(0));
            assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 123 departs iah at 03/03/2017 12:00 am arrives iad at 03/03/2017 4:00 pm"));
            assertTrue(file.exists());
        }
    }

    @Test
    public void testinvalidtimeinfile(){
        File file = new File("src/test/resources/edu/pdx/cs410J/pkadam/invalidtime.txt");
        MainMethodResult result = invokeMain(new String[] {"-textFile", "src/test/resources/edu/pdx/cs410J/pkadam/invalidtime.txt", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Please verify the format for datetime in the text file"));
    }

    @Test
    public void testinvalidformatinfile(){
        File file = new File("src/test/resources/edu/pdx/cs410J/pkadam/invalidformat.txt");
        MainMethodResult result = invokeMain(new String[] {"-textFile", "src/test/resources/edu/pdx/cs410J/pkadam/invalidformat.txt", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The text file is not formatted properly."));
    }

    @Test
    public void testsingleprettyprint() {
        MainMethodResult result = invokeMain(new String[] {"-pretty", "-", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Duration (minutes) : 960"));
    }

    @Test
    public void testtextfileandsingleprint(){
        File file = new File("xyz.txt");
        MainMethodResult result = null;
        if(file.delete()) {
            result = invokeMain(new String[]{"-textFile", "xyz.txt", "-pretty", "-", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        }
        else{
            result = invokeMain(new String[]{"-textFile", "xyz.txt", "-pretty", "-", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        }
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Duration (minutes) : 960"));
    }

    @Test
    public void testtextandprettyfile(){
        File file1 = new File("xyz.txt");
        File file2 = new File("xyzpretty.txt");
        MainMethodResult result = null;
        if(file1.delete()) {
            result = invokeMain(new String[]{"-textFile", "xyz.txt", "-pretty", "xyzpretty.txt", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        }
        else{
            result = invokeMain(new String[]{"-textFile", "xyz.txt", "-pretty", "xyzpretty.txt", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        }
        assertThat(result.getExitCode(), equalTo(0));
        assertTrue(file2.exists());
    }

    @Test
    public void testtextandprettyfileindirectory(){
        File file1 = new File("xyz/xyz.txt");
        File file2 = new File("xyz/xyzpretty.txt");
        MainMethodResult result = null;
        if(file1.delete()) {
            result = invokeMain(new String[]{"-textFile", "xyz/xyz.txt", "-pretty", "xyz/xyzpretty.txt", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        }
        else{
            result = invokeMain(new String[]{"-textFile", "xyz/xyz.txt", "-pretty", "xyz/xyzpretty.txt", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        }
        assertThat(result.getExitCode(), equalTo(0));
        assertTrue(file2.exists());
    }

    @Test
    public void justcorrectargument() {
        MainMethodResult result = invokeMain(new String[] {"emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void twelveargumenterrortext(){
        MainMethodResult result = invokeMain(new String[] {"-textFile", "xyz.txt", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "-README"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Please check the arguments"));
    }

    @Test
    public void twelveargumenterrorpretty(){
        MainMethodResult result = invokeMain(new String[] {"-pretty", "-", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "-README"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Please check the arguments"));
    }

    @Test
    public void twelveargumentprettyfile(){
        File file2 = new File("prettyfile.txt");
        MainMethodResult result = invokeMain(new String[] {"-pretty", "prettyfile.txt", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "pm"});
        assertThat(result.getExitCode(), equalTo(0));
        assertTrue(file2.exists());
    }

    @Test
    public void fourteenargumenterrorpretty(){
        MainMethodResult result = invokeMain(new String[] {"-pretty", "xyz.txt", "-textFile", "abc.txt", "emirates", "123", "iah", "03/03/2017", "12:00", "am", "iad", "03/03/2017", "4:00", "-README"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Please check the arguments"));
    }
}
