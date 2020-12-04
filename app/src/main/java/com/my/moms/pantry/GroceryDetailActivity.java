
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
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class GroceryDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_QUANTITY = "quantity";
    public static final String EXTRA_LIFECYCLE = "lifecycle";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_EXPIRATION = "expiration";


    //set the simple date format
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");


    /***
     * Set the view
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_detail);


        //get the data from th database that was passed through intent in pantrylistfragment
        Intent intent = getIntent();
        final String foodName = intent.getStringExtra(EXTRA_NAME);
        final String foodQuantity = intent.getStringExtra(EXTRA_QUANTITY);
        final String foodLifecycle = intent.getStringExtra(EXTRA_LIFECYCLE);
        final String foodDate = intent.getStringExtra(EXTRA_DATE);
        final String foodExpiration = intent.getStringExtra(EXTRA_EXPIRATION);

        Log.i("INTENT", "Intent: " + EXTRA_NAME);
        Log.i("INTENT", "Intent: " + EXTRA_QUANTITY);
        Log.i("INTENT", "Intent: " + EXTRA_LIFECYCLE);
        Log.i("INTENT", "Intent: " + EXTRA_DATE);
        Log.i("INTENT", "Intent: " + EXTRA_EXPIRATION);

        Log.i(foodName," lifecycle: "+foodLifecycle);
        Log.i("foodName"," quantity: "+foodQuantity);
        Log.i(foodName," date: "+foodDate);
        Log.i(foodName," expiration: "+foodExpiration);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        //collapsingToolbar.text.setTextColor(Color.RED);

        collapsingToolbar.setTitle(foodName);


        Log.i(foodName, " GroceryList lifecycle: " + foodLifecycle);
        Log.i(foodName, " GroceryList quantity: " + foodQuantity);
        Log.i(foodName, " GroceryList date: " + foodDate);
        Log.i(foodName, " GroceryList expiration: " + foodExpiration);


        //get todays date for the grocery list insertion
        Date today = Calendar.getInstance().getTime();
        String dateAdded = sdf.format(today);


        //calculate the difference between todays date, and the food expiration
        //date of the item from the data base
        long diffInMillis = getDayDifference(foodExpiration);
        //calculate the number of days by dividing the difference in millis by the number of millis in th day
        long diffInDays = diffInMillis / DateUtils.DAY_IN_MILLIS;


        //bind the database query to the Card view
        final TextView date = (TextView) findViewById(R.id.grocery_date);
        final TextView quantity = (TextView) findViewById(R.id.grocery_quantity);
        final TextView lifecycle = (TextView) findViewById(R.id.grocery_lifecycle);

        SimpleDateFormat month_date = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");


        Date expireBy = null, purchasedOn = null;
        try {
            expireBy = sdf.parse(foodExpiration);
            purchasedOn = sdf.parse(foodDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formatLifecycle = month_date.format(expireBy); //converts lifecycle to date with month
        String formatPurchaseDate = month_date.format(purchasedOn); //converts lifecycle to date with month

        //String formatDate = month_date.format(foodDate); //converts purhcase date to date with month


        // Append the string for lifecycle card view
        String lifecycleTextView = foodLifecycle + " days, " + diffInDays + " days left until expiration date: " + formatLifecycle;

        String purchaseDateTextView = "Purchased on: " + formatPurchaseDate;


        date.setText(purchaseDateTextView); //sets purchase date in cardview
        quantity.setText(foodQuantity); //sets quantity of items in cardview
        lifecycle.setText(foodLifecycle); //sets lifecycle in cardview


        new CountDownTimer(diffInMillis, 1000) {
            StringBuilder time = new StringBuilder();

            public void onTick(long millisUntilFinished) {


                String time = formatMilliSecondsToTime(millisUntilFinished);

                lifecycle.setText(time);
            }

            public void onFinish() {
                lifecycle.setText("done!");
            }
        }.start();


        //add to grocery list item
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            //insert into database
            FirebaseDatabase.getInstance().getReference("Grocery List")
                    .child(foodName)
                    .setValue(new food(foodName, foodQuantity, foodLifecycle, dateAdded, foodExpiration));

            Snackbar.make(view, "Added " + foodName + " to Grocery List ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
        loadBackdrop();
    }


    /***
     * loads the random food image in the backdrop image view
     */
    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        Glide.with(this).load(food.getRandFoodImage()).apply(RequestOptions.centerCropTransform()).into(imageView);
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



























//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.icu.text.DateFormat;
//import android.icu.text.SimpleDateFormat;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.text.format.DateUtils;
//import android.util.Log;
//import android.view.Menu;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.google.android.material.appbar.CollapsingToolbarLayout;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//import com.google.firebase.database.FirebaseDatabase;
//
//import org.joda.time.Interval;
//
//import java.text.ParseException;
//import java.time.Instant;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//import java.util.TimeZone;
//import java.util.concurrent.TimeUnit;
//
//@RequiresApi(api = Build.VERSION_CODES.N)
//public class GroceryListDetailActivity extends AppCompatActivity {
//
//    public static final String EXTRA_NAME = "name";
//    public static final String EXTRA_QUANTITY = "quantity";
//    public static final String EXTRA_LIFECYCLE = "lifecycle";
//    public static final String EXTRA_DATE = "date";
//
//
//    //set the simple date format
//    @SuppressLint("SimpleDateFormat")
//    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
//
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.grocery_detail);
//
//
//        //get the data from th database that was passed through intent in pantrylistfragment
//        Intent intent = getIntent();
//        final String foodName = intent.getStringExtra(EXTRA_NAME);
//        final String foodQuantity = intent.getStringExtra(EXTRA_QUANTITY);
//        final String foodLifecycle = intent.getStringExtra(EXTRA_LIFECYCLE);
//        final String foodDate = intent.getStringExtra(EXTRA_DATE);
//
//
//        final Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
//
//        collapsingToolbar.setTitle(foodName);
//
//
//        Log.i(foodName, " lifecycle: " + foodLifecycle);
//        Log.i(foodName, " quantity: " + foodQuantity);
//        Log.i(foodName, " date: " + foodDate);
//
//
//
//        //get todays date for the grocery list insertion
//        Date today = Calendar.getInstance().getTime();
//        String dateAdded = sdf.format(today);
//        FloatingActionButton fab = findViewById(R.id.fab);
//
//        fab.setOnClickListener(view -> {
//            //insert into database
//            FirebaseDatabase.getInstance().getReference("Pantry")
//                    .child(foodName)
//                    .setValue(new grocery(foodName, foodQuantity, foodLifecycle, dateAdded));
//
//            Snackbar.make(view, "Added " + foodName + " to Pantry List ", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        });
//
//
//
//
//        //bind the database query to the Card view
//        final TextView date = (TextView) findViewById(R.id.date);
//        final TextView quantity = (TextView) findViewById(R.id.quantity);
//        final TextView lifecycle = (TextView) findViewById(R.id.lifecycleDays);
//
//
////        SimpleDateFormat month_date = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
////        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
//
//
////        Date expireBy = null, purchasedOn = null;
////        try {
////            expireBy = sdf.parse(foodExpiration);
////            purchasedOn = sdf.parse(foodDate);
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
//
////        String formatLifecycle = month_date.format(expireBy); //converts lifecycle to date with month
////        String formatPurchaseDate = month_date.format(purchasedOn); //converts lifecycle to date with month
//
//        //String formatDate = month_date.format(foodDate); //converts purhcase date to date with month
//
//
//        // Append the string for lifecycle card view
////        String lifecycleTextView = foodLifecycle + " days, " + diffInDays + " days left until expiration date: " + formatLifecycle;
////
////        String purchaseDateTextView = "Added on: " + dateAdded;
//
//
//        date.setText(foodDate); //sets purchase date in cardview
//        quantity.setText(foodQuantity); //sets quantity of items in cardview
//        lifecycle.setText(foodLifecycle); //sets lifecycle in cardview
//
//
//
//        loadBackdrop();
//    }
//
//    /***
//     * loads the random food image in the backdrop image view
//     */
//    private void loadBackdrop() {
//        final ImageView imageView = findViewById(R.id.backdrop);
//        Glide.with(this).load(food.getRandFoodImage()).apply(RequestOptions.centerCropTransform()).into(imageView);
//    }
//
//    /***
//     * inflate the menu
//     * @param menu
//     * @return boolean
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.sample_actions, menu);
//        return true;
//    }
//
//    /***
//     * method to convert String to Date element
//     * @param foodDate this is the final expiration date in Date formal
//     * @return a Date
//     */
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public Date stringToDate(String foodDate) {
//        Date purchaseDate = new Date();
//        //Converts the string element date from firebase into a Date
//        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); //convert String containing the purchase date to a Date object
//        try {
//            purchaseDate = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").parse(foodDate);
//            //Log.i(purchaseDate.toString(), "purchaseDate before conversion: " + foodDate + " purchaseDate after: " + purchaseDate + " Different: " + purchaseDate.getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return purchaseDate;
//    }
//
//}
//























//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.view.Menu;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.google.android.material.appbar.CollapsingToolbarLayout;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//import com.yarolegovich.lovelydialog.LovelySaveStateHandler;
//import com.yarolegovich.lovelydialog.LovelyTextInputDialog;
//
//public class GroceryDetailActivity extends AppCompatActivity {
//
//    public static final String EXTRA_NAME = "food_name";
//    public int counter;
//    private LovelySaveStateHandler saveStateHandler;
//    private static final int ID_TEXT_INPUT_DIALOG = R.id.fab;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pantry_detail);
//        saveStateHandler= new LovelySaveStateHandler();
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Added to Grocery List ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        Intent intent = getIntent();
//        final String foodName = intent.getStringExtra(EXTRA_NAME);
//
//        final Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(foodName);
//
//        final TextView lifecycle=findViewById(R.id.lifecycle);
//        new CountDownTimer(500000000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                lifecycle.setText(String.valueOf(counter));
//                counter++;
//            }
//            @Override
//            public void onFinish() {
//                lifecycle.setText("Finished");
//            }
//        }.start();
//
//        loadBackdrop();
//    }
//
//    public void showLovelyDialog(int savedDialogId, Bundle savedInstanceState){
//        showTextInputDialog(savedInstanceState);
//    }
//
//    private void showTextInputDialog(Bundle savedInstanceState) {
//        new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
//                .setTopColorRes(R.color.PINK)
//                .setTitle(R.string.text_inputGL_title)
//
//                .setIcon(R.drawable.ic_forum)
//                .setInstanceStateHandler(ID_TEXT_INPUT_DIALOG, saveStateHandler)
//                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
//                    @Override
//                    public void onTextInputConfirmed(String text) {
//                        Toast.makeText(GroceryDetailActivity.this,"Added "+ text, Toast.LENGTH_SHORT).show();
//
//                    }
//                })
////                .setNegativeButton(android.R.string.no, null)
//                .setSavedInstanceState(savedInstanceState)
//                .show();
//    }
//
//    private void loadBackdrop() {
//        final ImageView imageView = findViewById(R.id.backdrop);
//        Glide.with(this).load(food.getRandFoodImage()).apply(RequestOptions.centerCropTransform()).into(imageView);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.sample_actions, menu);
//        return true;
//    }
//
//
//}
