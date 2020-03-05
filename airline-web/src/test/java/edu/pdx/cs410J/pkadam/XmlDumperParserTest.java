package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.pkadam.Airline;
import edu.pdx.cs410J.pkadam.Flight;
import edu.pdx.cs410J.pkadam.XmlDumper;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class XmlDumperParserTest
{
    @Test
    public void TestXmlDumperParser() throws TransformerConfigurationException, IOException, ParserConfigurationException, SAXException {
        Airline airline = new Airline();
        airline.setName("emirates");
        Flight flight = new Flight();
        flight.setFlightnum("123");
        flight.setSrc("PDX");
        flight.setDest("ORD");
        flight.setDepart("01/07/2019", "07:00","am");
        flight.setArrive("01/07/2019", "09:00","am");
        airline.addFlight(flight);


        //airline = new Airline("Emirates");
        flight = new Flight();
        flight.setFlightnum("245");
        flight.setSrc("PDX");
        flight.setDest("ORD");
        flight.setDepart("02/07/2019", "07:00","pm");
        flight.setArrive("03/07/2019", "09:00","pm");
        airline.addFlight(flight);
        StringWriter stringWriter = new StringWriter();

        XmlDumper xmlDumper = new XmlDumper(stringWriter);
        xmlDumper.dump(airline);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlDumper.generateAsString()));

        XmlParser parser = new XmlParser(builder.parse(is), "emirates");
        Airline parsedairline = parser.parse();
        assertThat(parsedairline.getName(), equalTo("emirates"));
    }
}
