package com.hytracked.hytrackedapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainMenu extends AppCompatActivity implements TabFragment.OnFragmentInteractionListener
{

    private static final int SOLICITAR_ATIVACAO = 1;
    private static final int COMUNICAR_GARRAFA = 2;
    private static final int MESSAGE_READ = 3;

    TabThree tab3;
    TextView nameOutput;
    BluetoothAdapter myBluetoothAdapter = null;
    BluetoothDevice myDevice = null;
    BluetoothSocket mySocket = null;

    ConnectedThread connectedThread;
    Handler mHandler;
    StringBuilder dadosBluetooth = new StringBuilder();

    UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private static String addr = null;

    String litresDrunkReceived, percentageReceived;
    String name, weight;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(myBluetoothAdapter ==null){
            Toast.makeText(getApplicationContext(), "Nao tens bluetooth", Toast.LENGTH_LONG).show();
        }
        else if (!myBluetoothAdapter.isEnabled()){
            Intent ativarBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativarBluetooth, SOLICITAR_ATIVACAO);
        }
        else {
            Intent comunicarGarrafa = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(comunicarGarrafa, COMUNICAR_GARRAFA);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("HandlerLeak")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){

            case SOLICITAR_ATIVACAO:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(), "Bluetooth Ativado!!", Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(getApplicationContext(), "Bluetooth não foi ativado :(, a fechar aplicacao...", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case COMUNICAR_GARRAFA:
                if(resultCode == Activity.RESULT_OK){
                    addr = "00:06:66:EB:F5:C3";
                    myDevice = myBluetoothAdapter.getRemoteDevice(addr);

                    try{
                        mySocket = myDevice.createRfcommSocketToServiceRecord(myUUID);
                        mySocket.connect();


                        Toast.makeText(getApplicationContext(), "Conectado ao: "+addr, Toast.LENGTH_LONG).show();
                    } catch(IOException error){
                        Toast.makeText(getApplicationContext(), "Deu merda: "+error, Toast.LENGTH_LONG).show();
                    }
                    connectedThread = new ConnectedThread(mySocket);
                    connectedThread.start();

                    connectedThread.sendToBottle("w"+weight);
                    connectedThread.sendToBottle("\n");
                    connectedThread.sendToBottle("a");



                    // Toast.makeText(getApplicationContext(), "MAC Address: "+addr, Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(getApplicationContext(), "Falha ao obter MAC Address", Toast.LENGTH_LONG).show();
                }
        }
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                if(msg.what == MESSAGE_READ){

                    String received = (String)msg.obj;

                    int infoSize = received.length();

                    String[] finalData = received.split("\\s+");

                    System.out.println(received);
                    System.out.println(finalData);
                    if (infoSize>0){

                        percentageReceived = finalData[0];
                        litresDrunkReceived = finalData[1];
                    }

                }
                dadosBluetooth.delete(0,dadosBluetooth.length());

            }

        };

    }




        @Override
    public void onFragmentInteraction(Uri uri)
    {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
            TextView textView = rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.

            Intent intent = getIntent();
            name = intent.getStringExtra("NAME");
            weight = intent.getStringExtra("WEIGHT");

            Fragment fragment = null;

            switch (position)
            {
                case 0:
                    fragment = TabOneFragment.newInstance(percentageReceived, weight, litresDrunkReceived);
                    break;
                case 1:
                    fragment = TabTwoFragment.newInstance(name, weight);
                    break;
                case 2:
                    fragment = TabThreeFragment.newInstance(name, weight);
                    break;
                default: return null;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }



    }

    private class ConnectedThread extends Thread {

        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {

            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    String dadosBT = new String(buffer, 0, bytes);

                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, dadosBT).sendToTarget();

                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void sendToBottle(String input) {
            try {
                byte[] msgBuffer = input.getBytes();
                mmOutStream.write(msgBuffer);
            } catch (IOException e) { }
        }


    }


}
