package com.alhoda.shefa;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;import java.lang.Integer;import java.lang.Override;

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        boolean running = false;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MyService.class.getName().equals(service.service.getClassName())) {
                running = true;
                break;
            }
        }
        if (!running) {
            Intent intentService = new Intent(context, MyService.class);
            context.startService(intentService);
        }
        //Intent myIntent1=new Intent(context,NotificationContent.class);
    }
}
