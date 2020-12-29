package com.example.projectmanagement.pages.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmanagement.R;
import com.example.projectmanagement.models.Models;
import com.example.projectmanagement.unused.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.projectmanagement.models.Models.PropertyInfo.PROPERTY_ID;
import static com.example.projectmanagement.models.Models.PropertyValuation.VALUATION_DB;

public class PropertyValuation extends AppCompatActivity {
    private final Models.PropertyValuation propertyValuation = new Models.PropertyValuation();
    private EditText purchasePrice, purchaseTax, currentValuation, gainLoss;
    private TextView purchaseDateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_valuation);

        if (getIntent().getExtras() != null) {
            propertyValuation.setPropertyId(getIntent().getExtras().get(PROPERTY_ID).toString());
        }

        Toolbar valuationTb = findViewById(R.id.valuationTb);
        setSupportActionBar(valuationTb);
        valuationTb.setNavigationOnClickListener(v -> finish());


        purchasePrice = findViewById(R.id.purchasePrice);
        purchaseTax = findViewById(R.id.purchaseTax);
        currentValuation = findViewById(R.id.currentValuation);
        gainLoss = findViewById(R.id.gainLoss);
        purchaseDateTv = findViewById(R.id.purchaseDateTv);

    }

    private boolean validateForm(EditText purchasePrice, EditText purchaseTax, EditText currentVal, EditText gain) {
        boolean valid = false;

        if (purchasePrice.getText().toString().isEmpty()) {
            purchasePrice.requestFocus();
            purchasePrice.setError("Required");
        } else if (purchaseTax.getText().toString().isEmpty()) {
            purchaseTax.requestFocus();
            purchaseTax.setError("Required");
        } else if (currentVal.getText().toString().isEmpty()) {
            currentVal.requestFocus();
            currentVal.setError("Required");
        } else if (gain.getText().toString().isEmpty()) {
            gain.requestFocus();
            gain.setError("Required");
        } else if (propertyValuation.getPurchaseDate() == null) {
            Toast.makeText(this, "Pick purchase Date", Toast.LENGTH_SHORT).show();
            showPurchaseDateDatePicker();
        } else {
            propertyValuation.setGainLoss(gain.getText().toString());
            propertyValuation.setPurchasePrice(purchasePrice.getText().toString());
            propertyValuation.setCurrentValuation(currentVal.getText().toString());
            propertyValuation.setPurchaseTax(purchaseTax.getText().toString());
            propertyValuation.setUid(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

            valid = true;
        }

        return valid;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_property_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if (validateForm(purchasePrice, purchaseTax, currentValuation, gainLoss)) {
                uploadData(propertyValuation);
            } else {
                Toast.makeText(this, "Check details", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadData(Models.PropertyValuation propertyValuation) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(VALUATION_DB).document(propertyValuation.getPropertyId()).set(propertyValuation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PropertyValuation.this, "Property Uploaded", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PropertyValuation.this, "Failed to upload property", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showPurchaseDateDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(PropertyValuation.this, (view, year, month, dayOfMonth) -> {
            String startDate = dayOfMonth + "/" + month + "/" + year;
            purchaseDateTv.setText(startDate);
            propertyValuation.setPurchaseDate(startDate);
            Snackbar.make(findViewById(android.R.id.content), startDate, Snackbar.LENGTH_LONG).show();
        }, 2020, 1, 1);
        datePickerDialog.show();
    }

    public void showPurchaseDate(View view) {
        showPurchaseDateDatePicker();
    }
}