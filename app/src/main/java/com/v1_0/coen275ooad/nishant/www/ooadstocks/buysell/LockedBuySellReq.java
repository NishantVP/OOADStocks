package com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell;

import com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell.BuySellRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishant on 12/3/16.
 */
public class LockedBuySellReq {

    private static List<BuySellRequest> BuySellRequestList =  new ArrayList<>();

    public static List<BuySellRequest> getBuySellRequestList() {
        return BuySellRequestList;
    }

    public static void RemoveRequest(String stockName) {

        for(int i=0; i<BuySellRequestList.size(); i++) {
            if(BuySellRequestList.get(i).getStockName().equals(stockName)) {
                BuySellRequestList.remove(i);
                break;
            }

        }

    }

    public static void AddRequest(String stockName, String quantity, String price, String buySell) {

        RemoveRequest(stockName);
        BuySellRequestList.add(new BuySellRequest(stockName,quantity,price,buySell));

    }

}
