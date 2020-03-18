package edu.pdx.cs410j.pkadam.myairlineapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;

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

        intent.putExtra("airlinename", aname.getText().toString());
        intent.putExtra("source", source.getText().toString());
        intent.putExtra("destination", dest.getText().toString());
        startActivity(intent);
    }
}
