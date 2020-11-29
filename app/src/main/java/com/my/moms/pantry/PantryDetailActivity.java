
package com.my.moms.pantry;

import android.content.Intent;
import android.icu.text.DateFormat;
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

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class PantryDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_QUANTITY = "quantity";
    public static final String EXTRA_LIFECYCLE = "lifecycle";
    public static final String EXTRA_DATE = "date";
    public int counter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(view -> Snackbar.make(view, "Added to Grocery List ", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());


        //get the data from th database that was passed through intent in pantrylistfragment
        Intent intent = getIntent();
        final String foodName = intent.getStringExtra(EXTRA_NAME);
        final String foodQuantity = intent.getStringExtra(EXTRA_QUANTITY);
        final String foodLifecycle = intent.getStringExtra(EXTRA_LIFECYCLE);
        final String foodDate = intent.getStringExtra(EXTRA_DATE);

        //convert lifecycle to int
        int lifecycle = Integer.parseInt(foodLifecycle);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(foodName);


        Log.i(foodName, " lifecycle: " + lifecycle);
        Log.i(foodName, " quantity: " + foodQuantity);

        long mInitialTime = DateUtils.DAY_IN_MILLIS * lifecycle;
//                DateUtils.HOUR_IN_MILLIS * 9 +
//                DateUtils.MINUTE_IN_MILLIS * 3 +
//                DateUtils.SECOND_IN_MILLIS * 42;


        //bind the database query to the Card view
        final TextView date = (TextView) findViewById(R.id.date);
        final TextView quantity = (TextView) findViewById(R.id.quantity);
        //final TextView lifecycle = (TextView) findViewById(R.id.item_lifecycle);


        date.setText(foodDate); //purchase date
        quantity.setText(foodQuantity); // number of items
        

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");         //convert String containing the purchase date to a Date object
        try {
            Date purchaseDate = new SimpleDateFormat("MM-dd-yyyy").parse(foodDate);
            Log.i(purchaseDate.toString(), "purchaseDate before conversion: " + foodDate + " purchaseDate after: " + purchaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // set time.

        c.add(Calendar.DATE, lifecycle); // Adding the lifecycle, an integer, to calculate expiration date
        String expirationDate = sdf.format(c.getTime());
        
        Log.i(foodName, "DATE INPUT: " + foodDate + " PLUS lifecycle: " + lifecycle + " EQUALS " + expirationDate);
        Log.i(expirationDate, "DATE OUTPUT: " + expirationDate);


        final TextView countTime = (TextView) findViewById(R.id.counttime);


        CountDownTimer mCountDownTimer = new CountDownTimer(mInitialTime, 1000) {
            StringBuilder time = new StringBuilder();

            @Override
            public void onFinish() {
                countTime.setText(DateUtils.formatElapsedTime(0));
                //mTextView.setText("Times Up!");
            }

            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("Milistilfinished: ", "millisUntil: "+millisUntilFinished);
                time.setLength(0);


                // Use days if appropriate
                if (millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
                    Log.i("Dateutils: ", "millisUntil: "+DateUtils.DAY_IN_MILLIS);
                    long count = millisUntilFinished / DateUtils.DAY_IN_MILLIS;
                    if (count > 1)
                        time.append(count).append(" days ");
                    else
                        time.append(count).append(" day ");

                    millisUntilFinished %= DateUtils.DAY_IN_MILLIS;
                }

                time.append(DateUtils.formatElapsedTime(Math.round(millisUntilFinished / 1000d)));
                countTime.setText(time.toString()+" left");
            }
        }.start();


//        new CountDownTimer(500000000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                counttime.setText(String.valueOf(counter));
//                counter++;
//            }
//            @Override
//            public void onFinish() {
//                counttime.setText("Finished");
//            }
//        }.start();


        loadBackdrop();
    }

    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        Glide.with(this).load(food.getRandFoodImage()).apply(RequestOptions.centerCropTransform()).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }


}
