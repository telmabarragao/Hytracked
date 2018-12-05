package com.hytracked.hytrackedapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SetupMenu extends AppCompatActivity {


    EditText weight, name, activityLvl;
    float litresNecessary;
    String csv = "../../../../../../database/records.csv";
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_menu);

        name = findViewById(R.id.name);
        weight = findViewById(R.id.Weight_input);


    }
    public void submitInfo(View viewwhat){


        LayoutInflater inflater = this.getLayoutInflater();
        View setupView = inflater.inflate(R.layout.activity_setup_menu, null);
        Intent intent = new Intent(this, MainScreen.class);

        //GET NAME
        String theName = name.getText().toString();
        String theWeight = weight.getText().toString();

        // GET WEIGHT NUMBER

        intent.putExtra("NAME", theName);
        intent.putExtra("WEIGHT", theWeight);

        startActivity(intent);
        //float number = Float.valueOf(weight.toString());

        //GET NECESSARY LITRES -> SEND THIS TO BOTTLE!!!!!
       // litresNecessary = number * 0.035f;

       // try {
       //     updateInfoToCSV();

      //  } catch (IOException e) {
      //      e.printStackTrace();
     //   }
    }


    public void updateInfoToCSV() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(csv));
        List<String[]> data = new ArrayList<String[]>();
        data.add(new String[] {name.toString()});
        data.add(new String[] {weight.toString()});
        writer.writeAll(data);
        writer.close();

        sendInfoToBottle(weight.toString());
    }

    public void sendInfoToBottle(String info) throws IOException {
        String[] row = null;
        CSVReader reader = new CSVReader(new FileReader(csv));
        List content = reader.readAll();

        for (Object line : content){
            row = (String[]) line;

            System.out.println(row);
        }
    }

}
