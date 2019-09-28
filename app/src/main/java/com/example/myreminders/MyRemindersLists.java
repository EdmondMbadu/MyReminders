package com.example.myreminders;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyRemindersLists extends CursorAdapter {

    public MyRemindersLists(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.li_myreminders_list, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ((TextView)view.findViewById(R.id.titleTextView)).setText(cursor.getString((cursor.getColumnIndex("tile"))));
        ((TextView)view.findViewById(R.id.dateTextView)).setText(cursor.getString((cursor.getColumnIndex("date"))));
        ((TextView)view.findViewById(R.id.expirationTextView)).setText(cursor.getString((cursor.getColumnIndex("expiration"))));
    }
}
