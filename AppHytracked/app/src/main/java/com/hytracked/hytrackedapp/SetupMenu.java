package com.hytracked.hytrackedapp;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SetupMenu extends AppCompatActivity {


    EditText weight, name, activityLvl;
    float litresNecessary;
    String csv = "../../../../../database/records.csv";
    View view;
    File fileDir;
    File personalfile;

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
        Intent intent = new Intent(this, MainMenu.class);

        // GET NAME
        // GET WEIGHT NUMBER
        String theName = name.getText().toString();
        String theWeight = weight.getText().toString();

        //CHECK IF FILE EXISTS; IF NOT -> write to file
        try {
            createCSVFile();
            if(readInfoFromCSV()){
                System.out.println("DEU TRUE -> UPDATE");
                updateInfoToCSV();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


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

    public void createCSVFile() throws IOException {
        //FileWriter(new File(getApplicationContext().getFilesDir(), fileName))
        fileDir = new File(getApplicationContext().getFilesDir()+ File.separator +"database");
        if(!fileDir.exists()) {
            try {
                fileDir.mkdir();
                System.out.println("estou a fazer a dir");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
            personalfile = new File(getApplicationContext().getFilesDir()+ File.separator+"database"+ File.separator +"PersonalInfo.csv");
            if(!personalfile.exists()){
                try {
                    personalfile.createNewFile();
                    System.out.println("estou a criar o "+ personalfile.getAbsolutePath());


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                System.out.println("o ficheiro já existia "+ personalfile.getAbsolutePath());

            }



    }


    public void updateInfoToCSV() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(personalfile));
        List<String[]> data = new ArrayList<String[]>();
        data.add(new String[] {name.getText().toString()});
        data.add(new String[] {weight.getText().toString()});
        writer.writeAll(data);
        writer.close();

        sendInfoToBottle(weight.getText().toString());
    }


    public boolean readInfoFromCSV() throws IOException {

        CSVReader reader = new CSVReader(new FileReader(personalfile));
        String[] row = null;
        List content = reader.readAll();
        String nameInFile = "";
        String weightInFile = "";
        int index = 0;


        for (Object object : content) {
            row = (String[]) object;

            if(index==0){
                //E O NOME
                nameInFile = row[0];
                index+=1;
            }else{
                weightInFile = row[0];
            }


        }

        if(nameInFile.equals(name.getText().toString())){

            if(weightInFile.equals(weight.getText().toString())){
                //não faz nada
                reader.close();

                return false;
            }
            else {
                //escreve no ficheiro
                reader.close();

                return true;

            }
        }else{
            //escreve no ficheiro
            System.out.println("VOU FAZER UPDATE");
            reader.close();

            return true;

        }


    }

    public void sendInfoToBottle(String info) throws IOException {
        //SO ENVIA O PESO!! row[1]
        String[] row = null;
        CSVReader reader = new CSVReader(new FileReader(personalfile));
        List content = reader.readAll();

        int index = 0;


        for (Object object : content) {
            row = (String[]) object;

            if(index==0){
                //E O NOME logo nao interessa
                index+=1;
            }else{
                //AQUI E PARA MANDAR O PESO
                System.out.println(row[0]);
            }


        }
    }

}
