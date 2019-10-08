package com.example.myreminders;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyRemindersLists extends CursorAdapter {

    public MyRemindersLists(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.li_myreminders_list, viewGroup, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ((TextView) view.findViewById(R.id.titleTextView)).setText(cursor.getString((cursor.getColumnIndex("title"))));
        ((TextView) view.findViewById(R.id.dateTextView)).setText(cursor.getString((cursor.getColumnIndex("date"))));
//        ((TextView) view.findViewById(R.id.expirationTextView)).setText
//                (cursor.getString((cursor.getColumnIndex("type"))));

        ((TextView) view.findViewById(R.id.expirationTextView)).setText("Expired ? "+
                Expiration(cursor.getString((cursor.getColumnIndex("date")))));


    }


    public static String Expiration(String date) {


        String[] data = date.split("-");
        int year = Integer.parseInt(data[0]);
        int month = Integer.parseInt(data[1]);
        int day = Integer.parseInt(data[2]);
        String expiration = "true";
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat mdformat= new SimpleDateFormat("yyy-MM-dd");
        String strDate= mdformat.format(calendar.getTime());
//        String currentDate = java.time.LocalDate.now().toString();
        String[] data2 = strDate.split("-");
        int yearCurrent = Integer.parseInt(data2[0]);
        int monthCurrent = Integer.parseInt(data2[1]);
        int dayCurrent = Integer.parseInt(data2[2]);

        // test the date

        if (year > yearCurrent) {

            expiration = "false";
        } else if (year == yearCurrent) {
            if (month > monthCurrent) {
                expiration = "false";
            } else if (month == monthCurrent) {
                if (day >dayCurrent) {
                    expiration = "false";
                }
            }
        }
         return expiration;
    }

}
