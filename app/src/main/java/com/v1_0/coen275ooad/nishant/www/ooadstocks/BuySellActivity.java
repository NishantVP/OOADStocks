package com.v1_0.coen275ooad.nishant.www.ooadstocks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.awt.font.TextAttribute;

public class BuySellActivity extends AppCompatActivity {

    private RadioButton googleBuyRB;
    private RadioButton googleSellRB;
    private RadioButton facebookBuyRB;
    private RadioButton facebookSellRB;
    private RadioButton amazonBuyRB;
    private RadioButton amazonSellRB;

    private EditText googlePriceET;
    private EditText facebookPriceET;
    private EditText amazonPriceET;

    private TextView googleMarketValueTV;
    private TextView facebookMarketValueTV;
    private TextView amazonMarketValueTV;

    private TextView googleUnitsOwnedTV;
    private TextView facebookUnitsOwnedTV;
    private TextView amazonUnitsOwnedTV;

    private String RequestToBeSent = " ";

    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        googleMarketValueTV = (TextView)findViewById(R.id.googleValue);
        facebookMarketValueTV = (TextView)findViewById(R.id.facebookValue);
        amazonMarketValueTV = (TextView)findViewById(R.id.amazonValue);

        googleUnitsOwnedTV = (TextView)findViewById(R.id.googleOwner);
        facebookUnitsOwnedTV = (TextView)findViewById(R.id.amazonUnits);
        amazonUnitsOwnedTV = (TextView)findViewById(R.id.facebookUnits);

        googlePriceET = (EditText)findViewById(R.id.googlePrice);
        facebookPriceET = (EditText)findViewById(R.id.facebookPrice);
        amazonPriceET = (EditText)findViewById(R.id.amazonPrice);

        googleBuyRB = (RadioButton)findViewById(R.id.googleBuy);
        googleSellRB = (RadioButton)findViewById(R.id.googleSell);
        facebookBuyRB = (RadioButton)findViewById(R.id.facebookBuy);
        facebookSellRB = (RadioButton)findViewById(R.id.facebookSell);
        amazonBuyRB = (RadioButton)findViewById(R.id.amazonBuy);
        amazonSellRB = (RadioButton)findViewById(R.id.amazonSell);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void requestClicked (View view) {

        if(googleBuyRB.isChecked()) {

        }
        //Save this IP and PORT Locally
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("REQUEST", RequestToBeSent);

        editor.commit();
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

            Intent myIntent = new Intent(BuySellActivity.this, StartScreenActivity.class);
            myIntent.putExtra("key", "nothing"); //Optional parameters
            BuySellActivity.this.startActivity(myIntent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
