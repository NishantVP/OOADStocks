package com.v1_0.coen275ooad.nishant.www.ooadstocks.connections;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class FirstConnectionToServer extends Service {

    String ip = "nothingYet";
    private Socket client;
    private PrintWriter printwriter;

    private String serverIP;
    private String serverPort;
    private int serverPortInt;
    private String ParseObjID;
    private SharedPreferences sharedpreferences;

    private int S2CPort;
    private int C2SPort;

    public static final String ACTION_BROADCAST = FirstConnectionToServer.class.getName() + "Broadcast";
    //private DataInputStream in;
    //private DataOutputStream out;

    private String userName = "";
    private String password = "";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        sharedpreferences = getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        // Reading from SharedPreferences
        serverIP = sharedpreferences.getString("SERVER_IP", "");
        serverPort = sharedpreferences.getString("SERVER_PORT", "");
        ParseObjID = sharedpreferences.getString("MY_PARSE_OBJ_ID", "");
        userName = sharedpreferences.getString("USERNAME", "Username");
        password = sharedpreferences.getString("PASSWORD", "Password");

        Log.d("SocketService IP ", serverIP);
        Log.d("SocketService Port ",serverPort);
        Log.d("SocketService ObjID ", ParseObjID);

        Toast.makeText(this, "Welcome "+userName, Toast.LENGTH_SHORT).show();

        serverPortInt = Integer.parseInt(serverPort);
        //ip = RunSocketClient();
        StartTheConnectionTask task = new StartTheConnectionTask();
        task.execute();
        return Service.START_NOT_STICKY;
    }


    private class StartTheConnectionTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {

                System.out.println("Inside AsyncTask");

                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress(serverIP, serverPortInt));

                String newUserAuth = userName+","+password;

                ByteBuffer buf = ByteBuffer.allocate(1024);
                ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

                buf.clear();
                buf.put(newUserAuth.getBytes());

                buf.flip();

                while(buf.hasRemaining()) {
                    socketChannel.write(buf);
                }

                // Receive message
                buffer.clear();
                // ...populate the buffer...
                socketChannel.read(buffer);
                buffer.flip(); // flip the buffer for reading
                byte[] bytes = new byte[buffer.remaining()]; // create a byte array the length of the number of bytes written to the buffer
                buffer.get(bytes); // read the bytes that were written
                String packet = new String(bytes);
                System.out.println("Buffer Incoming" + packet);
                sendBroadcastMessage("port " + packet);

                S2CPort = Integer.parseInt(packet);
                C2SPort = S2CPort + 1;

                System.out.println("NewPortsFromServer: " +S2CPort +"," +C2SPort);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("SERVER_TO_CLIENT_PORT", S2CPort);
                editor.putInt("CLIENT_TO_SERVER_PORT", C2SPort);
                editor.commit();

                Intent i = new Intent(FirstConnectionToServer.this, AlternateSendReceiveService.class);
                // potentially add data to the intent
                i.putExtra("KEY1", "Value to be used by the service");
                FirstConnectionToServer.this.startService(i);

                return "done";
            }
            catch (IOException e) {
                return "Not done";
            }
            finally {

            }

        }
    }



    private void sendBroadcastMessage(String messageFromPC) {

        Intent intent = new Intent(ACTION_BROADCAST);
        intent.putExtra("Message", messageFromPC);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }



    public FirstConnectionToServer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
