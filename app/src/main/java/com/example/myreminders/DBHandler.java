package com.example.myreminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myreminders.db";
    private static final String TABLE_MYREMINDERS_LIST = "myreminderslist";
    private static final String COLUMN_LIST_ID = "_id";
    private static final String COLUMN_LIST_TITLE = "title";
    private static final String COLUMN_LIST_DATE = "date";
    private static final String COLUMN_LIST_TYPE = "type";

    public DBHandler(Context context, SQLiteDatabase.CursorFactory cursorFactory) {
        super(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
    }

//    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + TABLE_MYREMINDERS_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_TITLE + " TEXT, " +
                COLUMN_LIST_DATE + " TEXT, " +
                COLUMN_LIST_TYPE + " TEXT" +
                ");";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MYREMINDERS_LIST);
        onCreate(sqLiteDatabase);

    }


//    @RequiresApi(api = Build.VERSION_CODES.O)
    // this method adds the reminder on the data base
    public void addMyReminder(String title, String date, String type) {
        SQLiteDatabase db = getWritableDatabase();
//        String[] data = date.split("-");
//        int year = Integer.parseInt(data[0]);
//        int month = Integer.parseInt(data[1]);
//        int day = Integer.parseInt(data[2]);
//        expiration = "false";
//        String currentDate = (java.time.LocalDate.now()).toString();
//        String[] data2 = currentDate.split("-");
//        int yearCurrent = Integer.parseInt(data2[0]);
//        int monthCurrent = Integer.parseInt(data2[1]);
//        int dayCurrent = Integer.parseInt(data2[2]);
//        boolean valueY = false;
//        boolean valueM = false;
//        boolean valueD = false;
//        // test the date
//
//        if (year > yearCurrent) {
//            valueY = true;
//            expiration = "true";
//        } else if (year == yearCurrent) {
//            if (month > monthCurrent) {
//                expiration = "true";
//            } else if (month == monthCurrent) {
//                if (day > dayCurrent) {
//                    expiration = "true";
//                }
//            }
//        }


        ContentValues values = new ContentValues();
        values.put(COLUMN_LIST_TITLE, title);
        values.put(COLUMN_LIST_DATE, date);
        values.put(COLUMN_LIST_TYPE, type);

        db.insert(TABLE_MYREMINDERS_LIST, null, values);
        db.close();


    }


    public Cursor getMyReminders() {
        SQLiteDatabase db = getWritableDatabase();

        return db.rawQuery("SELECT * FROM " + TABLE_MYREMINDERS_LIST, null);
    }

    public String getMyRemindersTitle(int id){
        SQLiteDatabase db= getWritableDatabase();
        String dbString="";
        String query="SELECT * FROM "+TABLE_MYREMINDERS_LIST +" WHERE "+ COLUMN_LIST_ID + " = "+ id;
        Cursor cursor=db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getString(cursor.getColumnIndex("title"))!=null){
            dbString=cursor.getString(cursor.getColumnIndex("title"));
        }

        return dbString;

    }

    public String getMyRemindersDate(int id){
        SQLiteDatabase db= getWritableDatabase();
        String dbString="";
        String query="SELECT * FROM "+TABLE_MYREMINDERS_LIST +" WHERE "+ COLUMN_LIST_ID + " = "+ id;
        Cursor cursor=db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getString(cursor.getColumnIndex("date"))!=null){
            dbString=cursor.getString(cursor.getColumnIndex("date"));
        }

        return dbString;

    }

}
