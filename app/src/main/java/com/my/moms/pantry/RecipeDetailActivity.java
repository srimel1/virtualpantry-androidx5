
/*
 * Copyright (c) 2020.. Stephanie Rimel
 */

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

import org.apache.commons.text.WordUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_DESCRIPTION = "quantity";
    public static final String EXTRA_STEPS = "lifecycle";
    public static final String EXTRA_SERVING = "serving";
    public static final String EXTRA_INGREDIENTS = "expiration";
    public static final String EXTRA_DATE = "date";


    /***
     * Set the view
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        //set the simple date format
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        SimpleDateFormat month = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);




        //get the data from th database that was passed through intent in pantrylistfragment
        Intent intent = getIntent();
        final String recipeName = WordUtils.capitalize(intent.getStringExtra(EXTRA_NAME));
        final String recipeDescription = intent.getStringExtra(EXTRA_DESCRIPTION);
        final String recipeSteps = intent.getStringExtra(EXTRA_STEPS);
        final String recipeServing = intent.getStringExtra(EXTRA_SERVING);
        final String recipeIngredients = intent.getStringExtra(EXTRA_INGREDIENTS);
        final String recipeDate = intent.getStringExtra(EXTRA_DATE);



        Date dateAdded = null;
        try {
            dateAdded = sdf.parse(recipeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formatDate = month.format(dateAdded);

        Log.i(recipeName," steps: "+recipeSteps);
        Log.i(recipeName," description: "+recipeDescription);
        Log.i(recipeName," serving: "+recipeServing);
        Log.i(recipeName," ingredients: "+recipeIngredients);
        Log.i(recipeName," date: "+recipeDate+" Formatted Date: "+formatDate);


        final Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle(recipeName);


        Log.i(recipeName, " RecipeList Steps: " + recipeSteps);
        Log.i(recipeName, " RecipeList Description: " + recipeDescription);
        Log.i(recipeName, " RecipeList Serving: " + recipeServing);
        Log.i(recipeName, " RecipeList Ingredients: " + recipeIngredients);
        Log.i(recipeName, " RecipeList Date: " + recipeDate);



        //get todays date for the grocery list insertion
        Date today = Calendar.getInstance().getTime();
//        String dateMonth = month.format(recipeDate);
//        String dateAdded = sdf.format(today);




        //bind the database query to the Card view
        final TextView description = (TextView) findViewById(R.id.description);
        final TextView serving = (TextView) findViewById(R.id.serving);
        final TextView steps = (TextView) findViewById(R.id.steps);
        final TextView ingredients = (TextView) findViewById(R.id.ingredients);
        final TextView date = (TextView) findViewById(R.id.date);

        date.setText(formatDate);
        serving.setText(recipeServing);




        description.setText(recipeDescription); //sets description date in cardview
        steps.setText(recipeSteps); //sets steps of items in cardview
        //serving.setText(recipeServing); //sets serving in cardview
        ingredients.setText(recipeIngredients); //sets ingredients in cardview
        //date.setText(dateMonth); //date added to cardview





        //add to grocery list item
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            //insert into database
//            FirebaseDatabase.getInstance().getReference("Recipe List")
//                    .child(recipeName)
//                    .setValue(new food(recipeName, recipeDescription, recipeSteps, dateAdded, recipeIngredients));

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





















