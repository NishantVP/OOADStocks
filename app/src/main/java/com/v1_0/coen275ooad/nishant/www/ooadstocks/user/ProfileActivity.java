package com.v1_0.coen275ooad.nishant.www.ooadstocks.user;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.v1_0.coen275ooad.nishant.www.ooadstocks.R;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.connections.ReceivedMessageDecode;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameTV;
    private TextView googleQuantityTV;
    private TextView facebookQuantityTV;
    private TextView amazonQuantityTV;
    private TextView cashValueTV;

    private String googleQuantityString = "0";
    private String facebookQuantityString = "0";
    private String amazonQuantityString = "0";
    private String cashValueString = "0";
    private String Name = "Your Name";


    private  User currentUser;

    private List<Stock> MyStocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        currentUser = ReceivedMessageDecode.getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        googleQuantityTV = (TextView)findViewById(R.id.GoogleOwnedQuantity);
        facebookQuantityTV = (TextView)findViewById(R.id.FacebookOwnedQuantity);
        amazonQuantityTV = (TextView)findViewById(R.id.AmazonOwnedQuantity);
        cashValueTV = (TextView)findViewById(R.id.cashValue);
        nameTV = (TextView)findViewById(R.id.NameText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void refreshProfileClicked (View view) {

        if(currentUser.getName() == null){

            Toast.makeText(this, "Market Closed, Please come back later", Toast.LENGTH_SHORT).show();
        }
        else {
            currentUser = ReceivedMessageDecode.getCurrentUser();

            MyStocks = currentUser.getPortfolio().getStocks();
            cashValueString = Double.toString(currentUser.getPortfolio().getMoneyBalance());

            Name = currentUser.getName();

            for(int i=0;i<MyStocks.size();i++) {
                Stock stock = MyStocks.get(i);

                if(stock.getStockName().equals("google")) {
                    googleQuantityString = Integer.toString(stock.getStockQty());
                }
                else if (stock.getStockName().equals("facebook")){
                    facebookQuantityString = Integer.toString(stock.getStockQty());
                }
                else if(stock.getStockName().equals("amazon")) {
                    amazonQuantityString = Integer.toString(stock.getStockQty());
                }

            }
            cashValueTV.setText(cashValueString);
            googleQuantityTV.setText(googleQuantityString);
            facebookQuantityTV.setText(facebookQuantityString);
            amazonQuantityTV.setText(amazonQuantityString);
            nameTV.setText(Name);
        }

    }

}
