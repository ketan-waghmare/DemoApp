package com.example.demoapp.Utils;

import android.content.Context;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.demoapp.R;

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

    public static String PREFERENCE_USER = "user";

    public static final String DD_MM_YYYY = "dd-MM-yyyy";

    public static String getCurrentDateTime(String format) {
        DateFormat dateFormatter = new SimpleDateFormat(format);
        dateFormatter.setLenient(false);
        dateFormatter.setTimeZone(Calendar.getInstance().getTimeZone());
        Date today = new Date();
        String s = dateFormatter.format(today);
        Log.e("today_Date", "" + s);
        return s;
    }

    public static void replaceFragment(Context context, Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

}
