package com.example.projectmanagement.pages.landlord.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmanagement.R;
import com.example.projectmanagement.pages.landlord.home.pages.tenant.TentantsList;
import com.example.projectmanagement.pages.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Landlord_Home_Activity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord__home_);

        GridView homeGrid = findViewById(R.id.homeGrid);
        DashboardPageGrid dashboardPageGrid = new DashboardPageGrid();
        homeGrid.setAdapter(dashboardPageGrid);

        homeGrid.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(Landlord_Home_Activity.this, String.valueOf(dashboardPageGrid.getItem(position)), Toast.LENGTH_SHORT).show();
            switch (position) {
                default:
                    break;
                case 0:
                    break;
                case 3:
                    startActivity(new Intent(Landlord_Home_Activity.this, TentantsList.class));
                    break;
                case 11:
                    FirebaseAuth.getInstance().signOut();
                    updateUi(FirebaseAuth.getInstance().getCurrentUser(), Landlord_Home_Activity.this);
                    break;
            }
        });

        TextView landlord_email_tv = findViewById(R.id.landlord_email_tv);

        try {
            landlord_email_tv.setText(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            landlord_email_tv.setText("Failed to get user");
        }

    }

    public static void updateUi(FirebaseUser user, Context context) {
        if (user != null) {
            System.out.println("Signed in");
            Toast.makeText(context, "Signed in", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("Signed out");
            Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

}