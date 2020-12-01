package com.my.moms.pantry;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/***
 * PantryListFragment Class to handle the Pantry List
 * Recyclerview in a fragment with
 * firebase database and custom adapter3
 * and to pass firebase data to PantryDetailActivity
 */
public class RecipeFragment extends Fragment {


    private RecyclerView recyclerView; // add recyclerView member

    recipeAdapter adapter3; // Create Object of the Adapter class
    DatabaseReference mbase; // Create reference to the database

    /***
     * Method to inflate the recycler view with each sub view
     * @param inflater to inflate the layout
     * @param container to contain the layout
     * @param savedInstanceState to save the machine state
     * @return view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipe_recycler_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewrecipe);
        setUpRecyclerView();
        return view;
    }

    /***
     * Method to instantiate the recyclerview and bind the data
     * from firebase database to each each list item container
     */
    public void setUpRecyclerView() {
        //query to get items from the database in Pantry child
        mbase = FirebaseDatabase.getInstance().getReference("Recipes");

        //set the recyclerView layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<recipe> options = new FirebaseRecyclerOptions.Builder<recipe>()
                .setQuery(mbase, recipe.class)
                .build();

        //initialize the adapter3
        adapter3 = new recipeAdapter(options);

        //set the custom adapter3 in the recyclerView
        recyclerView.setAdapter(adapter3);
    }

    /***
     * starts the adapter3 and tells it to start listening
     */
    @Override
    public void onStart() {
        super.onStart();
        if (adapter3 != null) {
            adapter3.startListening();
        }
    }

    /***
     * tells the adapter3 when to stop
     */
    @Override
    public void onStop() {
        super.onStop();
        if (adapter3 != null) {
            adapter3.stopListening();
        }
    }
}