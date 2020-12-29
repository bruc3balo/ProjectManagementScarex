package com.example.projectmanagement.pages.landlord.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projectmanagement.R;


public class DashboardPageGrid extends BaseAdapter {

    private final String[] dashboardGridTitle = new String[]{"Dashboard", "Property Reports", "Sales approval", "Tenants", "Add Rent", "Property Manager", "Maintenance", "Lease", "My detail", "Settings", "", "Log out"};
    private final int[] dashboardPageIcons = new int[]{R.drawable.ic_grid_45, R.drawable.report, R.drawable.notes, R.drawable.multiple11, R.drawable.handout, R.drawable.ic_home_52, R.drawable.like, R.drawable.archive_paper, R.drawable.badge13, R.drawable.settings, R.drawable.email84, R.drawable.exit};

    public DashboardPageGrid() {
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
