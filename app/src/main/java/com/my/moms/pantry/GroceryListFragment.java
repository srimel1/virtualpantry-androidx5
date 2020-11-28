//package com.my.moms.pantry;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class GroceryListFragment extends Fragment {
//
//    private DatabaseReference mRef;
//    private FirebaseRecyclerAdapter<food, SimpleStringRecyclerViewAdapter.ViewHolder> mFirebaseAdapter;
//
//    private List<food> foodList;
//    private List<String> nameList;
//    private String[] array;
//    Query query = FirebaseDatabase.getInstance()
//            .getReference()
//            .child("Pantry");
//
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref = database.getReference("Pantry");
//
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        RecyclerView rv = (RecyclerView) inflater.inflate(
//                R.layout.frag_food_list, container, false);
//        setupRecyclerView(rv);
//        return rv;
//    }
//
//    private void setupRecyclerView(RecyclerView recyclerView) {
//
//        foodList = new ArrayList<>();
//
//        nameList = new ArrayList<>();
//
//
//        //ref.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                Map<String, Foods> td = (HashMap<String,Foods>) dataSnapshot.getValue();
////
////                foodList = td.values();
////
////                //notifyDataSetChanged();
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////                Log.w("Read failed", "Failed to read value.", databaseError.toException());
////
////            }
////
////        });
//
//
//
//
//
////        ref.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                Log.e("Count " ,""+snapshot.getChildrenCount());
////                foodList.clear();
////                nameList.clear();
////                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
////                    Foods food  = postSnapshot.getValue(Foods.class);
////                    foodList.add(food);
////                    nameList.add(food.name);
////                    Log.d("list", nameList.toString());
////                    Log.e("Get Data", food.name);
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////                Log.w("Read failed", "Failed to read value.", databaseError.toException());
////
////            }
////        });
//
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                Log.e("Count " ,""+snapshot.getChildrenCount());
//                foodList.clear();
//                nameList.clear();
//                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                    food food  = postSnapshot.getValue(com.my.moms.pantry.food.class);
//                    foodList.add(food);
//                    nameList.add(food.name);
//                    Log.d("list", nameList.toString());
//                    Log.e("Get Data", food.name);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w("Read failed", "Failed to read value.", error.toException());
//
//            }
//
//        });
//        String[] arr = GetStringArray(nameList);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//        recyclerView.setAdapter(new PantryListFragment.SimpleStringRecyclerViewAdapter(getActivity(),
//                getRandomSublist(arr, arr.length)));   ;
//    }
//
//    public static String[] GetStringArray(List<String> arr)
//    {
//
//        // declaration and initialise String Array
//        String str[] = new String[arr.size()];
//
//        // ArrayList to Array Conversion
//        for (int j = 0; j < arr.size(); j++) {
//
//            // Assign each value to String array
//            str[j] = arr.get(j);
//        }
//
//        return str;
//    }
//
//    private List<String> getRandomSublist(String[] array, int amount) {
//        ArrayList<String> list = new ArrayList<>(amount);
//        Random random = new Random();
//        while (list.size() < amount) {
//            list.add(array[random.nextInt(array.length)]);
//        }
//        return list;
//    }
//
////    private List<String> getRandomSublist(String[] arr, int amount) {
////        array = nameList.toArray(new String[0]);
////
////        Random random = new Random();
////        while (nameList.size() < amount) {
////            nameList.add(nameList.get(random.nextInt(nameList.size())));
////        }
////        return nameList;
////    }
//
//
//
//        }
//
//
//
//
//
//
//
//
//
//
//    class SimpleStringRecyclerViewAdapter
//            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {
//
//        private final TypedValue mTypedValue = new TypedValue();
//        private int mBackground;
//        private List<String> mValues;
//
//        public static class ViewHolder extends RecyclerView.ViewHolder {
//            public String mBoundString;
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
//                    .inflate(R.layout.list_item, parent, false);
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
//m
//        @Override
//        public int getItemCount() {
//            return mValues.size();
//        }
//    }
//
//
