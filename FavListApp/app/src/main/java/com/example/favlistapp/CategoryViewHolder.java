package com.example.favlistapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private TextView txtCategoryNumber;
    private TextView txtCategoryName;

    public CategoryViewHolder(View view){
        super(view);

        txtCategoryNumber = view.findViewById(R.id.category_number_txt);
        txtCategoryName = view.findViewById(R.id.category_name_txt);
    }
}
