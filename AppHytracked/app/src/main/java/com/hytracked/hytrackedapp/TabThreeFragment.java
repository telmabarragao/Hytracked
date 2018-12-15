package com.hytracked.hytrackedapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabThreeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabThreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabThreeFragment extends TabFragment {

    private static final int SOLICITAR_ATIVACAO = 1;
    private static final int COMUNICAR_GARRAFA = 2;
    private static final int SEND_WEIGHT = 3;
    private static final int ASK_FOR_PROGRESS = 4;
    private static final int MESSAGE_READ = 5;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView nameOutput;
    TextView weightInput;
    String name, weight;

    public TabThreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabThreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabThreeFragment newInstance(String param1, String param2) {
        TabThreeFragment fragment = new TabThreeFragment();
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
        View view =  inflater.inflate(R.layout.fragment_tab_three, container, false);
        Button button = (Button) view.findViewById(R.id.syncbutton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                askForSync(v);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        nameOutput = (TextView) view.findViewById(R.id.nameOutput);
        weightInput = (TextView) view.findViewById(R.id.weight_input);

        if (nameOutput != null)
        {
            nameOutput.setText(mParam1);
        }

        if (weightInput != null)
        {
            weightInput.setText(mParam2+" kg");
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


    public void askForSync(View view)
    {
        /*LayoutInflater inflater = this.getLayoutInflater();
        View setupView = inflater.inflate(R.layout.activity_setup_menu, null);
        Intent intent = new Intent(this, MainMenu.class);*/

        System.out.println("BOTÃO FUNCOU");

        Intent communicateWithBottle = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        MainMenu activity = (MainMenu)getActivity();
        activity.startActivityForResult(communicateWithBottle, SEND_WEIGHT);
        activity.startActivityForResult(communicateWithBottle, ASK_FOR_PROGRESS);


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
