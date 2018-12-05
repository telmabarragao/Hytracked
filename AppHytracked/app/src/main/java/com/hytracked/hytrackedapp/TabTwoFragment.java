package com.hytracked.hytrackedapp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabTwoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabTwoFragment extends TabFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    LineChart lineChart, lineChart2;

    public TabTwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabTwoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabTwoFragment newInstance(String param1, String param2) {
        TabTwoFragment fragment = new TabTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_two, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        lineChart2 = (LineChart) view.findViewById(R.id.lineChart2);

        if (lineChart != null)
        {
            createHydrationLevelChart(lineChart);
        }

        if (lineChart2 != null)
        {
            createLitresChart(lineChart2);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
