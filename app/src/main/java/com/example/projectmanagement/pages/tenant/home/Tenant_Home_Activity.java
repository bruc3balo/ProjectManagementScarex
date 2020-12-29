package com.example.projectmanagement.pages.tenant.home;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmanagement.R;
import com.example.projectmanagement.pages.landlord.home.Landlord_Home_Activity;
import com.example.projectmanagement.pages.landlord.home.pages.tenant.AddTenant;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.projectmanagement.pages.landlord.home.Landlord_Home_Activity.updateUi;

public class Tenant_Home_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant__home_);

        GridView homeGrid = findViewById(R.id.tenantHomeGrid);
        TenantPageGrid tenantPageGrid = new TenantPageGrid();
        homeGrid.setAdapter(tenantPageGrid);

        homeGrid.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(Tenant_Home_Activity.this, String.valueOf(tenantPageGrid.getItem(position)) + position, Toast.LENGTH_SHORT).show();
            switch (position){
                default:break;
                case 12:
                    FirebaseAuth.getInstance().signOut();
                    updateUi(FirebaseAuth.getInstance().getCurrentUser(), Tenant_Home_Activity.this);
                    break;
            }
        });
    }
}