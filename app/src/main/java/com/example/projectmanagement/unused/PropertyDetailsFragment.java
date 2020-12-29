package com.example.projectmanagement.unused;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projectmanagement.R;
import com.example.projectmanagement.unused.adapter.PropertyDetailsAdapter;
import com.example.projectmanagement.unused.adapter.PropertyListAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.LinkedList;

public class PropertyDetailsFragment extends BottomSheetDialogFragment {

    private LinkedList<String> propertyDetailsList = new LinkedList<>();
    private ImageButton left, right;
    private boolean compareMode;

    public PropertyDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.property_details_fragment, container, false);

        ViewPager2 property_details_rv = root.findViewById(R.id.property_details_vp);
        property_details_rv.setPadding(20, 10, 20, 10);
        property_details_rv.setClipToPadding(false);
        property_details_rv.setClipChildren(false);
        property_details_rv.setOffscreenPageLimit(1);

        propertyDetailsList.add("");
        propertyDetailsList.add("");
        propertyDetailsList.add("");

        PropertyDetailsAdapter propertyDetailsAdapter = new PropertyDetailsAdapter(requireContext(), propertyDetailsList);
        property_details_rv.setAdapter(propertyDetailsAdapter);
        propertyDetailsAdapter.setClickListener((view, position) -> Toast.makeText(requireContext(), "" + position, Toast.LENGTH_SHORT).show());

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleX(0.85f + r * 0.15f);
        });

        property_details_rv.setPageTransformer(compositePageTransformer);
        property_details_rv.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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

        left = root.findViewById(R.id.leftDetailArrow);
        right = root.findViewById(R.id.rightDetailArrow);

        checkListSize();

        return root;
    }

    private void checkListSize() {
        compareMode = propertyDetailsList.size() > 0;
        if (compareMode) {
            right.setVisibility(View.VISIBLE);
            left.setVisibility(View.VISIBLE);
        } else {
            right.setVisibility(View.GONE);
            left.setVisibility(View.GONE);
        }

    }
}
