package com.example.projectmanagement.pages.property;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectmanagement.R;
import com.example.projectmanagement.models.Models;
import com.example.projectmanagement.pages.login.SignUpActivity;
import com.example.projectmanagement.utils.IdGen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.projectmanagement.models.Models.PropertyInfo.ADDRESS1;
import static com.example.projectmanagement.models.Models.PropertyInfo.ADDRESS2;
import static com.example.projectmanagement.models.Models.PropertyInfo.AREA;
import static com.example.projectmanagement.models.Models.PropertyInfo.BATHROOMS;
import static com.example.projectmanagement.models.Models.PropertyInfo.CITY;
import static com.example.projectmanagement.models.Models.PropertyInfo.IMAGE_URL;
import static com.example.projectmanagement.models.Models.PropertyInfo.IS_MUTLTI_UNIT;
import static com.example.projectmanagement.models.Models.PropertyInfo.LAST_MODIFIED;
import static com.example.projectmanagement.models.Models.PropertyInfo.NOTES;
import static com.example.projectmanagement.models.Models.PropertyInfo.NO_IMAGE_POSTER;
import static com.example.projectmanagement.models.Models.PropertyInfo.PROPERTY_DB;
import static com.example.projectmanagement.models.Models.PropertyInfo.PROPERTY_ID;
import static com.example.projectmanagement.models.Models.PropertyInfo.STATE;
import static com.example.projectmanagement.models.Models.PropertyInfo.SUBURB;
import static com.example.projectmanagement.models.Models.PropertyInfo.ZIPCODE;
import static com.example.projectmanagement.pages.login.SignUpActivity.time;

public class EntryPropertyDetails extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 12;
    public static final int GET_FROM_GALLERY = 3;
    private Uri file = null;
    private ImageView propertyImagePreview;
    private Button addPhotoButton;
    private final Models.PropertyInfo propertyInfo = new Models.PropertyInfo();
    private EditText countryField, addressLine1Field, addressLine2Field, subUrbField, cityField, stateField, zipField, totalAreaField, bedroomsField, bathroomsField, property_notes_field;

    private final Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        Toolbar propertyDetailsTb = findViewById(R.id.propertyDetailsTb);
        setSupportActionBar(propertyDetailsTb);
        propertyDetailsTb.setNavigationOnClickListener(v -> finish());

        if (getIntent().getExtras() != null) {
            propertyInfo.setPropertyId(getIntent().getExtras().get(PROPERTY_ID).toString());
        }

        countryField = findViewById(R.id.countryField);
        addressLine1Field = findViewById(R.id.addressLine1Field);
        addressLine2Field = findViewById(R.id.addressLine2Field);
        subUrbField = findViewById(R.id.subUrbField);

        cityField = findViewById(R.id.cityField);
        stateField = findViewById(R.id.stateField);
        zipField = findViewById(R.id.zipField);
        totalAreaField = findViewById(R.id.totalAreaField);

        bedroomsField = findViewById(R.id.bedroomsField);
        bathroomsField = findViewById(R.id.bathroomsField);
        property_notes_field = findViewById(R.id.property_notes_field);
        propertyImagePreview = findViewById(R.id.propertyImagePreview);

        addPhotoButton = findViewById(R.id.addPhotoButton);
        addPhotoButton.setOnClickListener(v -> galleryPermission());

        ImageButton goTOMortgage = findViewById(R.id.goTOMortgage);
        goTOMortgage.setOnClickListener(v -> startActivity(new Intent(EntryPropertyDetails.this, PropertyMortgage.class).putExtra(PROPERTY_ID, propertyInfo.getPropertyId())));

        ImageButton gotToValuation = findViewById(R.id.gotToValuation);
        gotToValuation.setOnClickListener(v -> startActivity(new Intent(EntryPropertyDetails.this, PropertyValuation.class).putExtra(PROPERTY_ID, propertyInfo.getPropertyId())));

        SwitchCompat hmoSwitch = findViewById(R.id.hmoSwitch);
        hmoSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                propertyInfo.setMultiUnit(true);
                Toast.makeText(EntryPropertyDetails.this, "Is Multiunit", Toast.LENGTH_SHORT).show();
            } else {
                propertyInfo.setMultiUnit(false);
                Toast.makeText(EntryPropertyDetails.this, "Is HMO", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateForm(EditText country, EditText address1, EditText address2, EditText subUrb, EditText city, EditText state, EditText zip, EditText area, EditText bed, EditText bath, EditText notes) {

        map.clear();
        boolean valid = false;

        if (country.getText().toString().isEmpty()) {
            country.requestFocus();
            country.setError("Required");
        } else if (address1.getText().toString().isEmpty()) {
            address1.requestFocus();
            address1.setError("Required");
        } else if (subUrb.getText().toString().isEmpty()) {
            subUrb.requestFocus();
            subUrb.setError("Required");
        } else if (city.getText().toString().isEmpty()) {
            city.requestFocus();
            city.setError("Required");
        } else if (state.getText().toString().isEmpty()) {
            state.requestFocus();
            state.setError("Required");
        } else if (zip.getText().toString().isEmpty()) {
            zip.requestFocus();
            zip.setError("Required");
        } else if (area.getText().toString().isEmpty()) {
            area.setError("Required");
            area.requestFocus();
        } else if (bed.getText().toString().isEmpty()) {
            bed.requestFocus();
            bed.setError("Required");
        } else if (bath.getText().toString().isEmpty()) {
            bath.requestFocus();
            bath.setError("Required");
        } else {


            propertyInfo.setAddress1(address1.getText().toString());
            map.put(ADDRESS1, propertyInfo.getAddress1());

            if (address2.getText().toString().isEmpty()) {
                propertyInfo.setAddress2("");
            } else {
                propertyInfo.setAddress2(address2.getText().toString());
            }
            map.put(ADDRESS2, propertyInfo.getAddress2());
            propertyInfo.setLastModifiedAt(time);
            map.put(LAST_MODIFIED, propertyInfo.getLastModifiedAt());

            if (notes.getText().toString().isEmpty()) {
                propertyInfo.setNotes("");
            } else {
                propertyInfo.setNotes(notes.getText().toString());
            }
            map.put(NOTES, propertyInfo.getNotes());
            propertyInfo.setBathrooms(Integer.parseInt(bath.getText().toString()));
            map.put(BATHROOMS, propertyInfo.getBathrooms());

            propertyInfo.setBedrooms(Integer.parseInt(bed.getText().toString()));
            map.put(BATHROOMS, propertyInfo.getBedrooms());
            propertyInfo.setCity(city.getText().toString());
            map.put(CITY, propertyInfo.getCity());
            propertyInfo.setArea(Double.parseDouble(area.getText().toString()));
            map.put(AREA, propertyInfo.getArea());

            propertyInfo.setState(state.getText().toString());
            map.put(STATE, state.getText().toString());
            propertyInfo.setSuburb(subUrb.getText().toString());
            map.put(SUBURB, propertyInfo.getSuburb());
            propertyInfo.setZipCode(zip.getText().toString());
            map.put(ZIPCODE, propertyInfo.getZipCode());

            map.put(PROPERTY_ID, propertyInfo.getPropertyId());
            map.put(IS_MUTLTI_UNIT, propertyInfo.isMultiUnit());

            valid = true;
        }

        return valid;
    }

    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
    }

    private void galleryPermission() {
        // checkSelfPermission(Manifest.permission_group.STORAGE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Not granted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else {
            Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            file = Uri.fromFile(getOutputMediaFile());
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).putExtra(MediaStore.EXTRA_OUTPUT, file), GET_FROM_GALLERY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_property_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if (validateForm(countryField, addressLine1Field, addressLine2Field, subUrbField, cityField, stateField, zipField, totalAreaField, bedroomsField, bathroomsField, property_notes_field)) {
                new UploadPropertyJobDetails().execute();
            } else {
                Toast.makeText(this, "Check details", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY) {
            new Handler(Looper.myLooper()).postDelayed(() -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        assert data != null;
                        propertyImagePreview.setImageURI(data.getData());
                        file = data.getData();
                        System.out.println("File : " + file + " Uri : " + data.getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Failed to get Image");
                }
            }, 1000);
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class UploadPropertyJobDetails extends AsyncTask<Void, Void, Void> {

        private String imageUri = "";

        @Override
        protected Void doInBackground(Void... voids) {
            if (file != null) {
                uploadUserImage(file);
            } else {
                updateDetails(NO_IMAGE_POSTER);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private void uploadUserImage(Uri imageFile) {
            StorageReference carBucket = FirebaseStorage.getInstance().getReference().child("Property Images").child(propertyInfo.getPropertyId());
            //  Uri file = Uri.fromFile(new File(String.valueOf(imageFile)));
            try {
                carBucket.putFile(file).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        carBucket.getDownloadUrl().addOnSuccessListener(uri -> {
                            imageUri = uri.toString();
                            propertyInfo.setImageUri(uri.toString());
                            updateDetails(uri.toString());
                            System.out.println("Stored link uri" + imageUri);
                        });

                        System.out.println("Uploaded " + imageFile.toString());
                    } else {
                        System.out.println("Failed to upload " + imageFile.toString());
                        updateDetails(NO_IMAGE_POSTER);
                    }

                });
            } catch (Exception e) {
                updateDetails(NO_IMAGE_POSTER);
            }
        }

        private void updateDetails(String imageUri) {
            map.put(IMAGE_URL, imageUri);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(PROPERTY_DB).document(propertyInfo.getPropertyId()).update(map).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(EntryPropertyDetails.this, "Updated property", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EntryPropertyDetails.this, ViewPropertyDetails.class).putExtra(PROPERTY_ID, propertyInfo.getPropertyId()));
                    if (!isDestroyed()) {
                        finish();
                    }
                } else {
                    Toast.makeText(EntryPropertyDetails.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}