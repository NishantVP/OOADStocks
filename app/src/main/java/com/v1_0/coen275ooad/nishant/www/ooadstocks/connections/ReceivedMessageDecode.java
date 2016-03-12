package com.v1_0.coen275ooad.nishant.www.ooadstocks.connections;

import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.Authentication;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.Portfolio;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.Stock;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.TransactionHistory;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishant on 12/3/16.
 */
public class ReceivedMessageDecode {

    private static User currentUser =  new User();
    private static List<Stock> MarketStocks = new ArrayList<Stock>();

    public static synchronized void updateCurrentUser ( User user) {
        currentUser = user;
    }
    public static synchronized void updateMarket (List<Stock> stocklist) {
        MarketStocks = stocklist;
    }


    public static synchronized User stringToUser(String data)
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

    public static synchronized List<Stock> stringToMarket(String data)
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
}
