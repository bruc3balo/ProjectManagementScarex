package com.example.projectmanagement.pages.property;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectmanagement.R;
import com.example.projectmanagement.models.Models;
import com.example.projectmanagement.utils.IdGen;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.example.projectmanagement.models.Models.PropertyInfo.PROPERTY_DB;
import static com.example.projectmanagement.models.Models.PropertyInfo.PROPERTY_ID;
import static com.example.projectmanagement.pages.property.EntryPropertyDetails.GET_FROM_GALLERY;

public class NewProperty1 extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener {

    public static final int LOCATION_PERMISSION = 10;
    private LatLng propertyPosition = null;
    private String propertyLocation = "";
    private EditText addressF, name;
    private GoogleMap map;
    private boolean backPressed;
    private final Models.PropertyInfo propertyInfo = new Models.PropertyInfo();
    private Uri file = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_property1);

        Toolbar newPropertyTb = findViewById(R.id.newPropertyTb);
        setSupportActionBar(newPropertyTb);
        newPropertyTb.setNavigationOnClickListener(v -> finish());

        addressF = findViewById(R.id.property_address_field);
        name = findViewById(R.id.property_name_field);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        backPressed = false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng kenya = new LatLng(-1, 36.4);
        googleMap.addMarker(new MarkerOptions().position(kenya).title("Kenya"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(kenya));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);
            return;
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

        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationClickListener(this);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(this);
        googleMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        getFromMarker(marker);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_property_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if (validateForm(addressF, name)) {
                savePropertyDetails();
            } else {
                Toast.makeText(this, "Check details", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePropertyDetails() {

        propertyInfo.setUid(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        propertyInfo.setName(name.getText().toString());
        propertyInfo.setPropertyPosition(propertyPosition);

        propertyInfo.setPropertyLocation(propertyLocation);
        propertyInfo.setPropertyId(IdGen.getPropertyId(name.getText().toString()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PROPERTY_DB).document(propertyInfo.getPropertyId()).set(propertyInfo).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(NewProperty1.this, "Details saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NewProperty1.this, EntryPropertyDetails.class).putExtra(PROPERTY_ID, propertyInfo.getPropertyId()));
                finish();
            } else {
                Toast.makeText(NewProperty1.this, "Failed to save details", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validateForm(EditText address, EditText name) {
        boolean valid = false;
        if (address.getText().toString().isEmpty()) {
            address.setError("Hold map location or input manually");
            address.requestFocus();
        } else if (name.getText().toString().isEmpty()) {
            name.setError("Name is empty");
            name.requestFocus();
        } else if (propertyPosition == null) {
            Snackbar.make(findViewById(android.R.id.content), "Pick from map. Long press map to pick a location", Snackbar.LENGTH_LONG).show();
        } else {
            valid = true;
        }
        return valid;
    }

    private void getFromMarker(Marker marker) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        String errorMessage = "";
        try {
            addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
            Address address = addresses.get(0);
            // ArrayList<String> addressFragments = new ArrayList<>();
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                //    addressFragments.add(address.getAddressLine(i));
                propertyLocation = address.getAddressLine(i);
                addressF.setText(propertyLocation);
                propertyPosition = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                Toast.makeText(this, String.valueOf(address.getAddressLine(i)), Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(android.R.id.content), address.getAddressLine(i), Snackbar.LENGTH_LONG).show();
            }
        } catch (IOException ioException) {
            errorMessage = "Service not available";
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = "Invalid latLng";
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private String getFromLocation(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        String errorMessage = "";
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            Address address = addresses.get(0);
            //    ArrayList<String> addressFragments = new ArrayList<>();
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                //    addressFragments.add(address.getAddressLine(i));
                propertyLocation = address.getAddressLine(i);
                propertyPosition = latLng;
                addressF.setText(propertyLocation);
                Toast.makeText(this, propertyLocation, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException ioException) {
            errorMessage = "Service not available";
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = "Invalid latLng";
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
        return propertyLocation;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(NewProperty1.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(NewProperty1.this, "Granted", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        map.addMarker(new MarkerOptions().position(latLng).draggable(true).title(getFromLocation(latLng)).snippet("This is the location you have selected"));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        getFromMarker(marker);
    }

    @Override
    public void onBackPressed() {
        if (!backPressed) {
            backPressed = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        backPressed = true;
    }



}