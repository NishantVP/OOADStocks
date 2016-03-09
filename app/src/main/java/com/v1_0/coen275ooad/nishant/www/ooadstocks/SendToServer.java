package com.v1_0.coen275ooad.nishant.www.ooadstocks;

/**
 * Created by nishant on 9/3/16.
 */
public class SendToServer {

    private static String TextDataToSend = "This is from Mobile";


    public static void setTextDataToSend (String data) {
        TextDataToSend = data;

    }
    public static String getTextDataToSend() {

        return TextDataToSend;
    }

}
