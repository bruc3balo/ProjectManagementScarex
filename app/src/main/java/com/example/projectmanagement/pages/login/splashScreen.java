package com.example.projectmanagement.pages.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmanagement.R;
import com.example.projectmanagement.pages.dashboard.Dashboard;
import com.example.projectmanagement.pages.landlord.home.Landlord_Home_Activity;
import com.example.projectmanagement.pages.property.NewProperty1;
import com.example.projectmanagement.pages.tenant.home.Tenant_Home_Activity;
import com.example.projectmanagement.pages.tenant.services.TenantSevices;
import com.google.firebase.FirebaseApp;


public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        setContentView(R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(this);
        new Handler(Looper.myLooper()).postDelayed(() -> {
            startActivity(new Intent(splashScreen.this, LoginActivity.class));
            finish();
        },2500);
    }
}