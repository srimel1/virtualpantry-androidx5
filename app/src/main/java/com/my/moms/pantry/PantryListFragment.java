package com.my.moms.pantry;

import android.os.Bundle;
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
 * Class to handle the Pantry List
 * Recyclerview in fragment with
 * firebase database and custom adapter
 */
public class PantryListFragment extends Fragment {

    // Add RecyclerView member
    private RecyclerView recyclerView;

    foodAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the


    // Firebase Realtime Database
//    Query query = FirebaseDatabase.getInstance()
//            .getReference()
//            .child("Foods");


    /***
     * Method to inflate the recycler view with each sub view
     * @param inflater to inflate the layout
     * @param container to contain the layout
     * @param savedInstanceState to save the machine state
     * @return view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_food_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler1);
        setUpRecyclerView();
        return view;
    }

    /***
     * Method to instantiate the recyclerview and bind the data
     * from firebase database to each list item container
     */
    public void setUpRecyclerView() {
        mbase = FirebaseDatabase.getInstance().getReference();




        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<food> options = new FirebaseRecyclerOptions.Builder<food>()
                .setQuery(mbase, food.class)
                .build();
        adapter = new foodAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    /***
     * initialized the recyclerview and loads the view
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
         * foodAdapter constructor
         * @param options to customize the adapter
         */
        public foodAdapter(
                @NonNull FirebaseRecyclerOptions<food> options) {
            super(options);
        }

        // Function to bind the view in Card view(here
        // "food.xml") with data in
        // model class(here "food.class")
//    could potentially be a problem because i used the linear layout and not cardview

        /***
         * Method to bind view to the layout
         * @param holder holds the view
         * @param position tracks position
         * @param model food model to bind detabase elements
         */
        @Override
        protected void
        onBindViewHolder(@NonNull com.my.moms.pantry.foodAdapter.foodsViewholder holder,
                         int position, @NonNull food model) {

            // Add firstname from model class (here
            // "food.class")to appropriate view in Card
            // view (here "food.xml")

            // item name
            holder.name.setText(model.getName());
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, PantryDetailActivity.class);
//                intent.putExtra(PantryDetailActivity.EXTRA_NAME, holder.mBoundString);
//
//                context.startActivity(intent);
//            }
//        });

            // avatar image
            RequestOptions options = new RequestOptions();
            Glide.with(holder.avatar.getContext())
                    .load(food.getRandFoodImage())
                    .apply(options.fitCenter())
                    .into(holder.avatar);


//        // Add lastname from model class (here
//        // "food.class")to appropriate view in Card
//        // view (here "food.xml")
//        holder.lastname.setText(model.getName());
//
//        // Add age from model class (here
//        // "food.class")to appropriate view in Card
//        // view (here "food.xml")
//        holder.age.setText(model.getQuantity());
//
//        // add lifecycle from model class
//        //"food.class")to appropriate view in Card
//        // view (here "food.xml")
//        holder.age.setText(model.getLifecycle());
        }

        // Function to tell the class about the Card view (here
        // "food.xml")in
        // which the data will be shown


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
                    .inflate(R.layout.food, parent, false);
            return new com.my.moms.pantry.foodAdapter.foodsViewholder(view);
        }

        // Sub Class to create references of the views in Crad
        // view (here "food.xml")

        /***
         *
         * subclass to create referece to the layout food.xml
         */
        class foodsViewholder extends RecyclerView.ViewHolder {
            TextView name;
            ImageView avatar;

            public foodsViewholder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.text1);
                avatar = itemView.findViewById(R.id.avatar);


            }
        }

    }
