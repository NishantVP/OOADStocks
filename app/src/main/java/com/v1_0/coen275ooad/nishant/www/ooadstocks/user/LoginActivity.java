package com.v1_0.coen275ooad.nishant.www.ooadstocks.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.R;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.application.StartScreenActivity;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell.BuySellNewActivity;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.connections.FirstConnectionToServer;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.market.MarketStatusActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameET;
    private EditText passwordET;
    private String userName = "";
    private String password = "";

    private SharedPreferences sharedpreferences;
    private String serverIP;
    private String serverPort;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentUser = new User();


        usernameET = (EditText)findViewById(R.id.username);
        passwordET = (EditText)findViewById(R.id.password);

        sharedpreferences = getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        getServerIPandPORT();

        userName = sharedpreferences.getString("USERNAME", "");
        password = sharedpreferences.getString("PASSWORD", "");

        if(userName.equals("")||password.equals("")) {

        }
        else {
            usernameET.setText(userName);
            passwordET.setText(password);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

                }
            }
        });
    }

    public void startClicked (View view) {
        userName = usernameET.getText().toString();
        password = passwordET.getText().toString();

        if(userName.equals("")||userName.equals("Username")) {
            Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show();
        }
        else if(password.equals("")|| password.equals("Password")) {
            Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show();
        }
        else {

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("USERNAME", userName);
            editor.putString("PASSWORD", password);
            editor.commit();

            Intent i = new Intent(LoginActivity.this, FirstConnectionToServer.class);
            // potentially add data to the intent
            i.putExtra("KEY1", "Value to be used by the service");
            LoginActivity.this.startService(i);

            Intent myIntent = new Intent(LoginActivity.this, MarketStatusActivity.class);
            myIntent.putExtra("key", "nothing"); //Optional parameters
            LoginActivity.this.startActivity(myIntent);
        }

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
            Intent myIntent = new Intent(LoginActivity.this, StartScreenActivity.class);
            myIntent.putExtra("key", "nothing"); //Optional parameters
            LoginActivity.this.startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
