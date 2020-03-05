package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.AirportNames;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible to parse the xml file
 */
public class XmlParser implements AirlineParser {
    Airline airline = null;
    Document xmldoc;

    public XmlParser(Document xmldocpassed, String airLineName) {
        this.xmldoc = xmldocpassed;
        airline = new Airline();
        airline.setName(airLineName);
    }

    /**
     * This method parses the given xml file and returns the airline object
     * @return Returns the airline object
     */
    public Airline parse() {
        try{
            checkAllFlightDetails(xmldoc);
        } catch (ParseException e) {
            System.err.println(e);
            System.err.println("Check the datetime format in the XML file!");
            System.exit(1);
        }
        return airline;
    }

    /**
     * This method validates the data in xml file is correct or not
     * @param doc The document object of the xml file
     */
    private void checkAllFlightDetails(Document doc) throws ParseException {
        SimpleDateFormat inf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        SimpleDateFormat outf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");

//        if(!doc.getElementsByTagName("name").item(0).getTextContent().equals(this.airLineName)) {
//            System.err.println("The provided airline name is different from that in the XML file");
//            System.exit(1);
//        }
        NodeList nList = doc.getElementsByTagName("flight");
        for(int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String flightStr = "";

                flightStr = eElement.getElementsByTagName("number").item(0).getTextContent();
                checkFlightnum(flightStr);

                String src = eElement.getElementsByTagName("src").item(0).getTextContent();
                checkairportcode(src);

                NodeList dept = eElement.getElementsByTagName("depart");
                Element eElement1 = (Element) dept.item(0);
                Node deptDate = eElement1.getElementsByTagName("date").item(0);
                Element subElement1 = (Element) deptDate;
                String deptDay = subElement1.getAttributes().item(0).getTextContent();
                String deptMonth = subElement1.getAttributes().item(1).getTextContent();
                String deptYear = subElement1.getAttributes().item(2).getTextContent();

                Node deptTime = eElement1.getElementsByTagName("time").item(0);
                Element subElement2 = (Element) deptTime;
                String deptHour = subElement2.getAttributes().item(0).getTextContent();
                String deptMinute = subElement2.getAttributes().item(1).getTextContent();
                String deptfinal = deptMonth + "/" + deptDay + "/" + deptYear + " " + deptHour + ":" + deptMinute;
                Date deptdate = inf.parse(deptfinal);
                String deptstr = outf.format(deptdate);
                String[] departure = deptstr.split(" ");
                checkdatetime(departure[0], departure[1], departure[2]);

                String dest = eElement.getElementsByTagName("dest").item(0).getTextContent();
                checkairportcode(dest);

                NodeList arrvs = eElement.getElementsByTagName("arrive");
                Element eElement2 = (Element) arrvs.item(0);
                Node arrvDate = eElement2.getElementsByTagName("date").item(0);
                Element subElement21 = (Element) arrvDate;
                String arrvDay = subElement21.getAttributes().item(0).getTextContent();
                String arrvMonth = subElement21.getAttributes().item(1).getTextContent();
                String arrvYear = subElement21.getAttributes().item(2).getTextContent();

                Node arrvTime = eElement2.getElementsByTagName("time").item(0);
                Element subElement22 = (Element) arrvTime;
                String arrvHour = subElement22.getAttributes().item(0).getTextContent();
                String arrvMinute = subElement22.getAttributes().item(1).getTextContent();
                String arrvfinal = arrvMonth + "/" + arrvDay + "/" + arrvYear + " " + arrvHour + ":" + arrvMinute;
                Date arrvdate = inf.parse(arrvfinal);
                String arrvstr = outf.format(arrvdate);
                String[] arrival = arrvstr.split(" ");
                checkdatetime(arrival[0], arrival[1], arrival[2]);

                Flight flight = new Flight();
                flight.setFlightnum(flightStr);
                flight.setSrc(src);
                flight.setDepart(departure[0], departure[1], departure[2]);
                flight.setDest(dest);
                flight.setArrive(arrival[0], arrival[1], arrival[2]);
                if(flight.checkdeparturebeforearrival()){
                    airline.addFlight(flight);
                }
                else{
                    System.err.println("The flight's arrival time is before its departure time in the XML file");
                    System.exit(1);
                }
            }
        }
    }

    /**
     * This method checks whether the flightnumbers in the text file are correct or not
     * @param number the flightnumber of a flight
     */
    public static void checkFlightnum(String number) {
        int num = 0;
        try {
            num = Integer.parseInt(number);
        }
        catch(NumberFormatException e) {
            System.err.println("Invalid flightnumbers in the XML file!");
            System.exit(1);
        }
        String numeric = "[0-9]+";
        Pattern pattern = Pattern.compile(numeric);
        Matcher matcher = pattern.matcher(Integer.toString(num));
        if (matcher.matches()) {
            return;
        }
        else {
            System.out.println("Please verify the flightnumbers in the XML file!");
            System.exit(1);
        }
    }

    /**
     * This method checks whether the airport codes in the text file are correct or not
     * @param source the airport code of a source or a destination
     */
    public static void checkairportcode(String source) {
        String sourceuppercase = source.toUpperCase();
        Map names = AirportNames.getNamesMap();
        if(!names.containsKey(sourceuppercase)){
            System.err.println("Please verify the airport codes in the XML file");
            System.exit(1);
        }
        return;
    }

    /**
     * This method checks whether the dates and times in the text file are correct or not
     * @param date the departure or arrival date of a flight
     * @param time the departure or arrival time of a flight
     * @param ampm The time of the day (am or pm)
     */
    public static void checkdatetime(String date, String time, String ampm) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String finaldatetime = date + " " + time + " " + ampm;
        try{
            Date d = formatter.parse(finaldatetime);
        }
        catch (ParseException e){
            System.err.println("Please verify the format for datetime in the XML file");
            System.exit(1);
        }
    }

}