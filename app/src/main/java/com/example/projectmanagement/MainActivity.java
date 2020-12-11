package com.example.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentMain = getSupportFragmentManager();
        fragmentMain.beginTransaction().replace(R.id.fragmentMain, new MapsFragment()).commit();

        NavigationView sort_drawer = findViewById(R.id.sort_drawer);
        View headerSort = sort_drawer.getHeaderView(0);

        DrawerLayout mainDrawer = findViewById(R.id.property_drawer);
        toolbar.setNavigationOnClickListener(v -> mainDrawer.openDrawer(GravityCompat.START));
        toolbar.setOnGenericMotionListener((v, event) -> {
            if (event.getAction() == MotionEvent.EDGE_LEFT) {
                Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
            } else if (event.getAction() == MotionEvent.EDGE_RIGHT) {
                Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        setSortDrawer(headerSort);

    }

    @Override
    protected void onPause() {
        super.onPause();
        fragmentMain.beginTransaction().remove(new MapsFragment()).commit();
        Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentMain.beginTransaction().replace(R.id.fragmentMain, new MapsFragment()).commit();
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
    }

    private void setSortDrawer(View v) {
        TextView goTo = v.findViewById(R.id.goToTv);
        LinearLayout goToLayout = v.findViewById(R.id.goToLayout);
        goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goToLayout.getVisibility() == View.GONE) {
                    goTo.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.minus, 0, 0, 0);
                    goToLayout.setVisibility(View.VISIBLE);
                } else {
                    goTo.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.plus, 0, 0, 0);
                    goToLayout.setVisibility(View.GONE);
                }
            }
        });

        TextView propertyNameTv = v.findViewById(R.id.propertyNameTv);
        LinearLayout propertyNameLayout = v.findViewById(R.id.propertyNameLayout);
        propertyNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (propertyNameLayout.getVisibility() == View.GONE) {
                    propertyNameTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.minus, 0, 0, 0);
                    goToLayout.setVisibility(View.VISIBLE);
                } else {
                    propertyNameTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.plus, 0, 0, 0);
                    propertyNameLayout.setVisibility(View.GONE);
                }
            }
        });

        TextView purchasePriceTv = v.findViewById(R.id.purchasePriceTv);
        LinearLayout purchasePriceLayout = v.findViewById(R.id.purchasePriceLayout);
        purchasePriceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (purchasePriceLayout.getVisibility() == View.GONE) {
                    purchasePriceTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.minus, 0, 0, 0);
                    purchasePriceLayout.setVisibility(View.VISIBLE);
                } else {
                    purchasePriceTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.plus, 0, 0, 0);
                    purchasePriceLayout.setVisibility(View.GONE);
                }
            }
        });

        TextView areaTv = v.findViewById(R.id.areaTv);
        LinearLayout areaLayout = v.findViewById(R.id.areaLayout);
        areaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (areaLayout.getVisibility() == View.GONE) {
                    areaTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.minus, 0, 0, 0);
                    areaLayout.setVisibility(View.VISIBLE);
                } else {
                    areaTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.plus, 0, 0, 0);
                    areaLayout.setVisibility(View.GONE);
                }
            }
        });

        TextView locationTv = v.findViewById(R.id.locationTv);
        LinearLayout locationLayout = v.findViewById(R.id.locationLayout);
        locationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationLayout.getVisibility() == View.GONE) {
                    locationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.minus, 0, 0, 0);
                    locationLayout.setVisibility(View.VISIBLE);
                } else {
                    locationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.plus, 0, 0, 0);
                    locationLayout.setVisibility(View.GONE);
                }
            }
        });

        TextView propertyStyleTv = v.findViewById(R.id.propertyStyleTv);
        LinearLayout propertyStyleLayout = v.findViewById(R.id.propertyStyleLayout);
        propertyStyleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (propertyStyleLayout.getVisibility() == View.GONE) {
                    propertyStyleTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.minus, 0, 0, 0);
                    propertyStyleLayout.setVisibility(View.VISIBLE);
                } else {
                    propertyStyleTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.plus, 0, 0, 0);
                    propertyStyleLayout.setVisibility(View.GONE);
                }
            }
        });

        TextView agentTv = v.findViewById(R.id.agentTv);
        LinearLayout agentLayout = v.findViewById(R.id.agentLayout);
        agentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agentLayout.getVisibility() == View.GONE) {
                    agentTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.minus, 0, 0, 0);
                    agentLayout.setVisibility(View.VISIBLE);
                } else {
                    agentTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.plus, 0, 0, 0);
                    agentLayout.setVisibility(View.GONE);
                }
            }
        });

        TextView creationDateTv = v.findViewById(R.id.creationDateTv);
        LinearLayout creationDateLayout = v.findViewById(R.id.creationDateLayout);
        creationDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (creationDateLayout.getVisibility() == View.GONE) {
                    creationDateTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.minus, 0, 0, 0);
                    creationDateLayout.setVisibility(View.VISIBLE);
                } else {
                    creationDateTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.plus, 0, 0, 0);
                    creationDateLayout.setVisibility(View.GONE);
                }
            }
        });

        TextView vehicleCapacityTv = v.findViewById(R.id.vehicleCapacityTv);
        LinearLayout vehicleCapacityLayout = v.findViewById(R.id.vehicleCapacityLayout);
        vehicleCapacityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vehicleCapacityLayout.getVisibility() == View.GONE) {
                    vehicleCapacityTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.minus, 0, 0, 0);
                    vehicleCapacityLayout.setVisibility(View.VISIBLE);
                } else {
                    vehicleCapacityTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.plus, 0, 0, 0);
                    vehicleCapacityLayout.setVisibility(View.GONE);
                }
            }
        });

    }

}