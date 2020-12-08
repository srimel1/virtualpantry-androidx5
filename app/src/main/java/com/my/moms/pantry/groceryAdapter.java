/*
 * Copyright (c) 2020.. Stephanie Rimel
 */

package com.my.moms.pantry;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.apache.commons.text.WordUtils;


/***
 *Custom FirebaseRecyclerAdapter
 *
 * Firebase Recycler Adapter is a class from firebase UI
 * to provide the methods to bind, change and display the
 * contents of a firebase database in a recycler view
 */
class groceryAdapter extends FirebaseRecyclerAdapter<groceryItem, groceryAdapter.groceryViewholder> {

    Context mContext;
    /***
     * method that tells the class which layout
     * @param parent
     * @param viewType
     * @return custom new Firebase foodAdapter to interface
     * with recycler
     */

    @NonNull
    @Override
    public groceryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grocery_recycler_item, parent, false);

        return new com.my.moms.pantry.groceryAdapter.groceryViewholder(view);
    }

    /***
     *
     * subclass to create reference to the layout pantry_recycler_item.xml
     */
    public static class groceryViewholder extends RecyclerView.ViewHolder {
        public TextView grocery_name;
        public ImageView grocery_avatar;
        public View mView;



        /***
         * custom view holder for food item
         * @param itemView hold the view
         */
        public groceryViewholder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            grocery_name = itemView.findViewById(R.id.grocery_text);
            grocery_avatar = itemView.findViewById(R.id.grocery_avatar);

        }


        /***
         * toString() method for food model name
         * @return model name string
         */
        @Override
        public String toString() {
            return super.toString() + " '" + grocery_name.getText();
        }
    }

    /***
     * groceryAdapter constructor
     * @param options to customize the adapter
     */
    public groceryAdapter(@NonNull FirebaseRecyclerOptions<groceryItem> options) {
        super(options);
    }

    /***
     * Method to bind view to the layout with data from the food model class
     * @param holder holds the view
     * @param position tracks position
     * @param model food model to bind database elements
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onBindViewHolder(@NonNull groceryAdapter.groceryViewholder holder,
                                    int position, @NonNull groceryItem model) {

        String name = WordUtils.capitalize(model.getName());

        mContext = holder.mView.getContext();

        holder.grocery_name.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));
        holder.mView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));
        holder.grocery_avatar.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));

        holder.grocery_name.setText(name);


        Log.i(model.getName(), "name: " + model.getName() + " position: " + position);
        Log.i(model.getQuantity(), "quantity: " + model.getQuantity() + " position: " + position);
        Log.i(model.getDate(), "date: " + model.getDate() + " position: " + position);

        /***
         * On click event to pass firebase data from the viewholder to the GroceryDetailActivity
         * class. Binds the model data to the EXTRA_NAME variable in GroceryDetailActivity
         */
        holder.mView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, GroceryDetailActivity.class);
            Log.i(GroceryDetailActivity.EXTRA_NAME, "model.getName: " + model.getName());
            Log.i(GroceryDetailActivity.EXTRA_QUANTITY, "model.getQuantity: " + model.getQuantity());
            Log.i(GroceryDetailActivity.EXTRA_DATE, "model.getDate: " + model.getDate());



            /* Send the database date to the Detail Activity through Intent */
            intent.putExtra(GroceryDetailActivity.EXTRA_NAME, model.getName());
            intent.putExtra(GroceryDetailActivity.EXTRA_QUANTITY, model.getQuantity());
            intent.putExtra(GroceryDetailActivity.EXTRA_DATE, model.getDate());

            /* start the activity */
            context.startActivity(intent);
        });

        // set random avatar image
        RequestOptions options = new RequestOptions();
        Glide.with(holder.grocery_avatar.getContext())
                .load(groceryItem.getAvatar())
                .apply(options.fitCenter())
                .into(holder.grocery_avatar);

    }


}
