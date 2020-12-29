package com.example.projectmanagement.pages.tenant.services;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projectmanagement.R;


public class TenantServicesGrid extends BaseAdapter {

    //todo icons names

    private final String[] dashboardGridTitle = new String[]{"Create Request", "Requests", "Work Orders", "Quotes", "In Progress", "Scheduled", "Pending Invoices", "Overdue", "Deferred", "Completed", "All jobs"};
    private final int[] dashboardPageIcons = new int[]{R.drawable.archive_paper, R.drawable.handout, R.drawable.notes, R.drawable.report, R.drawable.multiple11, R.drawable.ic_home_52, R.drawable.ic_invoice_receipt, R.drawable.archive_paper, R.drawable.badge13, R.drawable.settings, R.drawable.email84};

    public TenantServicesGrid() {
    }

    @Override
    public int getCount() {
        return dashboardGridTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return dashboardGridTitle[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_row, null);
        }

        TextView title = convertView.findViewById(R.id.title_row);
        title.setText(dashboardGridTitle[position]);
        ImageView icon = convertView.findViewById(R.id.icon_row);
        Glide.with(parent.getContext()).load(dashboardPageIcons[position]).into(icon);

        return convertView;
    }
}
