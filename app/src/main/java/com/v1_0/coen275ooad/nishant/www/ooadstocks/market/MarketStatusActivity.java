package com.v1_0.coen275ooad.nishant.www.ooadstocks.market;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.v1_0.coen275ooad.nishant.www.ooadstocks.R;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.application.StartScreenActivity;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell.BuySellNewActivity;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.connections.ReceivedMessageDecode;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.ProfileActivity;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.Stock;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.User;

import junit.framework.TestCase;

import java.util.List;

public class MarketStatusActivity extends AppCompatActivity {

    private static List<Stock> MarketStocks;
    private static User currentUser;

    private TextView googlePriceTV;
    private String googlePriceString = "100";

    private TextView facebookPriceTV;
    private String facebookPriceString = "100";

    private TextView amazonPriceTV;
    private String amazonPriceString = "100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        googlePriceTV = (TextView)findViewById(R.id.GoogleValue);
        facebookPriceTV = (TextView)findViewById(R.id.FacebookValue);
        amazonPriceTV = (TextView)findViewById(R.id.AmazonValue);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void refreshButtonClicked (View view) {
        MarketStocks = ReceivedMessageDecode.getMarketStocks();

        for(int i=0;i<MarketStocks.size();i++) {
            Stock stock = MarketStocks.get(i);

            if(stock.getStockName().equals("google")) {
                googlePriceString = Double.toString(stock.getUnitPrice());
            }
            else if (stock.getStockName().equals("facebook")){
                facebookPriceString = Double.toString(stock.getUnitPrice());
            }
            else if(stock.getStockName().equals("amazon")) {
                amazonPriceString = Double.toString(stock.getUnitPrice());
            }

        }
        googlePriceTV.setText(googlePriceString);
        facebookPriceTV.setText(facebookPriceString);
        amazonPriceTV.setText(amazonPriceString);


    }

    public void buySellClicked (View view) {
        Intent myIntent = new Intent(MarketStatusActivity.this, BuySellNewActivity.class);
        myIntent.putExtra("key", "nothing"); //Optional parameters
        MarketStatusActivity.this.startActivity(myIntent);
    }

    public void viewProfileClicked (View view) {
        Intent myIntent = new Intent(MarketStatusActivity.this, ProfileActivity.class);
        myIntent.putExtra("key", "nothing"); //Optional parameters
        MarketStatusActivity.this.startActivity(myIntent);
    }


}
