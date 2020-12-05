
package com.my.moms.pantry;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
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

import org.apache.commons.text.WordUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_DESCRIPTION = "quantity";
    public static final String EXTRA_STEPS = "lifecycle";
    public static final String EXTRA_SERVING = "date";
    public static final String EXTRA_INGREDIENTS = "expiration";


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
        setContentView(R.layout.recipe_detail);


        //get the data from th database that was passed through intent in pantrylistfragment
        Intent intent = getIntent();
        final String recipeName = WordUtils.capitalize(intent.getStringExtra(EXTRA_NAME));
        final String recipeDescription = intent.getStringExtra(EXTRA_DESCRIPTION);
        final String recipeSteps = intent.getStringExtra(EXTRA_STEPS);
        final String recipeServing = intent.getStringExtra(EXTRA_SERVING);
        final String recipeIngredients = intent.getStringExtra(EXTRA_INGREDIENTS);



        Log.i(recipeName," steps: "+recipeSteps);
        Log.i(recipeName," description: "+recipeDescription);
        Log.i(recipeName," serving: "+recipeServing);
        Log.i(recipeName," ingredients: "+recipeIngredients);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("Lasagna");


        Log.i(recipeName, " RecipeList Steps: " + recipeSteps);
        Log.i(recipeName, " RecipeList Description: " + recipeDescription);
        Log.i(recipeName, " RecipeList Serving: " + recipeServing);
        Log.i(recipeName, " RecipeList Ingredients: " + recipeIngredients);


        //get todays date for the grocery list insertion
        Date today = Calendar.getInstance().getTime();
        String dateAdded = sdf.format(today);




        //bind the database query to the Card view
        final TextView description = (TextView) findViewById(R.id.description);
        final TextView serving = (TextView) findViewById(R.id.serving);
        final TextView steps = (TextView) findViewById(R.id.steps);
        final TextView ingredients = (TextView) findViewById(R.id.ingredients);



        description.setText(recipeDescription); //sets purchase date in cardview
        steps.setText(recipeSteps); //sets quantity of items in cardview
        serving.setText(recipeServing); //sets lifecycle in cardview
        ingredients.setText("Click the + button to add all the ingredients to your Grocery List");





        //add to grocery list item
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            //insert into database
            FirebaseDatabase.getInstance().getReference("Recipe List")
                    .child(recipeName)
                    .setValue(new food(recipeName, recipeDescription, recipeSteps, dateAdded, recipeIngredients));

            Snackbar.make(view, "Added " + recipeName + " ingredients to Grocery List ", Snackbar.LENGTH_LONG)
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
     * @param recipeServing this is the final expiration date in Date formal
     * @return a Date
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Date stringToDate(String recipeServing) {
        Date purchaseDate = new Date();
        //Converts the string element date from firebase into a Date
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); //convert String containing the purchase date to a Date object
        try {
            purchaseDate = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").parse(recipeServing);
            //Log.i(purchaseDate.toString(), "purchaseDate before conversion: " + recipeServing + " purchaseDate after: " + purchaseDate + " Different: " + purchaseDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return purchaseDate;
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
//        final String recipeName = intent.getStringExtra(EXTRA_NAME);
//        final String recipeDescription = intent.getStringExtra(EXTRA_QUANTITY);
//        final String recipeSteps = intent.getStringExtra(EXTRA_LIFECYCLE);
//        final String recipeServing = intent.getStringExtra(EXTRA_DATE);
//
//
//        final Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
//
//        collapsingToolbar.setTitle(recipeName);
//
//
//        Log.i(recipeName, " lifecycle: " + recipeSteps);
//        Log.i(recipeName, " quantity: " + recipeDescription);
//        Log.i(recipeName, " date: " + recipeServing);
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
//                    .child(recipeName)
//                    .setValue(new grocery(recipeName, recipeDescription, recipeSteps, dateAdded));
//
//            Snackbar.make(view, "Added " + recipeName + " to Pantry List ", Snackbar.LENGTH_LONG)
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
////            expireBy = sdf.parse(recipeIngredients);
////            purchasedOn = sdf.parse(recipeServing);
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
//
////        String formatLifecycle = month_date.format(expireBy); //converts lifecycle to date with month
////        String formatPurchaseDate = month_date.format(purchasedOn); //converts lifecycle to date with month
//
//        //String formatDate = month_date.format(recipeServing); //converts purhcase date to date with month
//
//
//        // Append the string for lifecycle card view
////        String lifecycleTextView = recipeSteps + " days, " + diffInDays + " days left until expiration date: " + formatLifecycle;
////
////        String purchaseDateTextView = "Added on: " + dateAdded;
//
//
//        date.setText(recipeServing); //sets purchase date in cardview
//        quantity.setText(recipeDescription); //sets quantity of items in cardview
//        lifecycle.setText(recipeSteps); //sets lifecycle in cardview
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
//     * @param recipeServing this is the final expiration date in Date formal
//     * @return a Date
//     */
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public Date stringToDate(String recipeServing) {
//        Date purchaseDate = new Date();
//        //Converts the string element date from firebase into a Date
//        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); //convert String containing the purchase date to a Date object
//        try {
//            purchaseDate = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").parse(recipeServing);
//            //Log.i(purchaseDate.toString(), "purchaseDate before conversion: " + recipeServing + " purchaseDate after: " + purchaseDate + " Different: " + purchaseDate.getTime());
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
//        final String recipeName = intent.getStringExtra(EXTRA_NAME);
//
//        final Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(recipeName);
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
