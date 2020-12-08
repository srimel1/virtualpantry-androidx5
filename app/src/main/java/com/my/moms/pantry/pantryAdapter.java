package com.my.moms.pantry;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;


/***
 *Custom FirebaseRecyclerAdapter
 *
 * Firebase Recycler Adapter is a class from firebase UI
 * to provide the methods to bind, change and display the
 * contents of a firebase database in a recycler view
 */
class pantryAdapter extends FirebaseRecyclerAdapter<pantryItem, pantryAdapter.foodsViewholder> implements Filterable{

    List<pantryItem> mData ;
    List<pantryItem> mDataFiltered ;
    Context context;
    View v;

    
    /***
     * method that tells the class which layout
     * @param parent
     * @param viewType
     * @return custom new Firebase foodAdapter to interface
     * with recycler
     */
    @NonNull
    @Override
    public foodsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pantry_recycler_item, parent, false);
        return new pantryAdapter.foodsViewholder(view);
    }


//    @Override
//    public int getItemCount() {
//        return mDataFiltered.size();
//    }

    @Override
    public Filter getFilter() {

        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData ;
                    Log.i("mDataFiltered", "mDatafiltered: "+ mDataFiltered);
                    Log.i("mData", "mData: "+mData);

                }
                else {
                    List<pantryItem> lstFiltered = new ArrayList<>();
                    for (pantryItem row : mData) {

                        if (row.getName().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }

                    }

                    mDataFiltered = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values= mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered = (List<pantryItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }



    /***
     *
     * subclass to create reference to the layout pantry_recycler_item.xml
     */
    public static class foodsViewholder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView avatar;
        public View mView;

        /***
         * custom view holder for food item
         * @param itemView hold the view
         */
        public foodsViewholder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            name = itemView.findViewById(R.id.pantry_text);
            avatar = itemView.findViewById(R.id.pantry_avatar);

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
    public pantryAdapter(@NonNull FirebaseRecyclerOptions<pantryItem> options) {
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
    protected void onBindViewHolder(@NonNull pantryAdapter.foodsViewholder holder,
                                    int position, @NonNull pantryItem model) {

        context = holder.mView.getContext();


        holder.name.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));
        holder.mView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));

//        holder.name.setText(mDataFiltered.get(position).getName());
        holder.name.setText(WordUtils.capitalize(model.getName()));

//        holder.avatar.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
//        holder.name.setText(mDataFiltered.get(position).getName());
//        newsViewHolder.tv_content.setText(mDataFiltered.get(position).getContent());
//        newsViewHolder.tv_date.setText(mDataFiltered.get(position).getDate());
//        newsViewHolder.img_user.setImageResource(mDataFiltered.get(position).getUserPhoto());



//        Log.i(model.getName()+ "from foodadapter ", "name: position " + position);
//        Log.i(model.getLifecycle()+" from foodadapter  ", "quantty: position " + position);
//        Log.i(model.getQuantity()+" from foodadapter  ", "lifecycle: position " + position);

        /***
         * On click event to pass firebase data from the viewholder to the PantryDetailActivity
         * class. Binds the model data to the EXTRA_NAME variable in PantryDetailActivity
         */
        holder.mView.setOnClickListener(v -> {

            context = v.getContext();

            Intent intent = new Intent(context, PantryDetailActivity.class);
            Log.i(PantryDetailActivity.EXTRA_NAME, "model.getName: " + model.getName());
            Log.i(PantryDetailActivity.EXTRA_LIFECYCLE, "model.getLifecycle: " + model.getLifecycle());
            Log.i(PantryDetailActivity.EXTRA_QUANTITY, "model.getQuantity: " + model.getQuantity());
            Log.i(PantryDetailActivity.EXTRA_DATE, "model.getDate: " + model.getDate());
            Log.i(PantryDetailActivity.EXTRA_DATE, "model.getExpirationDate: " + model.getExpirationDate());


            /* Send the database date to the Detail Activity through Intent */
            intent.putExtra(PantryDetailActivity.EXTRA_NAME, model.getName());
            intent.putExtra(PantryDetailActivity.EXTRA_QUANTITY, model.getQuantity());
            intent.putExtra(PantryDetailActivity.EXTRA_LIFECYCLE, model.getLifecycle());
            intent.putExtra(PantryDetailActivity.EXTRA_DATE, model.getDate());
            intent.putExtra(PantryDetailActivity.EXTRA_EXPIRATION, model.getExpirationDate());

            context.startActivity(intent);
        });

        // set random avatar image
        RequestOptions options = new RequestOptions();
        Glide.with(holder.avatar.getContext())
                .load(pantryItem.getAvatar())
                .apply(options.fitCenter())
                .into(holder.avatar);

//         we apply animation to views here
//         first lets create an animation for user photo
//        holder.avatar.setAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_transition_animation));

//         lets create the animation for the whole card
//         first lets create a reference to it
//         you ca use the previous same animation like the following
//
//         but i want to use a different one so lets create it ..

    }




}
