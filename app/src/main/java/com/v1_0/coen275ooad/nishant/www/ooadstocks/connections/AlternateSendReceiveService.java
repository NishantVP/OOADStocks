package com.v1_0.coen275ooad.nishant.www.ooadstocks.connections;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell.BuySellRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AlternateSendReceiveService extends Service {
    public AlternateSendReceiveService() {
    }

    private String serverIP;
    private SharedPreferences sharedpreferences;

    private int C2SPort;
    private Socket CtoSSocket;

    private int S2CPort;
    private Socket StoCSocket;

    private OutputStream outStream;
    private InputStream inStream;
    private PrintWriter pWrite;
    private BufferedReader bRead;
    private String receivedMessage;
    private  SendDataTask task;

    public static final String ACTION_BROADCAST = C2SService.class.getName() + "Broadcast";
    private static final String NO_NEW_DATA = "noUpdates";
    private String newData = NO_NEW_DATA;

    //private List<BuySellRequest> BuySellRequestList; //=  new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        Toast.makeText(this, "This is from StoCService", Toast.LENGTH_SHORT).show();

        sharedpreferences = getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        serverIP = sharedpreferences.getString("SERVER_IP", "");
        C2SPort = sharedpreferences.getInt("CLIENT_TO_SERVER_PORT", 1000);
        S2CPort = sharedpreferences.getInt("SERVER_TO_CLIENT_PORT", 1000);

        Log.d("C2SPort ", Integer.toString(C2SPort));

        //BuySellRequestList =  new ArrayList<>();

        new SendDataTask().execute();
        //System.out.println("SendTask Created");
        //task.execute();

        return Service.START_NOT_STICKY;
    }

    private class SendDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            System.out.println("Started SendDataTask");


            try {
                StoCSocket = new Socket(serverIP, S2CPort);  //connect to server
                inStream = StoCSocket.getInputStream();
                bRead = new BufferedReader(new InputStreamReader(inStream));
            } catch (IOException e) {
                e.printStackTrace();
            }



            while(true) {


                try {
                    CtoSSocket = new Socket(serverIP, C2SPort);  //connect to server
                    outStream = CtoSSocket.getOutputStream();
                    pWrite = new PrintWriter(outStream, true);
                    //inStream = CtoSSocket.getInputStream();
                    //bRead = new BufferedReader(new InputStreamReader(inStream));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Log.d("ClientApp", "Started");

                String RequestToSend = SendToServer.getRequestDataToSend();

                System.out.println(RequestToSend);
                pWrite.println(RequestToSend);
                pWrite.flush();

                try {
                    if ((receivedMessage = bRead.readLine()) == null) {
                        System.out.println("Waiting for new message from Server ..");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Message from Server: " + receivedMessage);
                        receivedMessage = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {
            //showDialog("Downloaded " + result + " bytes");

        }
    }

    public void SendDataToServer (String newMessage) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
