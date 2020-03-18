package edu.pdx.cs410j.pkadam.myairlineapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import edu.pdx.cs410J.ParserException;

public class resultsActivity extends AppCompatActivity {
    String aname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        File dir = getFilesDir();
        Intent intent = getIntent();
        aname = intent.getStringExtra("airlinename");
        String source = intent.getStringExtra("source");
        String dest = intent.getStringExtra("destination");

        ListView listView = findViewById(R.id.resultsview);
        ArrayAdapter<Flight> adapter = new FlightAdapter(this);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        TextParser parser = new TextParser(dir, aname + ".txt", aname);
        Airline parsedairline = null;
        try {
            parsedairline = (Airline) parser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        ArrayList<Flight> arrli = parsedairline.getFlights();
        ArrayList<Flight> temp = new ArrayList<>();

        if (source.equals("") && dest.equals("")) {
            for (Flight f : arrli) {
                adapter.add(f);
                temp.add(f);
            }
        } else {
            for (Flight flight : arrli) {
                if (flight.getSource().equals(source) && flight.getDestination().equals(dest)) {
                    adapter.add(flight);
                    temp.add(flight);
                }
            }
        }
        if (temp.size() == 0) {
            adapter2.add("No flights found!");
            listView.setAdapter(adapter2);
        } else {
            listView.setAdapter(adapter);
        }
    }

    class FlightAdapter extends ArrayAdapter<Flight> {

        public FlightAdapter(@NonNull Context context) {
            super(context, R.layout.flight_view);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View flightview, @NonNull ViewGroup parent) {
            PrettyPrinter pretty = new PrettyPrinter();
            if (flightview == null){
                flightview = getLayoutInflater().inflate(R.layout.flight_view, parent, false);
            }
            Flight flight = getItem(position);
            TextView number = flightview.findViewById(R.id.flightsview);
            number.setText(pretty.getpretty(flight, aname));
            return flightview;
        }
    }
}
