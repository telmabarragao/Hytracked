package com.hytracked.hytrackedapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.spec.ECField;
import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    private static final int WRITE_REQUEST_CODE = 1;
    LineChart lineChart, lineChart2;
    float litresNecessary;
    String csvFile = "./records.csv";
    TextView nameOutput;
    TextView weightOutput;
    TextView litresgoalOutput;
    TextView litresdOutput;
    TextView hidlevelOutput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String weight = intent.getStringExtra("WEIGHT");


        //FIELDS TO CHANGE
        nameOutput = findViewById(R.id.nameOutput);
        weightOutput = findViewById(R.id.weightOutput);
        litresgoalOutput = findViewById(R.id.goal);
        litresdOutput = findViewById(R.id.Litres);
        hidlevelOutput = findViewById(R.id.hydrationLvlOutput);


        //SEND WEIGHT TO BOTTLE AND SET IT
        sendWeight(weight);
        weightOutput.setText("Weight: "+weight+"kg");


        //SET TEXT AND WRITE TEXT AND WEIGHT ON FILE CSV
        nameOutput.setText(name);
        /*try {
            //writeToCsv(""+ name + "," + weight);
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        //GET GOAL from BOTTLE and set in view
        //litresgoalOutput.setText(getGoal());
        litresgoalOutput.setText(String.valueOf(calculateLitresNecessary(weight)) + "L");


        //GET ACTUAL litres dranked from BOTTLE and set in view
        litresdOutput.setText(getActual(1) + "L Drank");


        lineChart = findViewById(R.id.lineChart);
        lineChart2 = findViewById(R.id.lineChart2);

        createHydrationLevelChart(lineChart);
        createLitresChart(lineChart2);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        try {
            this.writeToCsv("jhsdbfjhsdbfjhdsbfhjdbfhjbsdfbdsb");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createLitresChart(LineChart lineChart) {
        ArrayList<String> xAxis = new ArrayList<String>();
        ArrayList<Entry> yAxis = new ArrayList<Entry>();
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();

        xAxis.add("Monday");
        xAxis.add("Tuesday");
        xAxis.add("Wednesday");
        xAxis.add("Thursday");
        xAxis.add("Friday");
        xAxis.add("Saturday");
        xAxis.add("Sunday");

        yAxis.add(new Entry(1.9f,0));
        yAxis.add(new Entry(1.6f,1));
        yAxis.add(new Entry(2f,2));
        yAxis.add(new Entry(1.8f,3));
        yAxis.add(new Entry(1f,4));
        yAxis.add(new Entry(0.2f,5));
        yAxis.add(new Entry(2f,6));

        String[] xaxes = new String[xAxis.size()];

        for (int i=0; i<xAxis.size(); i++){
            xaxes[i]= xAxis.get(i);
        }

        LineDataSet lineDataSet = new LineDataSet(yAxis,"Litres (L)");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setColor(Color.MAGENTA);

        lineDataSets.add(lineDataSet);

        lineChart.setData(new LineData(xaxes, lineDataSets));
        lineChart.setVisibleXRangeMaximum(50f);
        lineChart.setVisibleXRangeMinimum(5f);
        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
    }

    public void createHydrationLevelChart(LineChart lineChart){

        ArrayList<String> xAxis = new ArrayList<String>();
        ArrayList<Entry> yAxis = new ArrayList<Entry>();
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();

        xAxis.add("Monday");
        xAxis.add("Tuesday");
        xAxis.add("Wednesday");
        xAxis.add("Thursday");
        xAxis.add("Friday");
        xAxis.add("Saturday");
        xAxis.add("Sunday");

        yAxis.add(new Entry(95,0));
        yAxis.add(new Entry(80,1));
        yAxis.add(new Entry(100,2));
        yAxis.add(new Entry(90,3));
        yAxis.add(new Entry(50,4));
        yAxis.add(new Entry(10,5));
        yAxis.add(new Entry(100,6));

        String[] xaxes = new String[xAxis.size()];

        for (int i=0; i<xAxis.size(); i++){
            xaxes[i]= xAxis.get(i);
        }

        LineDataSet lineDataSet = new LineDataSet(yAxis,"Hydration Level (%)");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setColor(Color.BLUE);

        lineDataSets.add(lineDataSet);

        lineChart.setData(new LineData(xaxes, lineDataSets));
        lineChart.setVisibleXRangeMaximum(50f);
        lineChart.setVisibleXRangeMinimum(5f);
        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);

    }

    public float calculateLitresNecessary(String weight){

        float number = Float.valueOf(weight);

        //GET NECESSARY LITRES -> SEND THIS TO BOTTLE!!!!!
        litresNecessary = number * 0.035f;
        return litresNecessary;
    }

    public void writeToCsv(String toWrite) throws IOException {




        //String baseDir = Environment.g
        //System.out.println(baseDir);
        String fileName = "records.csv";
        //String filePath = baseDir + File.separator + fileName;


        //String filePath = csvFile;

        System.out.println(toWrite);
        System.out.println(getApplicationContext().getFilesDir());
        FileWriter mFileWriter = null;
        try
        {
            mFileWriter = new FileWriter(new File(getApplicationContext().getFilesDir(), fileName));
        }
        catch (Exception e) { e.printStackTrace(); }
        CSVWriter writer = new CSVWriter(mFileWriter);

        String [] toWriteOnFile = toWrite.split(",");

        writer.writeNext(toWriteOnFile);

        writer.close();

        String yourFilePath = getApplicationContext().getFilesDir() + "/" + fileName;

        try {
            System.out.println(MainScreen.getStringFromFile(yourFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public String readFromCsv(){
            return "";
    }

    public boolean sendWeight(String weight){

        //SEND w56 example
        System.out.println(weight);



        return true;
    }

    public String getActual(int what){

        if(what == 0){
            //QUER A PERCENTAGEM
            return "50";
        }else{
            //QUER OS LITROS
            return "1,3";
        }
        //SEND a - receive int float
    }

    public String getGoal(){


        //SEND g - receive float
        return "2,4";
    }

    public String getLastOfDay(){
        return "";
    }
}
