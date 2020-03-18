package edu.pdx.cs410j.pkadam.myairlineapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.Map;

import edu.pdx.cs410J.AirportNames;

public class LaunchSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_search);
    }

    public void searchflights(View view){
        Intent intent = new Intent(this, resultsActivity.class);
        EditText aname = findViewById(R.id.searchairlinename);
        if(aname.getText().toString().equals("")){
            Snackbar.make(view, "Enter valid Airline name", 1000).show();
            return;
        }
        File dir = getFilesDir();
        File file = new File(dir, aname.getText().toString() + ".txt");
        if(!file.exists()){
            Snackbar.make(view, "The Airline " + aname.getText().toString() + " does not exist", 1000).show();
            return;
        }
        EditText source = findViewById(R.id.searchsource);
        EditText dest = findViewById(R.id.searchdestination);

        if(source.getText().toString().equals("") && dest.getText().toString().length() > 0){
            Snackbar.make(view, "Enter valid search parameters", 1000).show();
            return;
        }
        else if (dest.getText().toString().equals("") && source.getText().toString().length() > 0){
            Snackbar.make(view, "Enter valid search parameters", 1000).show();
            return;
        }

        if(source.getText().toString().length() > 0 && dest.getText().toString().length() > 0){
            if(!checkairportcode(source.getText().toString()) || !checkairportcode(dest.getText().toString())){
                Snackbar.make(view, "Check the Airport codes", 1000).show();
                return;
            }
        }

        intent.putExtra("airlinename", aname.getText().toString());
        intent.putExtra("source", source.getText().toString());
        intent.putExtra("destination", dest.getText().toString());
        startActivity(intent);
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
}
