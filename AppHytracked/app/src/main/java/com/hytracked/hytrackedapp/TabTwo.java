package com.hytracked.hytrackedapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;




public class TabTwo extends Fragment {

    LineChart lineChart, lineChart2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_two, container, false);
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        lineChart2 = (LineChart) view.findViewById(R.id.lineChart2);

        createHydrationLevelChart(lineChart);
        createLitresChart(lineChart2);
        return view;
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
            xaxes[i]= xAxis.get(i).toString();
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
            xaxes[i]= xAxis.get(i).toString();
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
}
