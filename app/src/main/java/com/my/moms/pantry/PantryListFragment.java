package com.my.moms.pantry;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/***
 * PantryListFragment Class to handle the Pantry List
 * Recyclerview in a fragment with
 * firebase database and custom adapter
 * and to pass firebase data to PantryDetailActivity
 */
public class PantryListFragment extends Fragment {


    private RecyclerView recyclerView; // add recyclerView member

    foodAdapter adapter; // Create Object of the Adapter class
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

        View view = inflater.inflate(R.layout.pantry_recycler_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler1);
        setUpRecyclerView();
        return view;
    }

    /***
     * Method to instantiate the recyclerview and bind the data
     * from firebase database to each each list item container
     */
    public void setUpRecyclerView() {
        //query to get items from the database in Pantry child
        mbase = FirebaseDatabase.getInstance().getReference("Pantry");

        //set the recyclerView layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<food> options = new FirebaseRecyclerOptions.Builder<food>()
                .setQuery(mbase, food.class)
                .build();

        //initialize the adapter
        adapter = new foodAdapter(options);

        //set the custom adapter in the recyclerView
        recyclerView.setAdapter(adapter);
    }

    /***
     * starts the adapter and tells it to start listening
     */
    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    /***
     * tells the adapter when to stop
     */
    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}