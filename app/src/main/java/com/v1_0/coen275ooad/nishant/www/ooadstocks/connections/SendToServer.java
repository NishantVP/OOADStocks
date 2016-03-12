package com.v1_0.coen275ooad.nishant.www.ooadstocks.connections;

import com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell.BuySellRequest;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell.LockedBuySellReq;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishant on 9/3/16.
 */
public class SendToServer {

    private static String TextDataToSend = "NothingNew";
    private static List<BuySellRequest> AllRequestList=  new ArrayList<>();



    public static void setTextDataToSend (String data) {
        TextDataToSend = data;
    }

    public static String getTextDataToSend() {
        return TextDataToSend;

    }

    public static void addToAllRequests (List<BuySellRequest> NewRequestList) {

        for(int i=0; i<NewRequestList.size(); i++) {
            AllRequestList.add(NewRequestList.get(i));
        }

    }
    public static String getRequestDataToSend() {

        BuySellRequest request;

        if(AllRequestList.size() > 0) {
            request = AllRequestList.get(0);
            TextDataToSend = request.getStockName() +","
                                +"888" +","
                                +"Nis" +","
                                +request.getPrice() + ","
                                +request.getQuantity() +","
                                +request.getBuySell();
            AllRequestList.remove(0);
        }
        else {
            TextDataToSend = "NothingNew";
        }
        return TextDataToSend;
    }

}
