package com.example.projectmanagement.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projectmanagement.R;

import java.util.LinkedList;


public class PropertyListAdapter extends RecyclerView.Adapter<PropertyListAdapter.SliderViewHolder> {

    private Context context;
    private LinkedList<String> properties;
    private ItemClickListener mClickListener;


    public PropertyListAdapter(Context context, LinkedList<String> properties) {
        this.properties = properties;
        this.context = context;
    }


    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SliderViewHolder(LayoutInflater.from(context).inflate(R.layout.property_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }




}
