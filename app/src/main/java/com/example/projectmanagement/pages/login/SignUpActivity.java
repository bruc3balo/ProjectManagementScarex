package com.example.projectmanagement.pages.login;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmanagement.R;
import com.example.projectmanagement.models.Models;
import com.example.projectmanagement.pages.getstarted.GetStarted1;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

import java.util.Calendar;
import java.util.Objects;

import static com.example.projectmanagement.models.Models.USER_DB;
import static com.example.projectmanagement.models.Models.User_Landlord.LANDLORD;
import static com.example.projectmanagement.pages.login.LoginActivity.truncate;

public class SignUpActivity extends AppCompatActivity implements InternetConnectivityListener {

    private String genderString = "";
    private boolean acceptedTerms;
    private Button signUpButton;

    private ProgressBar contentLoadingProgressBar;
    private String countryName = "Kenya";
    private String mobileCode = "";
    private String monthOfBirth = "";
    private String yearOfBirth = "";
    private String dateOfBirth = "";

    private TextView date_field;
    private final Models.User_Landlord landlord = new Models.User_Landlord();
    private InternetAvailabilityChecker mInternetAvailabilityChecker;

    public static final String time = truncate(Calendar.getInstance().getTime().toString(),16);


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //ID's
        EditText fullNames = findViewById(R.id.fullNames);
        date_field = findViewById(R.id.date_picked);
        ImageButton get_date = findViewById(R.id.get_date);
        get_date.setOnClickListener(v -> showDatePicker());

        CountryCodePicker ccp = findViewById(R.id.ccp);
        ccp.setOnCountryChangeListener(selectedCountry -> {
            countryName = selectedCountry.getName();
            mobileCode = selectedCountry.getPhoneCode();
            Snackbar.make(findViewById(android.R.id.content), countryName, Snackbar.LENGTH_LONG).show();
        });
        EditText phone_number_field = findViewById(R.id.phone_number_field);
        EditText addressField = findViewById(R.id.addressField);

        EditText email_address = findViewById(R.id.email_address);
        EditText id_number_field = findViewById(R.id.id_number_field);
        EditText password_field_pass = findViewById(R.id.password_field_pass);
        EditText confirm_password_field_pass = findViewById(R.id.confirm_password_field_pass);

        RadioGroup radioGender = findViewById(R.id.radioGender);
        radioGender.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {

                case R.id.maleRadioButton:
                default:
                    genderString = "Male";
                    break;

                case R.id.femaleRadioButton:
                    genderString = "Female";
                    break;
            }
        });

        CheckBox acceptTermsBox = findViewById(R.id.acceptTermsBox);
        acceptedTerms = false;
        acceptTermsBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            acceptedTerms = isChecked;
            if (isChecked) {
                Toast.makeText(this, "T&C's Accepted", Toast.LENGTH_SHORT).show();
                signUpButton.setEnabled(true);
            } else {
                Toast.makeText(this, "Accept T&C's to continue", Toast.LENGTH_SHORT).show();
                signUpButton.setEnabled(false);
            }
        });

        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setEnabled(false);
        signUpButton.setOnClickListener(v -> {
            if (validateForm(fullNames, phone_number_field, addressField, email_address, id_number_field, password_field_pass, confirm_password_field_pass)) {
                inProgress();
                createUserWithFirebase(email_address.getText().toString(), confirm_password_field_pass.getText().toString());
            } else {
                Toast.makeText(SignUpActivity.this, "Check details", Toast.LENGTH_SHORT).show();
            }
        });

        TextView goToLogin = findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });

        contentLoadingProgressBar = findViewById(R.id.contentLoadingProgressBar);
        contentLoadingProgressBar.setVisibility(View.GONE);

        //Watchers
        phone_number_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().startsWith("0")) {
                    phone_number_field.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fullNames.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    s.toString().replaceAll("\\.", "");
                    fullNames.setError("(.) not allowed");
                }


                if (s.toString().contains("!")) {
                    s.toString().replaceAll("!", "");
                    fullNames.setError("(!) not allowed");
                }

                if (s.toString().contains("!")) {
                    s.toString().replaceAll("!", "");
                    fullNames.setError("(!) not allowed");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);
    }

    private boolean validateForm(EditText fN, EditText phone, EditText address, EditText email, EditText id, EditText pass, EditText cPass) {
        boolean valid = false;

        if (fN.getText().toString().isEmpty()) {
            fN.requestFocus();
            fN.setError("Full Names required");
        } else if (!fN.getText().toString().contains(" ")) {
            fN.setError("Separate names with a space");
            fN.requestFocus();
        } else if (dateOfBirth.equals("")) {
            showDatePicker();
        } else if (mobileCode.equals("")) {
            Snackbar.make(findViewById(android.R.id.content), "Pick a country", Snackbar.LENGTH_LONG).show();
            phone.requestFocus();
            phone.setError("Select country");
        } else if (phone.getText().toString().isEmpty()) {
            phone.requestFocus();
            phone.setError("Phone number required");
        } else if (address.getText().toString().isEmpty()) {
            address.requestFocus();
            address.setError("Address Required");
        } else if (!email.getText().toString().contains("@") || email.getText().toString().length() < 4) {
            email.requestFocus();
            email.setError("Wrong format");
        } else if (id.getText().toString().length() < 6) {
            id.requestFocus();
            id.setError("ID cannot be less than 6 characters");
        } else if (pass.getText().toString().length() < 6) {
            pass.requestFocus();
            pass.setError("Password should not be less than 6");
        } else if (!cPass.getText().toString().equals(pass.getText().toString())) {
            cPass.setError("Passwords don't match");
            cPass.requestFocus();
            pass.setError("Passwords don't match");
            pass.requestFocus();
        } else if (genderString.equals("")) {
            Snackbar.make(findViewById(android.R.id.content), "Pick a gender", Snackbar.LENGTH_LONG).show();
        } else if (!acceptedTerms) {
            signUpButton.setEnabled(false);
            Snackbar.make(findViewById(android.R.id.content), "Accept terms to continue", Snackbar.LENGTH_LONG).show();
        } else {

            landlord.setPhoneNumber(mobileCode.concat(phone.getText().toString()));
            landlord.setAddressLine(address.getText().toString());
            landlord.setCountry(countryName);

            landlord.setDateOfBirth(dateOfBirth);
            landlord.setYearOfBirth(yearOfBirth);
            landlord.setMonthOfBirth(monthOfBirth + 1);

            landlord.setIdNumber(id.getText().toString());
            landlord.setGender(genderString);
            landlord.setCreatedAt(time);

            landlord.setRole(LANDLORD);
            landlord.setEmail(email.getText().toString());
            landlord.setName(fN.getText().toString());

            signUpButton.setEnabled(true);
            valid = true;
        }
        return valid;
    }

    private void showDatePicker() {
        Toast.makeText(this, "Pick your date of birth", Toast.LENGTH_SHORT).show();
        DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, (view, year, month, dayOfMonth) -> {
            String datePicked = dayOfMonth + "/" + month + "/" + year;
            date_field.setText(datePicked);
            dateOfBirth = String.valueOf(dayOfMonth);
            monthOfBirth = String.valueOf(month);
            yearOfBirth = String.valueOf(year);
            Snackbar.make(findViewById(android.R.id.content), datePicked, Snackbar.LENGTH_LONG).show();
        }, 2008, 1, 1);
        datePickerDialog.show();
    }

    private void createUserWithFirebase(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                landlord.setUid(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                Toast.makeText(this, "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                saveLandlordToFirebase(landlord);
            } else {
                outProgress();
                Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLandlordToFirebase(Models.User_Landlord landlord) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USER_DB).document(landlord.getUid()).set(landlord).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                accountCreationSuccess();
            } else {
                accountCreationFail(Objects.requireNonNull(task.getException()).toString());
            }
        });
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (!isConnected) {
            signUpButton.setEnabled(false);
            Snackbar.make(findViewById(android.R.id.content), "Connect to continue", Snackbar.LENGTH_LONG).show();
        } else {
            signUpButton.setEnabled(true);
            Snackbar.make(findViewById(android.R.id.content), "Connected", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        mInternetAvailabilityChecker.removeInternetConnectivityChangeListener(this);
        super.onDestroy();

    }

    private void inProgress() {
        contentLoadingProgressBar.setVisibility(View.VISIBLE);
        signUpButton.setEnabled(false);
    }

    private void outProgress() {
        contentLoadingProgressBar.setVisibility(View.GONE);
        signUpButton.setEnabled(true);
    }

    private void accountCreationSuccess() {
        outProgress();
        Toast.makeText(SignUpActivity.this, "Landlord saved", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SignUpActivity.this, GetStarted1.class));
        finish();
    }

    private void accountCreationFail(String fail) {
        Toast.makeText(SignUpActivity.this, fail, Toast.LENGTH_SHORT).show();
        outProgress();
    }

}