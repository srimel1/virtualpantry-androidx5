package com.my.moms.pantry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    // Add RecyclerView member
    private RecyclerView recyclerView;

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

/***
 *Custom FirebaseRecyclerAdapter
 *
 * Firebase Recycler Adapter is a class from firebase UI
 * to provide the methods to bind, change and display the
 * contents of a firebase database in a recycler view
 */
class foodAdapter extends FirebaseRecyclerAdapter<food, foodAdapter.foodsViewholder> {

    /***
     *
     * subclass to create reference to the layout pantry_recycler_item.xml
     */
    public static class foodsViewholder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView avatar;
        public View mView;
        public TextView lifecycle;
        public TextView quantity;


        /***
         * custom view holder for food item
         * @param itemView hold the view
         */
        public foodsViewholder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            name = itemView.findViewById(R.id.text1);
            avatar = itemView.findViewById(R.id.avatar);
        }

        /***
         * toString() method for food model name
         * @return model name string
         */
        @Override
        public String toString() {
            return super.toString() + " '" + name.getText();
        }
    }

    /***
     * foodAdapter constructor
     * @param options to customize the adapter
     */
    public foodAdapter(@NonNull FirebaseRecyclerOptions<food> options) {
        super(options);
    }

    /***
     * Method to bind view to the layout with data from the food model class
     * @param holder holds the view
     * @param position tracks position
     * @param model food model to bind database elements
     */
    @Override
    protected void onBindViewHolder(@NonNull foodAdapter.foodsViewholder holder,
                                    int position, @NonNull food model) {


        // adds the food item name from model class to view (pantry_recycler_item.xml)
        holder.name.setText(model.getName());
//        holder.quantity.setText(model.getQuantity());
//        holder.lifecycle.setText(model.getQuantity());

        Log.i(model.getName(), "name: position " + position);
        Log.i(model.getLifecycle(), "quantty: position " + position);
        Log.i(model.getQuantity(), "lifecycle: position " + position);

        /***
         * On click event to pass firebase data from the viewholder to the PantryDetailActivity
         * class. Binds the model data to the EXTRA_NAME variable in PantryDetailActivity
         */
        holder.mView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, PantryDetailActivity.class);
            Log.i(PantryDetailActivity.EXTRA_NAME, "model.getName: " + model.getName());
            Log.i(PantryDetailActivity.EXTRA_LIFECYCLE, "model.getLifecycle: " + model.getLifecycle());
            Log.i(PantryDetailActivity.EXTRA_QUANTITY, "model.getQuantity: " + model.getQuantity());
            Log.i(PantryDetailActivity.EXTRA_DATE, "model.getDate: " + model.getDate());


            intent.putExtra(PantryDetailActivity.EXTRA_NAME, model.getName());
            intent.putExtra(PantryDetailActivity.EXTRA_QUANTITY, model.getQuantity());
            intent.putExtra(PantryDetailActivity.EXTRA_LIFECYCLE, model.getLifecycle());
            intent.putExtra(PantryDetailActivity.EXTRA_DATE, model.getDate());


            context.startActivity(intent);
        });

        // avatar image
        RequestOptions options = new RequestOptions();
        Glide.with(holder.avatar.getContext())
                .load(food.getRandFoodImage())
                .apply(options.fitCenter())
                .into(holder.avatar);


//        // Add lastname from model class (here
//        // "pantry_recycler_item.class")to appropriate view in Card
//        // view (here "pantry_recycler_item.xml")
//        holder.lastname.setText(model.getName());
//
//        // Add age from model class (here
//        // "pantry_recycler_item.class")to appropriate view in Card
//        // view (here "pantry_recycler_item.xml")
//        holder.age.setText(model.getQuantity());
//
//        // add lifecycle from model class
//        //"pantry_recycler_item.class")to appropriate view in Card
//        // view (here "pantry_recycler_item.xml")
//        holder.age.setText(model.getLifecycle());
    }

    /***
     * method that tells the classs which layout
     * @param parent
     * @param viewType
     * @return custom new Firebase foodAdapter to interface
     * with recycler
     */
    @NonNull
    @Override
    public com.my.moms.pantry.foodAdapter.foodsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pantry_recycler_item, parent, false);
        return new com.my.moms.pantry.foodAdapter.foodsViewholder(view);
    }
}