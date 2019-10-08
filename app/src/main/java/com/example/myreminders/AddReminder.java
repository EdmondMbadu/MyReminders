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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddReminder extends AppCompatActivity {

    Intent intent;

    EditText titleEditText;
    EditText dateEditText;
    EditText typeEditText;

    Spinner TypeSpinner;


    Bundle bundle;

    Calendar calendar;
    DBHandler dbHandler;
    String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminders);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleEditText= (EditText)findViewById(R.id.titleEditText);
        dateEditText=(EditText)findViewById(R.id.dateEditText);
//        typeEditText=(EditText)findViewById(R.id.typeEditText);

        TypeSpinner =(Spinner)findViewById(R.id.TypeSpinner) ;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.quantities, android.R.layout.simple_spinner_item);

        // further stylize ArrayAdapter with style defined by simple_spinner_dropdown_item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // set ArrayAdapter on Spinner
        TypeSpinner.setAdapter(adapter);

        // set OnItemSelectedListener on Spinner

//        TypeSpinner.setOnItemSelectedListener(this);

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

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
//        String type= typeEditText.getText().toString();

        // trim strings and see if they're equal to empty string
        // trim Strings and see if any are equal to an empty String
        if ((title.trim().equals("")) || (date.trim().equals("")) || (quantity.trim().equals(""))){
            // if any are equal to an empty String, then that means
            // required data hasn't been input, so display a toast
            Toast.makeText(this, "Please enter a name, price, and quantity!", Toast.LENGTH_LONG).show();
        } else {
            // if none are equal to an empty String, then that means
            // all required data has been input, so update the database
            // and display a toast
            dbHandler.addMyReminder(title, date, quantity);
            Toast.makeText(this, "Reminder added!", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * This method gets called when an item in the Spinner is selected.
     * @param adapterView Spinner AdapterView
     * @param view AddItem View
     * @param position position of item in Spinner that was selected
     * @param id database id of item in Spinner that was selected
     */

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        quantity = adapterView.getItemAtPosition(position).toString();
    }


}



