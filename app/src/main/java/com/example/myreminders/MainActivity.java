package com.example.myreminders;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    // THIS is the version I am resending to prof Ceklosky
    Intent intent;
    DBHandler dbHandler;

    ListView myReminderListsView;
    MyRemindersLists myRemindersListsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new DBHandler(this, null);

//        myReminderListsView=(ListView)findViewById(R.id.m) ;
        myRemindersListsAdapter= new MyRemindersLists(this, dbHandler.getMyReminders(),0);
        myReminderListsView.setAdapter(myRemindersListsAdapter);


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
