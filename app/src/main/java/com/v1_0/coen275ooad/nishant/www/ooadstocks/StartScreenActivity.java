package com.v1_0.coen275ooad.nishant.www.ooadstocks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class StartScreenActivity extends AppCompatActivity {

    private SharedPreferences sharedpreferences;
    private String serverIP;
    private String serverPort;

    private TextView ServerIPTxtV;
    private TextView ServerPORTTxtV;
    private TextView newCommunicationPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ServerIPTxtV = (TextView) findViewById(R.id.serverIPTextView);
        ServerPORTTxtV = (TextView) findViewById(R.id.serverPORTTextView);
        newCommunicationPort = (TextView) findViewById(R.id.newPortTextView);

        /*//Parse Test
        ParseObject testObject = new ParseObject("TestObjectOOAD");
        testObject.put("foo", "bar2");
        testObject.saveInBackground();
        */

        sharedpreferences = getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);

        getServerIPandPORT();

    }

    private void getServerIPandPORT() {

        //Get the IP address and PORT of the Server's always on listening socket
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ServerIP");
        query.whereEqualTo("Running", "true");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("MainActivity ", "The getFirst request failed.");
                } else {
                    Log.d("MainActivity ", "Retrieved the object.");
                    serverIP = object.getString("IP");
                    serverPort = object.getString("PORT");
                    Log.d("MainActivity IP ", serverIP);
                    Log.d("MainActivity Port ", serverPort);

                    //Save this IP and PORT Locally
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("SERVER_IP", serverIP);
                    editor.putString("SERVER_PORT", serverPort);
                    editor.commit();

                    //Update Views
                    ServerIPTxtV.setText("Server IP:" +serverIP);
                    ServerPORTTxtV.setText("Default PORT:" +serverPort);
                    newCommunicationPort.setText("New PORT:");

                    //Start a Service which will initiate the communication wiht the server in background
                    // use this to start and trigger a service
                    Intent i = new Intent(StartScreenActivity.this, FirstConnectionToServer.class);
                    // potentially add data to the intent
                    i.putExtra("KEY1", "Value to be used by the service");
                    StartScreenActivity.this.startService(i);
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
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
}
