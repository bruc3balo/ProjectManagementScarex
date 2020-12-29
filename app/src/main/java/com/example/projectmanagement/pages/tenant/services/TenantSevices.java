package com.example.projectmanagement.pages.tenant.services;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.projectmanagement.R;
import com.example.projectmanagement.pages.tenant.home.TenantPageGrid;
import com.example.projectmanagement.pages.tenant.home.Tenant_Home_Activity;

public class TenantSevices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_sevices);

        GridView homeGrid = findViewById(R.id.tenantServicesGrid);
        TenantServicesGrid tenantServicesGrid = new TenantServicesGrid();
        homeGrid.setAdapter(tenantServicesGrid);

        homeGrid.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(TenantSevices.this, String.valueOf(tenantServicesGrid.getItem(position)), Toast.LENGTH_SHORT).show());
    }
}