package com.example.myreminders;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // THIS is the version I am resending to prof Ceklosky

    MyRemindersLists myRemindersLists;
    Intent intent;
    DBHandler dbHandler;


    MyRemindersLists myRemindersListsAdapter;
    ListView myReminderListsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new DBHandler(this, null);
        myReminderListsView =(ListView)findViewById(R.id.myRemindersListsView);

//        myReminderListsView=(ListView) findViewById(R.id.myReminderListsView) ;


        myRemindersListsAdapter= new MyRemindersLists(this, dbHandler.getMyReminders(),0);
        myReminderListsView.setAdapter(myRemindersListsAdapter);

        // register OnItemClickListener on ListView
        myReminderListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This method gets called when a row in the ListView is clicked
             * @param adapterView shopperListView
             * @param view content_main
             * @param position row clicked in shopperListView
             * @param id shopping list id from database associated with clicked row
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                String date=dbHandler.getMyRemindersDate((int)id);

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


                if (expiration.equalsIgnoreCase("true")){

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);



                    // set it icon, title, and text
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setContentTitle("MyReminders");
                    builder.setContentText("Reminder is expired "+date);

                    // initialize an intent for the view list activity

//            intent = new Intent(this, ViewList.class);
                    intent= new Intent(MainActivity.this, MainActivity.class);

                    // initialize a pending intent
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                    // set the content intent of the notification
                    builder.setContentIntent(pendingIntent);

                    // Initialize a Notification Manager
                    NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    // have the NotifcationManager send the Notification

                    notificationManager.notify(2142, builder.build());
                }

            }
        });

        // if all shopping list items have been purchased




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       switch (item.getItemId()){
           case R.id.action_home:
               intent= new Intent(this, MainActivity.class);
               startActivity(intent);
               return true;
           case R.id.action_add_reminder:
               intent= new Intent(this, AddReminder.class);
               startActivity(intent);
               return true;
               default:
                   return super.onOptionsItemSelected(item);

       }

    }

    public void openCreateReminders(View view){

    intent= new Intent(this, AddReminder.class);
    startActivity(intent);

    }




}
