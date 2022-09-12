package com.railway.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class DateHelper {

    public static String getDateFromCurrentDate(String formatDate, int noOfDays) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(getCurrentDate(formatDate)));
        } catch (ParseException e) {
            System.out.println("Exception occurred" + e);
        }
        c.add(Calendar.DATE, noOfDays);
        return sdf.format(c.getTime());
    }

    public static String getCurrentDate(String formatDate) {
        SimpleDateFormat df = new SimpleDateFormat(formatDate);
        Calendar c = Calendar.getInstance();
        return df.format(c.getTime());
    }

    public static int getHourCurrent() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.getHour();
    }
}
