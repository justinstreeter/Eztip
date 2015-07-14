package com.jstreetdev.eztip;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void calculate(View view){
        // Hide Numpad onClick - when the button is pressed
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        // get all the user input from the EditText fields
        EditText total   = (EditText)findViewById(R.id.total);
        EditText people   = (EditText)findViewById(R.id.people);
        EditText tip   = (EditText)findViewById(R.id.percent);
        EditText each   = (EditText)findViewById(R.id.tip);
        EditText tiptotal = (EditText)findViewById(R.id.tiptotal2);

        // Convert all user input to Float
        double totalVar = Double.parseDouble(total.getText().toString());
        float peopleVar = Float.parseFloat(people.getText().toString());
        float tipVar = Float.parseFloat(tip.getText().toString());
        // Calculate total * tip + total / people
        double calc = (totalVar * (tipVar/100) + totalVar) / peopleVar;
        double calc2 = (totalVar * (tipVar/100)) / peopleVar;

        // Round it up to 2 decimal points

        String people1 = people.getText().toString();
        String EachPay = String.format("%.2f", calc);
        String TipPay = String.format("%.2f", calc2);


        // Show what everyone pays in the EditText field
        each.setText("$"+EachPay);
        tiptotal.setText("$"+TipPay);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        restoreActionBar();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                return (true);
            case R.id.action_about:
                Intent i = new Intent(this, About_Activity.class);
                startActivity(i);
                return (true);
            case R.id.action_help:
                i = new Intent(this, Help_Activity.class);
                startActivity(i);
                return (true);
            case R.id.action_mail:
                i = new Intent(this, MailActivity.class);
                startActivity(i);
                return (true);
        }

            return super.onOptionsItemSelected(item);
        }
    }

