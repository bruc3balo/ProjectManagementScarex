package com.example.projectmanagement.pages.landlord.home.pages.tenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.projectmanagement.R;
import com.example.projectmanagement.models.Models;
import com.example.projectmanagement.pages.landlord.home.pages.tenant.adapter.TenantRvAdapter;

import java.util.LinkedList;

public class TentantsList extends AppCompatActivity {

    private TenantRvAdapter tenantRvAdapter;
    private final LinkedList<Models.Tenant_Person> personList = new LinkedList<>();
    private final LinkedList<Models.Tenant_Company> companyLinkedList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentants_list);

        Toolbar landlord_tenant_tb = findViewById(R.id.landlord_tenant_tb);
        setSupportActionBar(landlord_tenant_tb);
        landlord_tenant_tb.setNavigationOnClickListener(v -> finish());


        RecyclerView tentantListRv = findViewById(R.id.tentantListRv);
        tentantListRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        tenantRvAdapter = new TenantRvAdapter(personList, companyLinkedList, this);
        tentantListRv.setAdapter(tenantRvAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_tenant, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.tenantAdd) {
            startActivity(new Intent(this, AddTenant.class));
        }
        return super.onOptionsItemSelected(item);
    }
}