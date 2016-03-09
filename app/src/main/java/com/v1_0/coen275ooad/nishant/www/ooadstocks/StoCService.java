package com.v1_0.coen275ooad.nishant.www.ooadstocks;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class StoCService extends Service {

    private String serverIP;
    private SharedPreferences sharedpreferences;
    private int S2CPort;

    private Socket StoCSocket;

    private OutputStream outStream;
    private InputStream inStream;
    private PrintWriter pWrite;
    private BufferedReader bRead;
    private String receivedMessage;

    public static final String ACTION_BROADCAST = FirstConnectionToServer.class.getName() + "Broadcast";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        Toast.makeText(this, "This is from StoCService", Toast.LENGTH_SHORT).show();

        sharedpreferences = getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        serverIP = sharedpreferences.getString("SERVER_IP", "");
        S2CPort = sharedpreferences.getInt("SERVER_TO_CLIENT_PORT", 1000);

        Log.d("SocketService IP ", Integer.toString(S2CPort));



        StartTheConnectionTask task = new StartTheConnectionTask();
        task.execute();

        return Service.START_NOT_STICKY;
    }

    private class StartTheConnectionTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            try {
                StoCSocket = new Socket(serverIP, S2CPort);  //connect to server
                outStream = StoCSocket.getOutputStream();
                pWrite = new PrintWriter(outStream, true);
                inStream = StoCSocket.getInputStream();
                bRead = new BufferedReader(new InputStreamReader(inStream));

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("ClientApp", "Started");

            while(true) {
                try {
                    if((receivedMessage = bRead.readLine()).equals(null))  {
                        System.out.println("Waiting for new message from Server ..");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else {
                        System.out.println("Message from Server: " +receivedMessage);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    public StoCService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
