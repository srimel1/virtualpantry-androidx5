
package com.my.moms.pantry;

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
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;



@RequiresApi(api = Build.VERSION_CODES.N)
public class GroceryListDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME2 = "name";
    public static final String EXTRA_QUANTITY2 = "quantity";
    public static final String EXTRA_LIFECYCLE2 = "lifecycle";
    public static final String EXTRA_DATE2 = "date";
    public static final String EXTRA_EXPIRATION2 = "expiration";



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_detail);


//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//
//        fab.setOnClickListener(view -> Snackbar.make(view, "Added to Grocery List ", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());


        //get the data from th database that was passed through intent in pantrylistfragment
        Intent intent = getIntent();
        final String foodName = intent.getStringExtra(EXTRA_NAME2);
        final String foodQuantity = intent.getStringExtra(EXTRA_QUANTITY2);
        final String foodLifecycle = intent.getStringExtra(EXTRA_LIFECYCLE2);
        final String foodDate = intent.getStringExtra(EXTRA_DATE2);
        final String foodExpiration = intent.getStringExtra(EXTRA_EXPIRATION2);


        final Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar2);
        //collapsingToolbar.text.setTextColor(Color.RED);

        collapsingToolbar.setTitle(foodName);



        Log.i(foodName, " lifecycle: " + foodLifecycle);
        Log.i(foodName, " quantity: " + foodQuantity);
        Log.i(foodName, " date: " + foodDate);
        Log.i(foodName, " expiration: " + foodExpiration);

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insert into database
                FirebaseDatabase.getInstance().getReference("Pantry")
                        .child(foodName)
                        .setValue(new food(foodName, foodQuantity, foodLifecycle, foodDate, foodExpiration));

                Snackbar.make(view, "Added to Pantry Inventory List", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
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

        String purchaseDateTextView = "Purchased on: "+formatPurchaseDate;


        date.setText(purchaseDateTextView); //sets purchase date in cardview
        quantity.setText(foodQuantity); //sets quantity of items in cardview
        lifecycle.setText(lifecycleTextView); //sets lifecycle in cardview



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

    /***
     * loads the random food image in the backdrop image view
     */
    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop2);
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
        if (number == 0) { return "00"; }
        if (number / 10 == 0) { return "0" + number; }

        return String.valueOf(number);
    }
}



