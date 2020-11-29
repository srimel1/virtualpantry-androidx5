
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

import java.util.Calendar;
import java.util.Date;

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



        Intent intent = getIntent();
        final String foodName = intent.getStringExtra(EXTRA_NAME);
        final String foodQuantity = intent.getStringExtra(EXTRA_QUANTITY);
        final String foodLifecycle = intent.getStringExtra(EXTRA_LIFECYCLE);

        int lifecycle = Integer.parseInt(foodLifecycle);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(foodName);

       // int lifecycle = Integer.parseInt(EXTRA_LIFECYCLE);

        Log.i(foodName, " lifecycle: "+ lifecycle);
        Log.i(foodName, " quantity: "+ foodQuantity);
        long mInitialTime = DateUtils.DAY_IN_MILLIS *  lifecycle;
//                DateUtils.HOUR_IN_MILLIS * 9 +
//                DateUtils.MINUTE_IN_MILLIS * 3 +
//                DateUtils.SECOND_IN_MILLIS * 42;
        TextView mTextView;

        final TextView date = (TextView) findViewById(R.id.date);
        Date purchaseDate = java.util.Calendar.getInstance().getTime();
        final TextView quantity = (TextView) findViewById(R.id.quantity);
        //final TextView lifecycle = (TextView) findViewById(R.id.item_lifecycle);


        quantity.setText(foodQuantity);
//        lifecycle.setText(foodLifecycle);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        date.setText(dateTime);

        final TextView countTime = (TextView) findViewById(R.id.counttime);

        //long lifecycleConversion = Long.parseLong(EXTRA_LIFECYCLE);
        //Log.i("lifecycleConversion", "lifecycleConversion: "+lifecycleConversion);

        CountDownTimer mCountDownTimer = new CountDownTimer(mInitialTime, 5000) {
            StringBuilder time = new StringBuilder();
            @Override
            public void onFinish() {
                countTime.setText(DateUtils.formatElapsedTime(0));
                //mTextView.setText("Times Up!");
            }

            @Override
            public void onTick(long millisUntilFinished) {
                time.setLength(0);
                // Use days if appropriate
                if(millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
                    long count = millisUntilFinished / DateUtils.DAY_IN_MILLIS;
                    if(count > 1)
                        time.append(count).append(" days ");
                    else
                        time.append(count).append(" day ");

                    millisUntilFinished %= DateUtils.DAY_IN_MILLIS;
                }

                time.append(DateUtils.formatElapsedTime(Math.round(millisUntilFinished / 1000d)));
                countTime.setText(time.toString());
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
