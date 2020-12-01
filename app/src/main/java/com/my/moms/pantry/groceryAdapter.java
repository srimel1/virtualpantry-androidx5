package com.my.moms.pantry;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


/***
 *Custom FirebaseRecyclerAdapter
 *
 * Firebase Recycler Adapter is a class from firebase UI
 * to provide the methods to bind, change and display the
 * contents of a firebase database in a recycler view
 */
class groceryAdapter extends FirebaseRecyclerAdapter<grocery, groceryAdapter.groceryViewHolder> {

    /***
     *
     * subclass to create reference to the layout pantry_recycler_item.xml
     */
    public static class groceryViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView avatar;
        public View mView;


        /***
         * custom view holder for grocery item
         * @param itemView hold the view
         */
        public groceryViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            name = itemView.findViewById(R.id.text2);
            avatar = itemView.findViewById(R.id.grocery_avatar);
        }

        /***
         * toString() method for grocery model name
         * @return model name string
         */
        @Override
        public String toString() {
            return super.toString() + " '" + name.getText();
        }
    }

    /***
     * groceryAdapter constructor
     * @param options to customize the adapter
     */
    public groceryAdapter(@NonNull FirebaseRecyclerOptions<grocery> options) {
        super(options);
    }

    /***
     * Method to bind view to the layout with data from the grocery model class
     * @param holder holds the view
     * @param position tracks position
     * @param model grocery model to bind database elements
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onBindViewHolder(@NonNull groceryAdapter.groceryViewHolder holder,
                                    int position, @NonNull grocery model) {


        holder.name.setText(model.getName());


        Log.i(model.getName(), "name: position " + position);
        Log.i(model.getLifecycle(), "quantty: position " + position);
        Log.i(model.getQuantity(), "lifecycle: position " + position);

        /***
         * On click event to pass firebase data from the viewholder to the GroceryListDetailActivity
         * class. Binds the model data to the EXTRA_NAME22 variable in GroceryListDetailActivity
         */
        holder.mView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, GroceryListDetailActivity.class);
            Log.i(GroceryListDetailActivity.EXTRA_NAME2, "model.getName: " + model.getName());
            Log.i(GroceryListDetailActivity.EXTRA_LIFECYCLE2, "model.getLifecycle: " + model.getLifecycle());
            Log.i(GroceryListDetailActivity.EXTRA_QUANTITY2, "model.getQuantity: " + model.getQuantity());
            Log.i(GroceryListDetailActivity.EXTRA_DATE2, "model.getDate: " + model.getDate());
            Log.i(GroceryListDetailActivity.EXTRA_EXPIRATION2, "model.getExpirationDate: " + model.getExpirationDate());


            /* Send the database date to the Detail Activity through Intent */
            intent.putExtra(GroceryListDetailActivity.EXTRA_NAME2, model.getName());
            intent.putExtra(GroceryListDetailActivity.EXTRA_QUANTITY2, model.getQuantity());
            intent.putExtra(GroceryListDetailActivity.EXTRA_LIFECYCLE2, model.getLifecycle());
            intent.putExtra(GroceryListDetailActivity.EXTRA_DATE2, model.getDate());
            intent.putExtra(GroceryListDetailActivity.EXTRA_EXPIRATION2, model.getExpirationDate());

            context.startActivity(intent);
        });

        // set random avatar image
        RequestOptions options = new RequestOptions();
        Glide.with(holder.avatar.getContext())
                .load(grocery.getRandFoodImage())
                .apply(options.fitCenter())
                .into(holder.avatar);

    }

    /***
     * method that tells the class which layout
     * @param parent
     * @param viewType
     * @return custom new Firebase groceryAdapter to interface
     * with recycler
     */
    @NonNull
    @Override
    public com.my.moms.pantry.groceryAdapter.groceryViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grocery_recycler_item, parent, false);
        return new com.my.moms.pantry.groceryAdapter.groceryViewHolder(view);
    }
}

