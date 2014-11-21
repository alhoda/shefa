package com.alhoda.shefa;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.lang.Exception;import java.lang.Math;import java.lang.Override;import java.lang.String;import java.util.List;


public class NotificationContent extends Activity {
    private GestureDetector gestureDetector;
    private ShareActionProvider mShareActionProvider;
    Intent shareIntent;
    List<ResolveInfo> activityList;
    String data;


//    @Override
//    protected void onPause() {
//        if (!this.isFinishing()) {
//            finish();
//        }
//        super.onPause();
//    }

    TextView text;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notification_content);
            shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            text = (TextView) findViewById(R.id.notifyContent);

            Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Cocon.otf");
            text.setTypeface(typeFace);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String value = extras.getString("content");
                text.setText(value);
            }
            gestureDetector = new GestureDetector(this, new SwipeGestureDetector());
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG
            );
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification_content, menu);
        MenuItem item = menu.findItem(R.id.share);
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        PackageManager pm = this.getPackageManager();
        activityList = pm.queryIntentActivities(shareIntent, 0);
////
//        int j = 0;
//        try {
//            for (final ResolveInfo app : activityList) {
//                try {
//                    if ((app.activityInfo.name).contains("facebook")) {
//                        activityList.remove(i);
//                        break;
//                    }
//                    j++;
//                } catch (Exception ex) {
//                    Log.i("ex", ex.getMessage());
//
//                }
//            }
//
//        } catch (Exception ex) {
//            Log.i("exception", ex.getMessage());
//        }
//

        shareIntent.putExtra(Intent.EXTRA_TEXT, " #تطبيق_شفاء_الروح " + text.getText());
        mShareActionProvider.setShareIntent(shareIntent);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class SwipeGestureDetector
            extends GestureDetector.SimpleOnGestureListener {
        // Swipe properties, you can change it to make the swipe
        // longer or shorter and speed
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {

            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onLeftSwipe();
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onRightSwipe();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

    }

    private void onRightSwipe() {
        if (i != 302) {
            i++;
            text.setText(getNewTxt(String.valueOf(i)));
            shareIntent.putExtra(Intent.EXTRA_TEXT, " #تطبيق_شفاء_الروح " + text.getText());
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private void onLeftSwipe() {
        if (i > 1) {
            i--;
            text.setText(getNewTxt(String.valueOf(i)));
            shareIntent.putExtra(Intent.EXTRA_TEXT, " #تطبيق_شفاء_الروح " + text.getText());
            mShareActionProvider.setShareIntent(shareIntent);

        }
    }

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

    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_BACK) {
//            finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
