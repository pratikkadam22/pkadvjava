package edu.pdx.cs410J.pkadam;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class is responsible for dumping the airline into an xml file
 */
public class XmlDumper implements AirlineDumper {
    String xmlFileName;
    Document document = null;
    Airline airline = null;

    /**
     * Stores the fileName
     * @param xmlFileName
     *        The name of the file into which the airline will be dumped.
     */
    public XmlDumper(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    /**
     * This method appends the newly created flight details for the airline into the text file
     * @param airline
     *        The airline that needs to be dumped.
     * @throws IOException
     */
    @Override
    public void dump(AbstractAirline airline) throws IOException {
        this.airline = (Airline) airline;
        List<Flight> flightsList = (List<Flight>) airline.getFlights();
        try {
//            File filexml = new File(xmlFileName);
//            if(filexml.exists() && filexml.length() == 0){
//                System.err.println("XML file already exists and is empty!");
//                System.exit(1);
//            }
            AirlineXmlHelper helper = new AirlineXmlHelper();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);

            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);
            File file = new File(this.xmlFileName);
            if(!file.exists()) {
                file.createNewFile();
            } else {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }
            document = builder.newDocument();
            } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Element rootElement = document.createElement("airline");
        document.appendChild(rootElement);
        Element nameNode = document.createElement("name");
        nameNode.appendChild(document.createTextNode(airline.getName()));
        rootElement.appendChild(nameNode);
        for(Flight f: flightsList) {
            try {
                addToXML(f);
            } catch (TransformerException | ParseException e) {
                e.printStackTrace();
                System.exit(1);
            }
            System.out.println("Flight written in xml file");
        }
    }

    /**
     * Appends a single flight to the xml
     * @param flight the flight that needs to be appended to the xml file
     * @throws TransformerException
     * @throws ParseException
     */
    private void addToXML(Flight flight) throws TransformerException, ParseException {
        SimpleDateFormat inf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        SimpleDateFormat outf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        Element documentElement = document.getDocumentElement();

        Element nodeElement = document.createElement("flight");
        documentElement.appendChild(nodeElement);

        Element textNodeNum = document.createElement("number");
        textNodeNum.setTextContent("" + flight.getNumber());
        nodeElement.appendChild(textNodeNum);

        Element textNodeSrc = document.createElement("src");
        textNodeSrc.setTextContent(flight.getSource());
        nodeElement.appendChild(textNodeSrc);

        Element textNodeDept = document.createElement("depart");
        Date deptDateTime = inf.parse(flight.getDepartureString());
        String outd = outf.format(deptDateTime);
        Date deptDate = outf.parse(outd);
        Calendar c = Calendar.getInstance();
        c.setTime(deptDate);
        Element subtextNodeDeptDate = document.createElement("date");
        subtextNodeDeptDate.setAttribute("day", c.get(Calendar.DATE) + "");
        subtextNodeDeptDate.setAttribute("month", c.get(Calendar.MONTH)+1 + "");
        subtextNodeDeptDate.setAttribute("year", c.get(Calendar.YEAR) + "");
        Element subtextNodeDeptTime = document.createElement("time");
        subtextNodeDeptTime.setAttribute("hour", c.get(Calendar.HOUR_OF_DAY) + "");
        subtextNodeDeptTime.setAttribute("minute", c.get(Calendar.MINUTE) + "");
        textNodeDept.appendChild(subtextNodeDeptDate);
        textNodeDept.appendChild(subtextNodeDeptTime);
        nodeElement.appendChild(textNodeDept);

        Element textNodeDest = document.createElement("dest");
        textNodeDest.setTextContent(flight.getDestination());
        nodeElement.appendChild(textNodeDest);

        Element textNodeArr = document.createElement("arrive");
        Date arrvDateTime = inf.parse(flight.getArrivalString());
        String outa = outf.format(arrvDateTime);
        Date arrvDate = outf.parse(outa);
        c.setTime(arrvDate);
        Element subtextNodeArrDate = document.createElement("date");
        subtextNodeArrDate.setAttribute("day", c.get(Calendar.DATE) + "");
        subtextNodeArrDate.setAttribute("month", c.get(Calendar.MONTH)+1 + "");
        subtextNodeArrDate.setAttribute("year", c.get(Calendar.YEAR) + "");
        Element subtextNodeArrTime = document.createElement("time");
        subtextNodeArrTime.setAttribute("hour", c.get(Calendar.HOUR_OF_DAY) + "");
        subtextNodeArrTime.setAttribute("minute", c.get(Calendar.MINUTE) + "");
        textNodeArr.appendChild(subtextNodeArrDate);
        textNodeArr.appendChild(subtextNodeArrTime);
        nodeElement.appendChild(textNodeArr);


        Transformer tFormer = TransformerFactory.newInstance().newTransformer();
        tFormer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        tFormer.setOutputProperty(OutputKeys.ENCODING, "us-ascii");
        tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
        DOMImplementation domImpl = document.getImplementation();
        //AirlineXmlHelper helper = new AirlineXmlHelper();
        DocumentType doctype = domImpl.createDocumentType("doctype", "", "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd");
        tFormer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
        tFormer.setOutputProperty(OutputKeys.INDENT, "yes");
        document.setXmlStandalone(true);
        Source source = new DOMSource(document);
        Result result = new StreamResult(this.xmlFileName);
        tFormer.transform(source, result);
    }
}
