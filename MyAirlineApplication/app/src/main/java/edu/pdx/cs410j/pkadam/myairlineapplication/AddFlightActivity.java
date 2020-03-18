package edu.pdx.cs410j.pkadam.myairlineapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

public class AddFlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);
    }
    public void addFlight(View view) throws IOException, ParserException {
        File dir = getFilesDir();
        EditText aname = findViewById(R.id.airlinename);
        EditText flightnum = findViewById(R.id.flightnum);
        EditText source = findViewById(R.id.source);
        EditText departure = findViewById(R.id.departure);
        EditText destination = findViewById(R.id.destination);
        EditText arrival = findViewById(R.id.arrival);

        String[] departwords = departure.getText().toString().split(" ");
        String[] arrvwords = arrival.getText().toString().split(" ");

        if(aname.getText().toString().equals("")){
            Snackbar.make(view, "Enter valid Airline name", 1000).show();
            return;
        }
        if(!checkFlightnum(flightnum.getText().toString())){
            Snackbar.make(view, "Enter valid Flight number", 1000).show();
            return;
        }
        if(!checkairportcode(source.getText().toString())){
            Snackbar.make(view, "Enter valid Source", 1000).show();
            return;
        }
        if(departwords.length == 3) {
            if (!checkdatetime(departwords[0], departwords[1], departwords[2])) {
                Snackbar.make(view, "Enter valid Departure date time", 1000).show();
                return;
            }
        }
        else { Snackbar.make(view, "Enter valid Departure date time", 1000).show(); return;  }
        if(!checkairportcode(destination.getText().toString())){
            Snackbar.make(view, "Enter valid Destination", 1000).show();
            return;
        }
        if(arrvwords.length == 3) {
            if (!checkdatetime(arrvwords[0], arrvwords[1], arrvwords[2])) {
                Snackbar.make(view, "Enter valid Arrival date time", 1000).show();
                return;
            }
        }
        else { Snackbar.make(view, "Enter valid Arrival date time", 1000).show(); return;  }

        Flight flight = new Flight();
        flight.setFlightnum(flightnum.getText().toString());
        flight.setSrc(source.getText().toString());
        flight.setDepart(departwords[0], departwords[1], departwords[2]);
        flight.setDest(destination.getText().toString());
        flight.setArrive(arrvwords[0], arrvwords[1], arrvwords[2]);

        if(!flight.checkdeparturebeforearrival()){
            Snackbar.make(view, "Error: Arrival is before Departure", 1000).show();
            return;
        }

        TextParser parser = new TextParser(dir, aname.getText().toString() + ".txt", aname.getText().toString());
        Airline parsedairline  = (Airline) parser.parse();
        parsedairline.addFlight(flight);
        TextDumper dumper = new TextDumper(dir, aname.getText().toString() + ".txt");
        dumper.dump(parsedairline);

        aname.setText("");
        flightnum.setText("");
        source.setText("");
        departure.setText("");
        destination.setText("");
        arrival.setText("");
        Snackbar.make(view, "Flight Added Successfully", 1000).show();
    }

    /**
     * This method checks whether the flightnumbers in the text file are correct or not
     * @param number the flightnumber of a flight
     */
    public static boolean checkFlightnum(String number) {
        int num = 0;
        try {
            num = Integer.parseInt(number);
        }
        catch(NumberFormatException e) {
            System.err.println("Invalid flightnumber!");
            return false;
        }
        String numeric = "[0-9]+";
        Pattern pattern = Pattern.compile(numeric);
        Matcher matcher = pattern.matcher(Integer.toString(num));
        if (matcher.matches()) {
            return true;
        }
        else {
            System.err.println("Invalid flightnumber!");
            return false;
        }
    }

    /**
     * This method checks whether the airport codes in the text file are correct or not
     * @param source the airport code of a source or a destination
     */
    public static boolean checkairportcode(String source) {
        String sourceuppercase = source.toUpperCase();
        Map names = AirportNames.getNamesMap();
        if(!names.containsKey(sourceuppercase)){
            System.err.println("The three-letter airport code is invalid");
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * This method checks whether the dates and times in the text file are correct or not
     * @param date the departure or arrival date of a flight
     * @param time the departure or arrival time of a flight
     * @param ampm The time of the day (am or pm)
     */
    public static boolean checkdatetime(String date, String time, String ampm) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String finaldatetime = date + " " + time + " " + ampm;
        try{
            Date d = formatter.parse(finaldatetime);
        }
        catch (ParseException e){
            System.err.println("Please verify the format for datetime");
            return false;
        }
        return true;
    }

}
