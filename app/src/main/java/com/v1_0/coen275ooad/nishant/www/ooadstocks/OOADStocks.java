package com.v1_0.coen275ooad.nishant.www.ooadstocks;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;

/**
 * Created by nishant on 8/3/16.
 */
public class OOADStocks extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, ParseKeys.ParseKey1, ParseKeys.ParseKey2);
        // Note: I have declared string constants in a class named ParseKeys.
        // This class is not shared on GitHub for Maintaining the privacy
        // Add your Parse ApplicationID and Client Key there

        Log.d("ApplicationClass", "Parse initialized");
    }


}
