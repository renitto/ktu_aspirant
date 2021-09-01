package com.example.renitto.ktu_master.Utils;

import android.content.Context;
import android.net.ParseException;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Renitto on 8/15/2016.
 */
public class TimesAgoHelper {


    private Context _context;

    public TimesAgoHelper(Context context){
        this._context = context;
    }



    public String getTimesAgo(String Fbdate)
    {

      return   DateUtils.getRelativeTimeSpanString(getDateInMillis(Fbdate),Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS).toString();


    }

    public String getTimesAgoCheckList(String Fbdate)
    {

        return   DateUtils.getRelativeTimeSpanString(Long.parseLong(Fbdate),Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS).toString();


    }





    public static long getDateInMillis(String srcDate) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ssZ");

        long dateInMillis = 0;
        try {
            Date date = desiredFormat.parse(srcDate);
            dateInMillis = date.getTime();
            return dateInMillis;
        } catch (ParseException e) {

            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
