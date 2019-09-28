package com.example.myreminders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddReminder extends AppCompatActivity {

    Intent intent;

    EditText titleEditText;
    EditText dateEditText;
    EditText typeEditText;

    Calendar calendar;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminders);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleEditText= (EditText)findViewById(R.id.titleEditText);
        dateEditText=(EditText)findViewById(R.id.dateEditText);
        typeEditText=(EditText)findViewById(R.id.typeEditText);

        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayofMonth) {
                // set the Calendar year, month, and day to year, month, and day
                // selected in DatePickerDialog
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH,dayofMonth);
                // call the method that updates date EditText with date set in DatePickerDialog
                updateDueDate();

            }
        };

        // register an OnCLick listener on the date EditText;
        dateEditText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddReminder.this,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        dbHandler= new DBHandler(this, null);


    }

    public void updateDueDate(){
        // create a SimpleDateFormat
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        // apply SimpleDateFormat to date in Calendar and set it in the date EditText
        dateEditText.setText(simpleDateFormat.format(calendar.getTime()));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_reminders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // get the id of the item that was selected
        switch (item.getItemId()) {
            case R.id.action_home:
                // initializing an intent for the main activity, starting it
                // and returning true
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_add_reminder:
                // initializing an intent for the create list activity, starting it
                // and returning true
                intent = new Intent(this, AddReminder.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createReminders(MenuItem menuItem){

        String title= titleEditText.getText().toString();
        String date= dateEditText.getText().toString();
        String type= typeEditText.getText().toString();

        // trim strings and see if they're equal to empty string
        if(title.trim().equals("")|| date.trim().equals("")|| type.trim().equals("")){
            Toast.makeText(this,"Please enter a title, date, and type!", Toast.LENGTH_LONG).show();
        }else{
            // if none of the Strings are empty, display, Shopping list Added
            dbHandler.addMyReminder(title, date, type);
            Toast.makeText(this,"Reminder Added!", Toast.LENGTH_LONG).show();
        }
    }

}



