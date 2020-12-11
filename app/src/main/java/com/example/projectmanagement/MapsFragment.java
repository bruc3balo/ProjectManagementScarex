package com.example.projectmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectmanagement.adapter.PropertyListAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;

public class MapsFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener {


    private final OnMapReadyCallback callback = googleMap -> {
        LatLng sydney = new LatLng(-1, 36.4);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Kenya"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        }

        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.setOnInfoWindowClickListener(this);


    };
    private final LinkedList<String> properties = new LinkedList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_maps, container, false);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(requireContext(), "Ready map", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        }

        ViewPager2 propertyListViewPager = root.findViewById(R.id.propertyListViewPager);
        propertyListViewPager.setPadding(10, 10, 10, 10);
        propertyListViewPager.setClipToPadding(false);
        propertyListViewPager.setClipChildren(false);
        propertyListViewPager.setOffscreenPageLimit(3);

        properties.add("");
        properties.add("");
        properties.add("");

        PropertyListAdapter propertyListAdapter = new PropertyListAdapter(requireContext(), properties);
        propertyListViewPager.setAdapter(propertyListAdapter);
        propertyListAdapter.setClickListener((view, position) -> Toast.makeText(requireContext(), ""+position, Toast.LENGTH_SHORT).show());

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleX(0.85f + r * 0.15f);
        });

        propertyListViewPager.setPageTransformer(compositePageTransformer);
        propertyListViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            default:
                break;
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(requireContext(), "Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        requireActivity().finish();
                    }
                } else {
                    requireActivity().finish();
                }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        showBottomSheetDialogFragment();
    }

    public void showBottomSheetDialogFragment() {
        PropertyDetailsFragment bottomSheetFragment = new PropertyDetailsFragment();
        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
    }
}