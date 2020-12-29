package com.example.projectmanagement.pages.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmanagement.R;
import com.example.projectmanagement.models.Models;
import com.example.projectmanagement.pages.login.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.projectmanagement.models.Models.PropertyInfo.PROPERTY_ID;
import static com.example.projectmanagement.models.Models.PropertyMortgage.MORTGAGE_DB;
import static com.example.projectmanagement.pages.login.SignUpActivity.time;

public class PropertyMortgage extends AppCompatActivity {

    private EditText bankNameField, principalAmountField, interestRateField, loanTermField, repaymentField, amountField;
    private String startDate = "", completionDate = "";
    private final Models.PropertyMortgage mortgage = new Models.PropertyMortgage();
    private TextView start, completion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_mortgage);

        Toolbar mortgageTb = findViewById(R.id.mortgageTb);
        setSupportActionBar(mortgageTb);
        mortgageTb.setNavigationOnClickListener(v -> finish());

        if (getIntent().getExtras() != null) {
            mortgage.setPropertyId(getIntent().getExtras().get(PROPERTY_ID).toString());
        }

        bankNameField = findViewById(R.id.bankNameField);
        principalAmountField = findViewById(R.id.principalAmountField);
        interestRateField = findViewById(R.id.interestRateField);

        loanTermField = findViewById(R.id.loanTermField);

        repaymentField = findViewById(R.id.repaymentField);

        amountField = findViewById(R.id.amountField);

        start = findViewById(R.id.startDateTv);
        completion = findViewById(R.id.completionDateTv);

        TextView getStartDateTv = findViewById(R.id.getStartDateTv);
        getStartDateTv.setOnClickListener(v -> startDateShow());

        TextView getCompletionDate = findViewById(R.id.getCompletionDate);
        getCompletionDate.setOnClickListener(v -> completionDateShow());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_property_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if (validateForm()) {
                uploadData(mortgage);
            } else {
                Toast.makeText(this, "Check details", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadData(Models.PropertyMortgage mortgage) {
        Toast.makeText(this, "Please wait. Uploading your details", Toast.LENGTH_SHORT).show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(MORTGAGE_DB).document(mortgage.getPropertyId()).set(mortgage).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(PropertyMortgage.this, "Details Saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(PropertyMortgage.this, "Failed to save details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm() {
        boolean valid = false;
        if (bankNameField.getText().toString().isEmpty()) {
            bankNameField.setError("Required");
            bankNameField.requestFocus();
        } else if (principalAmountField.getText().toString().isEmpty()) {
            principalAmountField.setError("Required");
            principalAmountField.requestFocus();
        } else if (interestRateField.getText().toString().isEmpty()) {
            interestRateField.setError("Required");
            interestRateField.requestFocus();
        } else if (loanTermField.getText().toString().isEmpty()) {
            loanTermField.setError("Required");
            loanTermField.requestFocus();
        } else if (repaymentField.getText().toString().isEmpty()) {
            repaymentField.setError("Required");
            repaymentField.requestFocus();
        } else if (amountField.getText().toString().isEmpty()) {
            amountField.setError("Required");
            amountField.requestFocus();
        } else if (startDate.equals("")) {
            startDateShow();
        } else if (completionDate.equals("")) {
            completionDateShow();
        } else {
            mortgage.setAmountRepaid(amountField.getText().toString());
            mortgage.setBankName(bankNameField.getText().toString());
            mortgage.setCompletionDate(completionDate);

            mortgage.setStartDate(startDate);
            mortgage.setLastModifiedAt(time);
            mortgage.setLoanTerm(loanTermField.getText().toString());

            mortgage.setInterestRate(interestRateField.getText().toString());
            mortgage.setUid(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
            mortgage.setRepaymentAmount(repaymentField.getText().toString());

            mortgage.setPrincipalAmount(principalAmountField.getText().toString());

            valid = true;
        }
        return valid;
    }

    public void completionDateShow() {
        showCompletionDatePicker();
    }

    public void startDateShow() {
        showStartDatePicker();
    }

    private void showStartDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(PropertyMortgage.this, (view, year, month, dayOfMonth) -> {
            startDate = dayOfMonth + "/" + month + "/" + year;
            start.setText(startDate);
            Snackbar.make(findViewById(android.R.id.content), startDate, Snackbar.LENGTH_LONG).show();
        }, 2020, 1, 1);
        datePickerDialog.show();
    }

    private void showCompletionDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(PropertyMortgage.this, (view, year, month, dayOfMonth) -> {
            completionDate = dayOfMonth + "/" + month + "/" + year;
            completion.setText(completionDate);
            Snackbar.make(findViewById(android.R.id.content), completionDate, Snackbar.LENGTH_LONG).show();
        }, 2020, 1, 1);
        datePickerDialog.show();
    }
}