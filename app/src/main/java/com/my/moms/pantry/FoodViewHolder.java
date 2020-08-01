package com.my.moms.pantry;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class FoodViewHolder extends RecyclerView.ViewHolder {
    public String mBoundString;
    public  View mView;
    public  ImageView mImageView;
    public  TextView mTextView;




    public FoodViewHolder(@NonNull View v) {
        super(v);
        this.mView = v;
        mTextView = itemView.findViewById(android.R.id.text1);
        mImageView = itemView.findViewById(R.id.avatar);

    }

    public void bind(Foods food) {
        mTextView.setText(food.getmName());

    }


}