package com.alhoda.shefa;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
//import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.lang.Exception;import java.lang.Override;import java.lang.String;import java.lang.System;import java.lang.UnsupportedOperationException;import java.util.Random;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //@Override
//    public void onStart(final Intent inten,int startid)
//    {
//
//       // Intent in=new Intent();
//        /*Intent intents = new Intent(getBaseContext(),MyActivity.class);
//        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intents);*/
//        //Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
//        //Intent service = new Intent(getApplicationContext(),MyService.class);
//        //getApplicationContext().startService(service);
//        Random random=new Random();
//        String number= String.valueOf(random.nextInt(9)+1);
//        String txt=getNewTxt(number);
//        Toast.makeText(this,txt, Toast.LENGTH_LONG).show();
//        /*Timer myTimer = new Timer();
//        myTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Intent service = new Intent(getApplicationContext(),MyService.class);
//                getApplicationContext().startService(service);
//            }
//        }, 0, 10000);*/
//       ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
//        // This schedule a runnable task every 5 seconds
//        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
//            public void run() {
//                Intent service = new Intent(getApplicationContext(),MyService.class);
//                getApplicationContext().startService(service);
//            }
//        },0,10,TimeUnit.SECONDS);
//
//
//        /*Intent newIntent=new Intent(getApplicationContext(),MyService.class);
//        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
//        SystemClock.sleep(10000);
//        startService(newIntent);*/
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences settingPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean programStatus = settingPrefs.getBoolean("programStatus", true);
        if (programStatus) {
            int delay = settingPrefs.getInt("hoursNum", 1);
            //Get random number
            Random random = new Random();
            String number = String.valueOf(random.nextInt(301) + 1);

            //Select new religion info to display it
            String txt = getNewTxt(number);

            //Send notification to user
            sendNotification(txt);

            //Wake service at specific time
            AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarm.set(
                    alarm.RTC_WAKEUP,
                    System.currentTimeMillis() + (delay * 1000 * 60 * 60), // x hour from now
                    PendingIntent.getService(this, 0, new Intent(this, MyService.class), 0)
            );


//            Intent alarmIntent = new Intent(getApplicationContext(), MyReceiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);
//            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.add(Calendar.SECOND, delay * 60 * 60); // first time
//            long frequancey = delay * 60 * 60 * 1000;
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequancey, pendingIntent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //Select Specific religion data from xml file
    public String getNewTxt(String number) {
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlPullParserFactory.newPullParser();
            //AssetManager assetManager = getBaseContext().getAssets();
            InputStream inputStream = getBaseContext().getAssets().open("shefa.xml");
            parser.setInput(inputStream, null);
            String text = "";
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    if (tagName.equalsIgnoreCase("data")) {
                        int attributeCount = parser.getAttributeCount();
                        for (int i = 0; i < attributeCount; i++) {
                            String name = parser.getAttributeName(i);
                            if (name != null && name.equalsIgnoreCase("id")) {
                                String sonaID = parser.getAttributeValue(i);
                                if (sonaID.equalsIgnoreCase(number)) {
                                    text = parser.nextText();
                                    return
                                            text;

                                }
                            }
                        }
                    }
                }
                eventType = parser.nextToken();
            }

        } catch (Exception ex) {
            return "Exception";

        }
        return "Text";
    }

    //Send notification with certain content to the user
    public void sendNotification(String content) {


        Intent intent = new Intent(getApplicationContext(), NotificationContent.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("content", content);
//        intent.setAction(Intent.ACTION_MAIN);
//         intent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                (int) System.currentTimeMillis(),
                intent,
                0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext()).setSmallIcon(R.drawable.my_logo)
                .setContentTitle("شفاء الروح")
                .setAutoCancel(true)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

