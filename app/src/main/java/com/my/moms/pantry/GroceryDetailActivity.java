
package com.my.moms.pantry;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelySaveStateHandler;

import org.apache.commons.text.WordUtils;

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


        //get the data from th database that was passed through intent in groceryListFragment
        Intent intent = getIntent();
        final String foodName = WordUtils.capitalize(intent.getStringExtra(EXTRA_NAME));
        final String foodQuantity = intent.getStringExtra(EXTRA_QUANTITY);
        //final String foodLifecycle = intent.getStringExtra(EXTRA_LIFECYCLE);
        final String foodDate = intent.getStringExtra(EXTRA_DATE);
        //final String foodExpiration = intent.getStringExtra(EXTRA_EXPIRATION);


        //Log.i(foodName," lifecycle: "+foodLifecycle);
        Log.i("foodName"," quantity: "+foodQuantity);
        Log.i(foodName," date: "+foodDate);
        //Log.i(foodName," expiration: "+foodExpiration);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle(foodName);


        //Log.i(foodName, " GroceryList lifecycle: " + foodLifecycle);
        Log.i(foodName, " GroceryList quantity: " + foodQuantity);
        Log.i(foodName, " GroceryList date: " + foodDate);
        //Log.i(foodName, " GroceryList expiration: " + foodExpiration);


        //get todays date for the grocery list insertion
        Date today = Calendar.getInstance().getTime();
        String dateAdded = sdf.format(today);


        //calculate the difference between todays date, and the food expiration
        //date of the item from the data base
        //long diffInMillis = getDayDifference(foodExpiration);
        //calculate the number of days by dividing the difference in millis by the number of millis in th day
        //long diffInDays = diffInMillis / DateUtils.DAY_IN_MILLIS;


        //bind the database query to the Card view
        final TextView date = (TextView) findViewById(R.id.grocery_date);
        final TextView quantity = (TextView) findViewById(R.id.grocery_quantity);
//        final TextView lifecycle = (TextView) findViewById(R.id.grocery_lifecycle);

        SimpleDateFormat month_date = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        Date todaysDate = new Date();
        String date_added = month_date.format(todaysDate);



//        Date expireBy = null, purchasedOn = null;
//        try {
//            expireBy = sdf.parse(foodExpiration);
//            purchasedOn = sdf.parse(foodDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        //String formatLifecycle = month_date.format(expireBy); //converts lifecycle to date with month
        //String formatPurchaseDate = month_date.format(purchasedOn); //converts lifecycle to date with month

        //String formatDate = month_date.format(foodDate); //converts purhcase date to date with month


        // Append the string for lifecycle card view
        //String lifecycleTextView = foodLifecycle + " days, " + diffInDays + " days left until expiration date: " + formatLifecycle;

        //String purchaseDateTextView = "Purchased on: " + formatPurchaseDate;


        date.setText(date_added); //sets purchase date in cardview
        quantity.setText(foodQuantity); //sets quantity of items in cardview
        //lifecycle.setText(foodLifecycle); //sets lifecycle in cardview


//        new CountDownTimer(diffInMillis, 1000) {
//            StringBuilder time = new StringBuilder();
//
//            public void onTick(long millisUntilFinished) {
//
//
//                String time = formatMilliSecondsToTime(millisUntilFinished);
//
//                lifecycle.setText(time);
//            }
//
//            public void onFinish() {
//                lifecycle.setText("done!");
//            }
//        }.start();


        //add to grocery list item
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {

            groceryToPantryDialog(foodName);
            //insert into database
//            FirebaseDatabase.getInstance().getReference("Grocery List")
//                    .child(foodName)
//                    .setValue(new food(foodName, foodQuantity, foodLifecycle, dateAdded, foodExpiration));

            Snackbar.make(view, "Added " + foodName + " to Pantry List ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
        loadBackdrop();
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


    /***
     * this is the dialog to add items to pantry inventory
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void groceryToPantryDialog(String mName) {

        //final Context context = this;
        final LovelyCustomDialog mDialog = new LovelyCustomDialog(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.grocery_to_pantry_dialog, null);

        mDialog.setView(dialogView);
        mDialog.setTopColorRes(R.color.PINK);
        mDialog.setTitle("Add " + WordUtils.capitalize(mName) + " to Pantry Inventory");
        mDialog.setIcon(R.drawable.ic_forum);
        mDialog.setInstanceStateHandler(R.id.fab, new LovelySaveStateHandler());
        mDialog.show();

        /***
         * onclick event to bind dialog to view
         */
        mDialog.setListener(R.id.ld_btn_confirm, (View.OnClickListener) view -> {


            final EditText quantity = (EditText) dialogView.findViewById(R.id.item_quantity);
            final EditText lifecycle = (EditText) dialogView.findViewById(R.id.item_lifecycle);


            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");

            //initialize strings for database insertion
            String mDate = sdf.format(new Date());
            String mQuantity = quantity.getText().toString().trim();
            String mLifecycle = lifecycle.getText().toString().trim();
            String mExpireDate = getExpirationDate(mDate, Integer.parseInt(mLifecycle));

            //Log.i(mExpireDate, "Date: " + mDate + " + " + mLifecycle + " = Expiration date: " + mExpireDate);


            //insert into database
            FirebaseDatabase.getInstance().getReference("Pantry")
                    .child(mName)
                    .setValue(new pantryItem(WordUtils.capitalize(mName), mQuantity, mLifecycle, mDate, mExpireDate));


            //dismiss the dialog box
            mDialog.dismiss();
            Toast.makeText(GroceryDetailActivity.this, "Added " + mName + " to Pantry Inventory", Toast.LENGTH_LONG).show();
        });
    }

    /***
     * Calculates the expiration date by adding the lifecycle to the purchase date
     * @param foodDate is the purchase date
     * @param lifecycle is the lifecycle date entered by user from database
     * @returns the expiration date
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getExpirationDate(String foodDate, int lifecycle) {

        //Converts the string element foodDate date from firebase into a Date
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");         //convert String containing the purchase date to a Date object
        try {
            Date purchaseDate = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").parse(foodDate);
            Log.i(purchaseDate.toString(), "purchaseDate before conversion: " + foodDate + " purchaseDate after: " + purchaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // set time.
        c.add(Calendar.DATE, lifecycle); // Adding the lifecycle, an integer, to calculate mExpireDateation date

        String mExpireDateationDate = sdf.format(c.getTime()); //converts the date to string

        Log.i("Conversion: ", "DATE INPUT: " + foodDate + " PLUS lifecycle: " + lifecycle + " EQUALS " + mExpireDateationDate);
        Log.i(mExpireDateationDate, "DATE OUTPUT: " + mExpireDateationDate);

        return mExpireDateationDate;
    }



}























