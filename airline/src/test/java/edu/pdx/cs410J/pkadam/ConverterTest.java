package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class ConverterTest extends InvokeMainTestCase {
    /**
     * Invokes the main method of {@link Project4} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Converter.class, args );
    }

    @Test
    public void convertertest(){
        MainMethodResult result = invokeMain(new String[] {"src/test/resources/edu/pdx/cs410J/pkadam/sample.txt", "sampleyo.xml"});
        File file = new File("sampleyo.xml");
        if(file.delete()) {
            XmlParser xmlParser = new XmlParser("sampleyo.xml", "emirates");
            Airline airline = xmlParser.parse();
            assertThat(airline.getName(), equalTo("emirates"));

        } else {
            XmlParser xmlParser = new XmlParser("sampleyo.xml", "emirates");
            Airline airline = xmlParser.parse();
            assertThat(airline.getName(), equalTo("emirates"));
        }
    }
}
