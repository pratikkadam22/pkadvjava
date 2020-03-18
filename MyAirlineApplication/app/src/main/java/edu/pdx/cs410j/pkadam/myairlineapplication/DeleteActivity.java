package edu.pdx.cs410j.pkadam.myairlineapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
    }

    public void deleteairline(View view) {
        EditText aname = findViewById(R.id.deleteairlinename);
        if(aname.getText().toString().equals("")){
            Snackbar.make(view, "Enter valid Airline name", 1000).show();
            return;
        }
        File dir = getFilesDir();
        File f = new File(dir, aname.getText().toString() + ".txt");
        if (f.exists()){
            f.delete();
            Snackbar.make(view, "The Airline " + aname.getText().toString() + " has been deleted", 1000).show();
            aname.setText("");
        }
        else {
            Snackbar.make(view, "The Airline " + aname.getText().toString() + " does not exist", 1000).show();
            aname.setText("");
        }
    }
}
