package com.example.projectmanagement.pages.landlord.home.pages.tenant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectmanagement.R;
import com.example.projectmanagement.models.Models;
import com.example.projectmanagement.unused.Model;


import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class TenantRvAdapter extends RecyclerView.Adapter<TenantRvAdapter.ViewHolder> {

    private final LinkedList<Models.Tenant_Person> personlist;
    private final LinkedList<Models.Tenant_Company> companyLinkedList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;


    public TenantRvAdapter(LinkedList<Models.Tenant_Person> personlist, LinkedList<Models.Tenant_Company> companyLinkedList, Context mContext) {
        this.personlist = personlist;
        this.companyLinkedList = companyLinkedList;
        this.mContext = mContext;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tenant_list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return personlist.size() + companyLinkedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView userDp;
        TextView userName, userEmail, userPosition;

        ViewHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}