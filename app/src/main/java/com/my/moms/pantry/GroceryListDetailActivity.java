
package com.my.moms.pantry;
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

























import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.yarolegovich.lovelydialog.LovelySaveStateHandler;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

public class GroceryListDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "food_name";
    public int counter;
    private LovelySaveStateHandler saveStateHandler;
    private static final int ID_TEXT_INPUT_DIALOG = R.id.fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        saveStateHandler= new LovelySaveStateHandler();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to Grocery List ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        final String foodName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(foodName);

        final TextView counttime=findViewById(R.id.counttime);
        new CountDownTimer(500000000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText(String.valueOf(counter));
                counter++;
            }
            @Override
            public void onFinish() {
                counttime.setText("Finished");
            }
        }.start();

        loadBackdrop();
    }

    public void showLovelyDialog(int savedDialogId, Bundle savedInstanceState){
        showTextInputDialog(savedInstanceState);
    }

    private void showTextInputDialog(Bundle savedInstanceState) {
        new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
                .setTopColorRes(R.color.PINK)
                .setTitle(R.string.text_inputGL_title)

                .setIcon(R.drawable.ic_forum)
                .setInstanceStateHandler(ID_TEXT_INPUT_DIALOG, saveStateHandler)
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        Toast.makeText(GroceryListDetailActivity.this,"Added "+ text, Toast.LENGTH_SHORT).show();

                    }
                })
//                .setNegativeButton(android.R.string.no, null)
                .setSavedInstanceState(savedInstanceState)
                .show();
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
