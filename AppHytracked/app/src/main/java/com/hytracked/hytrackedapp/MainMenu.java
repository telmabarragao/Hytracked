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

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainMenu extends AppCompatActivity implements TabFragment.OnFragmentInteractionListener
{

    private static final int SOLICITAR_ATIVACAO = 1;
    private static final int COMUNICAR_GARRAFA = 2;
    private static final int SEND_WEIGHT = 3;
    private static final int ASK_FOR_PROGRESS = 4;
    private static final int MESSAGE_READ = 5;

    TextView nameOutput;
    TextView hydrationPercentageText = null;
    TextView litresText = null;
    BluetoothAdapter myBluetoothAdapter = null;
    BluetoothDevice myDevice = null;
    BluetoothSocket mySocket = null;

    ConnectedThread connectedThread;
    Handler mHandler;
    StringBuilder dadosBluetooth = new StringBuilder();

    File recordsfile;
    File fileDir;

    UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private static String addr = "00:06:66:EB:F5:C3";

    String messageFromBT = "";
    String litresDrunkReceived = "0", percentageReceived = "0";
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(myBluetoothAdapter == null)
        {
            Toast.makeText(getApplicationContext(), "Can't get bluetooth adapter", Toast.LENGTH_LONG).show();
        }
        else
        {
            if (!myBluetoothAdapter.isEnabled())
            {
                Intent ativarBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(ativarBluetooth, SOLICITAR_ATIVACAO);
            }

            if (myBluetoothAdapter.isEnabled())
            {
                myDevice = myBluetoothAdapter.getRemoteDevice(addr);
                Intent comunicarGarrafa = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(comunicarGarrafa, COMUNICAR_GARRAFA);
            }
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


        //CHECKTIME
        String totalOfDay = getActualToFile(1);

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
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case SOLICITAR_ATIVACAO:
                if(resultCode == Activity.RESULT_OK)
                {
                    Toast.makeText(getApplicationContext(), "Bluetooth Activated", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Bluetooth couldn't be activated, can't sync data at the moment...", Toast.LENGTH_LONG).show();
                    //finish();
                }
                break;
            case COMUNICAR_GARRAFA:
                if(resultCode == Activity.RESULT_OK)
                {
                    if (checkConnectionVariables())
                    {
                        checkConnectedThread();

                        Intent intent = getIntent();
                        name = intent.getStringExtra("NAME");
                        weight = intent.getStringExtra("WEIGHT");

                        connectedThread.sendToBottle("w" + weight + "#");
                        connectedThread.sendToBottle("a#");
                    }


                    // Toast.makeText(getApplicationContext(), "MAC Address: "+addr, Toast.LENGTH_LONG).show();
                }
                else
                {
                    // Toast.makeText(getApplicationContext(), "Falha ao obter MAC Address", Toast.LENGTH_LONG).show();
                }
                break;
            case SEND_WEIGHT:
                if(resultCode == Activity.RESULT_OK)
                {
                    if (checkConnectionVariables())
                    {
                        checkConnectedThread();

                        System.out.println("WEIGHT_SEND: " + weight);
                        connectedThread.sendToBottle("w" + weight + "#");
                    }


                    // Toast.makeText(getApplicationContext(), "MAC Address: "+addr, Toast.LENGTH_LONG).show();
                }
                else
                {
                    // Toast.makeText(getApplicationContext(), "Falha ao obter MAC Address", Toast.LENGTH_LONG).show();
                }
                break;
            case ASK_FOR_PROGRESS:
                if(resultCode == Activity.RESULT_OK)
                {
                    if (checkConnectionVariables())
                    {
                        checkConnectedThread();

                        connectedThread.sendToBottle("a#");
                    }

                    // Toast.makeText(getApplicationContext(), "MAC Address: "+addr, Toast.LENGTH_LONG).show();
                }
                else
                {
                    // Toast.makeText(getApplicationContext(), "Falha ao obter MAC Address", Toast.LENGTH_LONG).show();
                }
                break;
        }
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                if(msg.what == MESSAGE_READ)
                {
                    String received = (String)msg.obj;

                    int infoSize = received.length();

                    for (int i = 0; i < received.length(); i++)
                    {
                        if (received.charAt(i) != '\n') {
                            messageFromBT += received.charAt(i);
                        }

                        if (received.charAt(i) == '#')
                        {
                            //System.out.println("FINAL DATA: " + messageFromBT);

                            String[] finalData = messageFromBT.split("\\s+");

                            if (finalData.length > 2)
                            {
                                finalData[0] = finalData[1];
                                finalData[1] = finalData[2];
                            }
                            percentageReceived = finalData[0].substring(1);
                            litresDrunkReceived = finalData[1].substring(1,finalData[1].length() - 2);

                            //for (int b = 0; b < finalData.length; b++) {System.out.println("SPLITTED: " + finalData[b]);}

                            System.out.println("PERCENTAGE: " + percentageReceived);
                            System.out.println("LITRES: " + litresDrunkReceived);

                            if (requestCode == COMUNICAR_GARRAFA)
                            {
                                List<Fragment> test = getSupportFragmentManager().getFragments();

                                if (test.size() > 0)
                                {
                                    if (test.get(0).getClass() == TabOneFragment.class)
                                    {
                                        TabOneFragment temp = (TabOneFragment) test.get(0);
                                        if (temp.litresdOutput != null)
                                        {
                                            temp.litresdOutput.setText(litresDrunkReceived + "L Drank");
                                        }
                                        if (temp.hidlevelOutput != null)
                                        {
                                            temp.hidlevelOutput.setText(percentageReceived + " %");
                                        }
                                    }
                                }
                            }


                            messageFromBT = "";
                        }
                    }

                }
                dadosBluetooth.delete(0,dadosBluetooth.length());

            }

        };

    }

    private boolean checkConnectionVariables()
    {
        if (myDevice != null)
        {
            if(mySocket != null)
            {
                if (mySocket.isConnected())
                {
                    return true;
                }
                else
                {
                    try
                    {
                        mySocket.connect();
                        return true;
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    return false;
                }
            }
            else
            {
                try
                {
                    mySocket = myDevice.createRfcommSocketToServiceRecord(myUUID);
                    mySocket.connect();

                    Toast.makeText(getApplicationContext(), "Connected to: " + addr, Toast.LENGTH_LONG).show();

                    return true;
                }
                catch (Exception e) { e.printStackTrace(); }

                return false;
            }
        }
        else
        {
            myDevice = myBluetoothAdapter.getRemoteDevice(addr);
            if (myDevice != null)
            {
                if(mySocket != null)
                {
                    if (mySocket.isConnected())
                    {
                        return true;
                    }
                    else
                    {
                        try
                        {
                            mySocket.connect();
                            return true;
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        return false;
                    }
                }
                else
                {
                    try
                    {
                        mySocket = myDevice.createRfcommSocketToServiceRecord(myUUID);
                        mySocket.connect();

                        Toast.makeText(getApplicationContext(), "Connected to: " + addr, Toast.LENGTH_LONG).show();

                        return true;
                    }
                    catch (Exception e) { e.printStackTrace(); }

                    return false;
                }
            }
            else
            {
                return false;
            }
        }
    }

    private void checkConnectedThread()
    {
        if (connectedThread == null)
        {
            connectedThread = new ConnectedThread(mySocket);
            connectedThread.start();
        }
        else if (!connectedThread.isAlive())
        {
            connectedThread.start();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

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

    public class ConnectedThread extends Thread
    {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {

            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            mmSocket = socket;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run()
        {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true)
            {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    String btData = new String(buffer, 0, bytes);

                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, btData).sendToTarget();

                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel()
        {
            try {
                mmSocket.close();
            } catch (IOException e) { e.printStackTrace(); }
        }

        /* Call this from the main activity to send data to the remote device */
        public void sendToBottle(String input)
        {
            System.out.println(input);
            try {
                byte[] msgBuffer = input.getBytes();
                mmOutStream.write(msgBuffer);
            } catch (IOException e) { e.printStackTrace(); }
        }
    }


    public void writeTotalInRecordCSV(String day, String total) throws IOException {

        createRecordsCSVFile();
        //ESCREVER TOTAL DO DIA NO CSV

        CSVWriter writer = new CSVWriter(new FileWriter(recordsfile));
        List<String[]> data = new ArrayList<String[]>();

        //ADICIONAR DATA E TOTAL

        data.add(new String[] {day , total});
        writer.writeAll(data);
        writer.close();

    }


    public void createRecordsCSVFile() throws IOException {

        fileDir = new File(getApplicationContext().getFilesDir()+ File.separator +"database");
        if(!fileDir.exists()) {
            try {
                fileDir.mkdir();
                System.out.println("estou a fazer a dir");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        recordsfile = new File(getApplicationContext().getFilesDir()+ File.separator+"database"+ File.separator +"RecordsInfo.csv");
        if(!recordsfile.exists()){
            try {
                recordsfile.createNewFile();
                System.out.println("estou a criar o "+ recordsfile.getAbsolutePath());


            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            System.out.println("o ficheiro já existia "+ recordsfile.getAbsolutePath());

        }



    }


    public String getActualToFile(int what)
    {
        if(what == 0){
            //QUER A PERCENTAGEM
            return "50";
        }else{
            //QUER OS LITROS
            return "2";
        }
        //SEND a - receive int float
    }
}
