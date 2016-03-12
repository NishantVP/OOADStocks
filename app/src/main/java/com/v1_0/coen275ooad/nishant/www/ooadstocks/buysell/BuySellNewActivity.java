package com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.v1_0.coen275ooad.nishant.www.ooadstocks.R;

import java.util.ArrayList;
import java.util.List;

public class BuySellNewActivity extends AppCompatActivity {

    private List<StockBuySell> stocksList;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv = (RecyclerView)findViewById(R.id.stocksrv);



        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        initializeData();
        initializeAdapter();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

               /* for(int i=0; i<LockedBuySellReq.getBuySellRequestList().size(); i++) {
                    System.out.println("Request Lists" +LockedBuySellReq.getBuySellRequestList().get(i));
                }*/
            }
        });
    }

    private void initializeData(){
        System.out.println("Inside Init Data");
        stocksList = new ArrayList<>();
        stocksList.add(new StockBuySell("Google", "100"));
        stocksList.add(new StockBuySell("Facebook", "100"));
        stocksList.add(new StockBuySell("Amazon", "100"));
    }

    private void initializeAdapter(){
        System.out.println("Inside Init Adapter");
        StockBuySellRVAdapter adapter = new StockBuySellRVAdapter(stocksList);
        rv.setAdapter(adapter);
    }


}
