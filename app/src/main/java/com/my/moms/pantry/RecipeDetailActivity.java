//
//package com.my.moms.pantry;
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
//public class RecipeDetailActivity extends AppCompatActivity {
//
//    public static final String EXTRA_NAME3 = "name";
//    public static final String EXTRA_DESCRIPTION3 = "description";
//    public static final String EXTRA_STEPS3 = "steps";
//    public static final String EXTRA_SERVING3 = "serving size";
//    public static final String EXTRA_INGREDIENTS3 = "recipeIngredients";
//    public int counter;
//
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.recipe_detail);
//
//
//        //get the data from th database that was passed through intent in pantrylistfragment
//        Intent intent = getIntent();
//        final String recipeName = intent.getStringExtra(EXTRA_NAME3);
//        final String recipeDescription = intent.getStringExtra(EXTRA_DESCRIPTION3);
//        final String recipeSteps = intent.getStringExtra(EXTRA_STEPS3);
//        final String recipeServing = intent.getStringExtra(EXTRA_SERVING3);
//        final String recipeIngredients = intent.getStringExtra(EXTRA_INGREDIENTS3);
//
//
//        final Toolbar toolbar = findViewById(R.id.toolbar3);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        CollapsingaToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar3);
//        //collapsingToolbar.text.setTextColor(Color.RED);
//
//        collapsingToolbar.saetTitle(recipeName);
//
//
//        Log.i(recipeName, " steps: " + recipeSteps);
//        Log.i(recipeName, " description: " + recipeDescription);
//        Log.i(recipeName, " serving: " + recipeServing);
//        Log.i(recipeName, " ingredients: " + recipeIngredients);
//
//        //TODO add new recipe dialog
//
////        FloatingActionButton fab = findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                //insert into database
////                FirebaseDatabase.getInstance().getReference("Grocery List")
////                        .child(recipeName)
////                        .setValue(new food(recipeName, recipeDescription, recipeSteps, recipeServing, recipeIngredients));
////
////                Snackbar.make(view, "Added to Grocery List", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//
//
//        //calculate the difference between todays date, and the food expiration
//        //date of the item from the data base
////        long diffInMillis = getDayDifference(recipeIngredients);
////        //calculate the number of days by dividing the difference in millis by the number of millis in th day
////        long diffInDays = diffInMillis / DateUtils.DAY_IN_MILLIS;
//
//
//        //bind the database query to the Card view
//        final TextView description = (TextView) findViewById(R.id.description);
//        final TextView steps = (TextView) findViewById(R.id.steps);
//        final TextView ingredients = (TextView) findViewById(R.id.ingredients);
//        final TextView serving = (TextView) findViewById(R.id.serving_size);
////
////
////        SimpleDateFormat month_date = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
////        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
////
////
////        Date expireBy = null, purchasedOn = null;
////        try {
////            expireBy = sdf.parse(recipeIngredients);
////            purchasedOn = sdf.parse(recipeServing);
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
////
////        String formatLifecycle = month_date.format(expireBy); //converts lifecycle to date with month
////        String formatPurchaseDate = month_date.format(purchasedOn); //converts lifecycle to date with month
//
//        //String formatDate = month_date.format(recipeServing); //converts purhcase date to date with month
//
//
//        // Append the string for lifecycle card view
////        String lifecycleTextView = recipeSteps + " days, " + diffInDays + " days left until expiration date: " + formatLifecycle;
////
////        String purchaseDateTextView = "Purchased on: "+formatPurchaseDate;
//
//
//        description.setText("Lorem ipsum"); //sets purchase date in cardview
//        steps.setText("lorem ipsum"); //sets quantity of items in cardview
//        ingredients.setText("Lorem ipsum"); //sets lifecycle in cardview
//
//
//        loadBackdrop();
//    }
//
//    /***
//     * loads the random food image in the backdrop image view
//     */
//    private void loadBackdrop() {
//        final ImageView imageView = findViewById(R.id.backdrop3);
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
//}
//
//    /***
//     * method to convert String to Date element
//     * @param recipeServing this is the final expiration date in Date formal
//     * @return a Date
//     */
////    @RequiresApi(api = Build.VERSION_CODES.N)
////    public Date stringToDate(String recipeServing) {
////        Date purchaseDate = new Date();
////        //Converts the string element date from firebase into a Date
////        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); //convert String containing the purchase date to a Date object
////        try {
////            purchaseDate = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").parse(recipeServing);
////            //Log.i(purchaseDate.toString(), "purchaseDate before conversion: " + recipeServing + " purchaseDate after: " + purchaseDate + " Different: " + purchaseDate.getTime());
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
////        return purchaseDate;
////    }
//
//    /***
//     * Calculates the difference between the food expiration date and todays date
//     * @param recipeIngredients String date value from database
//     * @return a long value that holds the difference in milliseconds between the current date, and
//     * the expiration date
//     */
////    @RequiresApi(api = Build.VERSION_CODES.N)
////    public Long getDayDifference(String recipeIngredients) {
////        Date today = new Date(); //todays date
////        Date exprDate = stringToDate(recipeIngredients); //expiration date
////        long diffInMillis = (long) ((exprDate.getTime() - today.getTime()));
////        //Log.i("Time difference: ", "Todays date: " + today + " \nExpiration Date: " + exprDate + " \nDifference: " + diffInDays + " days\n DIFF IN MILLIS: " + diffInMillis);
////
////        return diffInMillis;
////    }
//
//    /***
//     * method to format the time for the countdown timer TextView
//     * @param milliseconds accepts milliseconds as a parameter
//     * @returns a string of the date in format dd:hh:mm:ss
////     */
////    private String formatMilliSecondsToTime(long milliseconds) {
////
////        int seconds = (int) (milliseconds / 1000) % 60;
////        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
////        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
////        int days = (int) ((milliseconds /= DateUtils.DAY_IN_MILLIS));
////        return twoDigitString(days) + " : " + twoDigitString(hours) + " : " + twoDigitString(minutes) + " : "
////                + twoDigitString(seconds);
////    }
//
////    /***
////     * method to handle the two digit time strings
////     * @param number
////     * @returns the formatted number
////     */
////    private String twoDigitString(long number) {
////        if (number == 0) { return "00"; }
////        if (number / 10 == 0) { return "0" + number; }
////
////        return String.valueOf(number);
////    }
////}
//
//
//
