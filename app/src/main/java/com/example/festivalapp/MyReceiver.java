package com.example.festivalapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED"))
        {
            Toast.makeText(context, "Power in OFF", Toast.LENGTH_SHORT).show();
        }
        if(intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED"))
        {
            Toast.makeText(context, "Power in ON", Toast.LENGTH_SHORT).show();
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null)
        {
            Toast.makeText(context, "Internet is not running", Toast.LENGTH_SHORT).show();
        }

    }
}
