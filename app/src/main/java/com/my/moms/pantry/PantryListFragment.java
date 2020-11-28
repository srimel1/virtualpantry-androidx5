package com.my.moms.pantry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        FirebaseRecyclerOptions<food> options = new FirebaseRecyclerOptions.Builder<food>()
                .setQuery(mbase, food.class)
                .build();

        adapter = new foodAdapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
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
//
//    public static class SimpleStringRecyclerViewAdapter
//            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {
//
//        private final TypedValue mTypedValue = new TypedValue();
//        private int mBackground;
//        private List<String> mValues;
//
//        public static class ViewHolder extends RecyclerView.ViewHolder {
//            public String mBoundString;
//
//            public final View mView;
//            public final ImageView mImageView;
//            public final TextView mTextView;
//
//            public ViewHolder(View view) {
//                super(view);
//                mView = view;
//                mImageView = view.findViewById(R.id.avatar);
//                mTextView = view.findViewById(android.R.id.text1);
//            }
//
//            @Override
//            public String toString() {
//                return super.toString() + " '" + mTextView.getText();
//            }
//        }
//
//        public SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
//            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//            mBackground = mTypedValue.resourceId;
//            mValues = items;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.food, parent, false);
//            view.setBackgroundResource(mBackground);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
//            holder.mBoundString = mValues.get(position);
//            holder.mTextView.setText(mValues.get(position));
//
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, PantryDetailActivity.class);
//                    intent.putExtra(PantryDetailActivity.EXTRA_NAME, holder.mBoundString);
//
//                    context.startActivity(intent);
//                }
//            });
//
//            RequestOptions options = new RequestOptions();
//            Glide.with(holder.mImageView.getContext())
//                    .load(food.getRandFoodImage())
//                    .apply(options.fitCenter())
//                    .into(holder.mImageView);
//        }
//
//
//
//        @Override
//        public int getItemCount() {
//            return mValues.size();
//        }
//    }
//
//
//}
