package com.v1_0.coen275ooad.nishant.www.ooadstocks.connections;

import android.widget.Toast;

import com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell.BuySellRequest;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell.LockedBuySellReq;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishant on 9/3/16.
 */
public class SendToServer {

    private static String TextDataToSend = "NothingNew";
    private static List<BuySellRequest> AllRequestList=  new ArrayList<>();
    private static User currentUser;


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
        currentUser = ReceivedMessageDecode.getCurrentUser();

        if(currentUser.getName() == null){
            TextDataToSend = "NothingNew";
        }
        else if(AllRequestList.size() > 0) {
            request = AllRequestList.get(0);
            TextDataToSend = request.getStockName() +","
                                +currentUser.getID() +","
                                +currentUser.getAuth().getUsername() +","
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
