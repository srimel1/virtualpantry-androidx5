
/*
 * Copyright (c) 2020.. Stephanie Rimel
 */

package com.my.moms.pantry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.apache.commons.text.WordUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class PantryDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_QUANTITY = "quantity";
    public static final String EXTRA_LIFECYCLE = "lifecycle";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_EXPIRATION = "expiration";

    //set the simple date format
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_detail);


        //get the data from th database that was passed through intent in pantrylistfragment
        Intent intent = getIntent();
        final String foodName = WordUtils.capitalize(intent.getStringExtra(EXTRA_NAME));
        final String foodQuantity = intent.getStringExtra(EXTRA_QUANTITY);
        final String foodLifecycle = intent.getStringExtra(EXTRA_LIFECYCLE);
        final String foodDate = intent.getStringExtra(EXTRA_DATE);
        final String foodExpiration = intent.getStringExtra(EXTRA_EXPIRATION);


        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        //collapsingToolbar.text.setTextColor(Color.RED);

        collapsingToolbar.setTitle(foodName);


        Log.i(foodName, " lifecycle: " + foodLifecycle);
        Log.i(foodName, " quantity: " + foodQuantity);
        Log.i(foodName, " this is food date: " + foodDate);
        Log.i(foodName, " expiration: " + foodExpiration);



        //get todays date for the grocery list insertion
        Date today = Calendar.getInstance().getTime();
        String dateAdded = sdf.format(today);
        FloatingActionButton fab = findViewById(R.id.fab);

        //fab onCLick adds item from PantryDetail to the Grocery List
        fab.setOnClickListener(view -> {

            new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                    .setTopColorRes(R.color.PINK)
                    .setButtonsColorRes(R.color.BLACK)
                    .setIcon(R.drawable.ic_forum)
                    .setTitle("Add Pantry Item To Grocery List")
                    .setMessage("Add "+foodName+" to Grocery List and delete from Pantry?")
                    .setPositiveButton("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //insert into Grocery List
                            String name = WordUtils.capitalize(foodName);
                            FirebaseDatabase.getInstance().getReference("Grocery List")
                                    .child(name)
                                    .setValue(new groceryItem(foodName, foodQuantity, dateAdded));

                            //delete from pantry list
                            DatabaseReference mref = FirebaseDatabase.getInstance().getReference("Pantry")
                                    .child(foodName);
                            mref.removeValue();

                            Snackbar.make(view,  "Added "+ foodName + " to Grocery List and removed from Pantry", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            String name = WordUtils.capitalize(foodName);
                            FirebaseDatabase.getInstance().getReference("Grocery List")
                                    .child(name)
                                    .setValue(new groceryItem(name, foodQuantity, dateAdded));

                            Snackbar.make(view,  "Added "+ name + " to Grocery List", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }
                    })
                    .show();



            //insert into database
            FirebaseDatabase.getInstance().getReference("Grocery List")
                    .child(WordUtils.capitalize(foodName))
                    .setValue(new groceryItem(foodName, foodQuantity, dateAdded));

            Snackbar.make(view, "Added " + foodName + " to Grocery List ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });


        //calculate the difference between todays date, and the food expiration
        //date of the item from the data base
        long diffInMillis = getDayDifference(foodExpiration);
        //calculate the number of days by dividing the difference in millis by the number of millis in th day
        long diffInDays = diffInMillis / DateUtils.DAY_IN_MILLIS;


        //bind the database query to the Card view
        final TextView date = (TextView) findViewById(R.id.date);
        final TextView quantity = (TextView) findViewById(R.id.quantity);
        final TextView lifecycle = (TextView) findViewById(R.id.lifecycleDays);
        final TextView countTime = (TextView) findViewById(R.id.counttime);
        final TextView expiration = (TextView) findViewById(R.id.expiration);




        SimpleDateFormat month_date = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");


        Date expireBy = null, purchasedOn = null;
        try {
            expireBy = sdf.parse(foodExpiration);
            purchasedOn = sdf.parse(foodDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formatLifecycle = "Expiration Date: "+ month_date.format(expireBy); //converts lifecycle to date with month
        String formatPurchaseDate = month_date.format(purchasedOn); //converts lifecycle to date with month

        //String formatDate = month_date.format(foodDate); //converts purhcase date to date with month


        // Append the string for lifecycle card view
        String lifecycleTextView = foodLifecycle + " days.\nItem will be added to Grocery List in "+diffInDays+" days";

        String purchaseDateTextView = "Purchased on: " + formatPurchaseDate;


        date.setText(purchaseDateTextView); //sets purchase date in cardview
        quantity.setText(foodQuantity); //sets quantity of items in cardview
        lifecycle.setText(lifecycleTextView); //sets lifecycle in cardview
        expiration.setText(formatLifecycle);


        new CountDownTimer(diffInMillis, 1000) {
            StringBuilder time = new StringBuilder();

            public void onTick(long millisUntilFinished) {


                String time = formatMilliSecondsToTime(millisUntilFinished);

                countTime.setText(time);
            }

            public void onFinish() {
                countTime.setText("done!");
            }
        }.start();


        loadBackdrop();
    }

    private long findExpirationTime(String foodDate) {
        Date future = stringToDate(foodDate);
        Date today = new Date();

        long diffInMillis =  (today.getTime() - future.getTime());

        return diffInMillis;
    }

    /***
     * loads the random food image in the backdrop image view
     */
    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        Glide.with(this).load(pantryItem.getRandFoodImage()).apply(RequestOptions.centerCropTransform()).into(imageView);
    }

    /***
     * inflate the menu
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    /***
     * method to convert String to Date element
     * @param foodDate this is the final expiration date in Date formal
     * @return a Date
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Date stringToDate(String foodDate) {
        Date purchaseDate = new Date();
        //Converts the string element date from firebase into a Date
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); //convert String containing the purchase date to a Date object
        try {
            purchaseDate = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").parse(foodDate);
            //Log.i(purchaseDate.toString(), "purchaseDate before conversion: " + foodDate + " purchaseDate after: " + purchaseDate + " Different: " + purchaseDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return purchaseDate;
    }

    /***
     * Calculates the difference between the food expiration date and todays date
     * @param foodExpiration String date value from database
     * @return a long value that holds the difference in milliseconds between the current date, and
     * the expiration date
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Long getDayDifference(String foodExpiration) {
        Date today = new Date(); //todays date
        Date exprDate = stringToDate(foodExpiration); //expiration date
        long diffInMillis = (long) ((exprDate.getTime() - today.getTime()));
        //Log.i("Time difference: ", "Todays date: " + today + " \nExpiration Date: " + exprDate + " \nDifference: " + diffInDays + " days\n DIFF IN MILLIS: " + diffInMillis);

        return diffInMillis;
    }

    /***
     * method to format the time for the countdown timer TextView
     * @param milliseconds accepts milliseconds as a parameter
     * @returns a string of the date in format dd:hh:mm:ss
     */
    private String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        int days = (int) ((milliseconds /= DateUtils.DAY_IN_MILLIS));
        return twoDigitString(days) + " : " + twoDigitString(hours) + " : " + twoDigitString(minutes) + " : "
                + twoDigitString(seconds);
    }

    /***
     * method to handle the two digit time strings
     * @param number
     * @returns the formatted number
     */
    private String twoDigitString(long number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }
}



