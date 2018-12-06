package com.hytracked.hytrackedapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabOneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabOneFragment extends TabFragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    private OnFragmentInteractionListener mListener;

    TextView nameOutput;
    TextView weightOutput;
    TextView litresgoalOutput;
    TextView litresdOutput;
    TextView hidlevelOutput;

    public TabOneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabOneFragment newInstance(String param1, String param2, String param3) {
        TabOneFragment fragment = new TabOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tab_one, container, false);
        View rootView = inflater.inflate(R.layout.fragment_tab_one, container, false);
        /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(mParam1);*/
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        litresgoalOutput = (TextView) view.findViewById(R.id.goal);
        litresdOutput = (TextView) view.findViewById(R.id.Litres);
        hidlevelOutput = (TextView) view.findViewById(R.id.percentage);

        if (litresgoalOutput != null)
        {
            litresgoalOutput.setText("Goal: " +String.valueOf(round(calculateLitresNecessary(mParam2),2)) + "L");
        }

        if (litresdOutput != null)
        {
            //GET ACTUAL litres dranked from BOTTLE and set in view
            litresdOutput.setText(getActual(1) + "L Drank");
        }
        if (hidlevelOutput != null)
        {
            //GET ACTUAL litres dranked from BOTTLE and set in view
            hidlevelOutput.setText(getActual(0) + "%");
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


    public float calculateLitresNecessary(String weight){

        float number = Float.valueOf(weight);

        //TODO: GET NECESSARY LITRES -> SEND THIS TO BOTTLE!!!!!
        float litresNecessary;
        litresNecessary = number * 0.035f;
        return litresNecessary;
    }


    public String getActual(int what){

        if(what == 0){
            //QUER A PERCENTAGEM
            return mParam1;
        }else{
            //QUER OS LITROS
            return mParam3;
        }
        //SEND a - receive int float
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
    /*public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }
}
