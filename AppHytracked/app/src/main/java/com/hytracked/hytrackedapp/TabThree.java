package com.hytracked.hytrackedapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TabThree extends Fragment {

    EditText weight, name, activityLvl;
    float litresNecessary;
    String csv = "../../../../../../database/records.csv";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_three, container, false);

        return view;

    }

    public View submitInfo(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState,View view) throws IOException {

        //GET NAME
        name = (EditText) view.findViewById(R.id.name);

        // GET WEIGHT NUMBER
        weight = (EditText) view.findViewById(R.id.Weight_input);
        float number = Float.valueOf(weight.getText().toString());

        //GET NECESSARY LITRES -> SEND THIS TO BOTTLE!!!!!
        litresNecessary = number * 0.035f;

        try {
            updateInfoToCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inflater.inflate(R.layout.tab_one, container, false);

    }

    public void updateInfoToCSV() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(csv));
        List<String[]> data = new ArrayList<String[]>();
        data.add(new String[] {name.toString()});
        data.add(new String[] {weight.toString()});
        writer.writeAll(data);
        writer.close();
    }
}
