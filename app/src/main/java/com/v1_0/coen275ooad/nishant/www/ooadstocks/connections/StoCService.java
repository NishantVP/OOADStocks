package com.v1_0.coen275ooad.nishant.www.ooadstocks.connections;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.Authentication;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.Portfolio;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.Stock;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.TransactionHistory;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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

    private ReceiveDataTask task;

    public static final String ACTION_BROADCAST = StoCService.class.getName() + "Broadcast";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        Toast.makeText(this, "This is from StoCService", Toast.LENGTH_SHORT).show();

        sharedpreferences = getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        serverIP = sharedpreferences.getString("SERVER_IP", "");
        S2CPort = sharedpreferences.getInt("SERVER_TO_CLIENT_PORT", 1000);

        Log.d("S2CPort", Integer.toString(S2CPort));

        new ReceiveDataTask().execute();
        //task.execute();

        return Service.START_NOT_STICKY;
    }

    private class ReceiveDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            System.out.println("Inside do in background");

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

            while (true) {
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
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            //task.cancel(true);
            //System.out.println("Returning done");

            //return "done";
        }
    }

    public User stringToUser(String data)
    {
        String[] fields = data.split("#");
        String[] auth = fields[3].split(",");
        Authentication a = new Authentication(auth[0],auth[1]);

        String[] port = fields[2].split(",");
        double moneyBalance = Double.parseDouble(port[0]);
        port[1] = port[1].replace("{","");
        port[1] = port[1].replace("}","");
        String[] stocks = port[1].split(";");
        List<Stock> portStocks = new ArrayList<Stock>();
        for(String s: stocks)
        {
            if(s.equals("") == false)
            {
                String[] val = s.split(":");
                Stock ob = new Stock(val[0],0,Integer.parseInt(val[1]));
                portStocks.add(ob);
            }
        }
        List<TransactionHistory> t = new ArrayList<TransactionHistory>();
        Portfolio p = new Portfolio(moneyBalance,portStocks,t);

        String[] userFields = fields[1].split(",");
        User u = new User(userFields[0],userFields[1],userFields[2],userFields[3],p,a);
        return u;

    }

    public List<Stock> stringToMarket(String data)
    {
        List<Stock> marketStocks = new ArrayList<Stock>();
        String[] arr = data.split("#");
        String[] stocks = arr[0].split(";");

        for(String s: stocks)
        {
            String[] values = s.split(",");
            Stock stk = new Stock(values[0],Double.parseDouble(values[1]),0);
            marketStocks.add(stk);
        }
        return marketStocks;
    }

    public StoCService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
