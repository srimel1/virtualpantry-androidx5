//package com.my.moms.pantry;
//
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//
//import java.util.List;
//
//public class Fadapter extends FirebaseRecyclerAdapter<Foods, Fadapter.FoodViewHolder> {
//    private final TypedValue mTypedValue = new TypedValue();
//    private int mBackground;
//    private List<String> mValues;
//    View v;
//
//    public Fadapter(@NonNull FirebaseRecyclerOptions<Foods> options){
//        super(options);
//    }
//
//    @NonNull
//    @Override
//    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_food_list, parent, false);
//
//        return new FoodViewHolder(v);
//    }
//
//
//
//    @Override
//    protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Foods foods) {
//        holder.mBoundString = mValues.get(position);
//        holder.mTextView.setText(mValues.get(position));
//
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
//
//        RequestOptions options = new RequestOptions();
//        Glide.with(holder.mImageView.getContext())
//                .load(Foods.getRandFoodImage())
//                .apply(options.fitCenter())
//                .into(holder.mImageView);
//
//    }
//
//    public class FoodViewHolder extends RecyclerView.ViewHolder{
//
//        public String mBoundString;
//        public final View mView;
//        public final ImageView mImageView;
//        public final TextView mTextView;
//
//    public FoodViewHolder(View view) {
//            super(view);
//            mView = view;
//            mImageView = view.findViewById(R.id.avatar);
//            mTextView = view.findViewById(android.R.id.text1);
//        }
//
//    public FoodViewHolder(@NonNull View itemView, View mView, ImageView mImageView, TextView mTextView) {
//            super(itemView);
//            this.mView = mView;
//            this.mImageView = mImageView;
//            this.mTextView = mTextView;
//        }
//    }
//
//
//
//
//}
//
//
