package com.isopod.wifidirecttestapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    public class WifiDirectConnListener implements WifiP2pManager.ConnectionInfoListener {
        private MainActivity curActivity;

        public WifiDirectConnListener(MainActivity curActivity){
            this.curActivity = curActivity;
        }

        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            if(info == null){
                showMessage("Group info available but group is null", getApplicationContext());
                return;
            }
            showMessage("Group Data is available!", getApplicationContext());
            String curGroupString = "Group: \n";
            curGroupString += "\t" + "groupFormed: " + info.groupFormed + "\n";
            curGroupString += "\t" + "isGroupOwner: " + info.isGroupOwner + "\n";
            curGroupString += "\t" + "groupOwnerAddress: " + info.groupOwnerAddress + "\n";
            if(deviceList == null){
                deviceList = findViewById(R.id.devicelist);
                if(deviceList == null) {
                    showMessage(info.toString(), getApplicationContext());
                    return;
                }
            }
            String oldString = (String) deviceList.getText();
            if(oldString == null)
                oldString = "";
            deviceList.setText(oldString + curGroupString);
            if(info.groupFormed){
                /*TCPSocketThread tcpThread = new TCPSocketThread(info.isGroupOwner, info.groupOwnerAddress, PORT, curActivity);
                tcpThread.start();*/
                // using the tcpSocketManager allows the tcp thread to safely send text to the UI using a handler, but you can also
                //start the thread straight from here if you only require one thread, uncomment the above code and comment the code below to do so
                TCPSocketManager tcpSocketManager = new TCPSocketManager(PORT,info.isGroupOwner, info.groupOwnerAddress, curActivity);
                tcpSocketManager.startSocketThreads();
            }
        }

    }

    public static final int PORT = 4444;
    private Context context;
    private boolean ready = false;
    private WifiManager wifiManager;
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel mChannel;
    private final IntentFilter intentFilter = new IntentFilter();
    private boolean inProgress;
    private TextView deviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        deviceList = findViewById(R.id.devicelist);
        context = getApplicationContext();
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiP2pManager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        showMessage(Looper.getMainLooper().toString(), context);
        mChannel = wifiP2pManager.initialize(context, Looper.getMainLooper(), null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void SearchButtonPressed(View view){
        wifiP2pManager.requestConnectionInfo(mChannel, new WifiDirectConnListener(this));
    }

    public static String showMessage(String message, Context curContext){
        String toReturn = "success";
        try {
            Toast.makeText(curContext, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            toReturn = e.getMessage();
        }
        return toReturn;
    }

    public void addTextToScreen(String messageToAdd){
        if(deviceList == null){
            deviceList = findViewById(R.id.devicelist);
            if(deviceList == null) {
                showMessage(messageToAdd, getApplicationContext());
                return;
            }
        }
        String oldString = (String) deviceList.getText();
        if(oldString == null)
            oldString = "";
        deviceList.setText(oldString + messageToAdd + "\n");
    }

}
