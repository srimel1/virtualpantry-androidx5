package com.my.moms.pantry;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/***
 * PantryListFragment Class to handle the Pantry List
 * Recyclerview in a fragment with
 * firebase database and custom adapter
 * and to pass firebase data to PantryDetailActivity
 */
public class PantryFragment extends Fragment {

    EditText searchInput ;
    private int pos;
    public void PantryFragment(int position){
        this.pos = position;
    }

    CharSequence search="";
    private RecyclerView recyclerView; // add recyclerView member
    WaveSideBar waveSideBar;

    ConstraintLayout rootLayout;

    pantryAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create reference to the database

    private ArrayList<pantryItem> items = new ArrayList<>();

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
        recyclerView = (RecyclerView) view.findViewById(R.id.pantry_recycler);
        rootLayout = view.findViewById(R.id.root_layout);
        searchInput = view.findViewById(R.id.search_input);
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
        FirebaseRecyclerOptions<pantryItem> options = new FirebaseRecyclerOptions.Builder<pantryItem>()
                .setQuery(mbase, pantryItem.class)
                .build();

        //initialize the adapter
        adapter = new pantryAdapter(options);

        //set the custom adapter in the recyclerView
        recyclerView.setAdapter(adapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                adapter.getFilter().filter(s);
                Log.i("adapter getfilter", "S "+ s+ " Start: "+start+" Before: "+ before+" Count: "+count);
                search = s;
                Log.i("Search: ", " Search: " + search);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




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