package com.example.demoapp.Utils;

import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.URL;
import java.util.Date;
import java.util.Calendar;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;


/**
 * Created by Aikya on 16-Jan-18.
 */

public class Utils {


    public static final String MM_DD_YYY_HH_MM = "MM/dd/yyyy HH:mm";


    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }


    public static String getCurrentDateTime(String format) {
        DateFormat dateFormatter = new SimpleDateFormat(format);
        dateFormatter.setLenient(false);
        dateFormatter.setTimeZone(Calendar.getInstance().getTimeZone());
        Date today = new Date();
        String s = dateFormatter.format(today);
        Log.e("today_Date", "" + s);
        return s;
    }

}
