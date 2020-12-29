package com.example.projectmanagement.pages.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projectmanagement.R;
import com.example.projectmanagement.pages.landlord.home.Landlord_Home_Activity;
import com.example.projectmanagement.pages.tenant.home.Tenant_Home_Activity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.projectmanagement.models.Models.LANDLORD_ROLE;
import static com.example.projectmanagement.models.Models.ROLE;
import static com.example.projectmanagement.models.Models.TENANT_ROLE;
import static com.example.projectmanagement.models.Models.USER_DB;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> updateUi(firebaseAuth.getCurrentUser());
    private boolean backPressed;
    private ProgressBar loginPb;
    private Button loginButton;
    public static final String role_fail = "Failed to get role";
    private String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar loginTb = findViewById(R.id.loginTb);
        setSupportActionBar(loginTb);
        loginTb.setNavigationOnClickListener(v -> finishAffinity());

        TextView register_tv = findViewById(R.id.register_tv);
        register_tv.setOnClickListener(v -> showSignUpPage());

        EditText email_login_field = findViewById(R.id.email_login_field), password_field = findViewById(R.id.password_field);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            if (validateForm(email_login_field, password_field)) {
                signInUser(email_login_field.getText().toString(), password_field.getText().toString(), loginButton);
            } else {
                Toast.makeText(LoginActivity.this, "Check details", Toast.LENGTH_SHORT).show();
            }
        });

        loginPb = findViewById(R.id.loginPb);
        outProgress();

        backPressed = false;

    }

    private void showSignUpPage() {
        Dialog d = new Dialog(LoginActivity.this);
        d.setContentView(R.layout.landlord_dialog);
        Button proceed_dialog = d.findViewById(R.id.proceed_dialog), cancel_dialog = d.findViewById(R.id.cancel_dialog);

        d.show();

        proceed_dialog.setOnClickListener(v -> {
            d.dismiss();
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });
        cancel_dialog.setOnClickListener(v -> d.dismiss());
    }

    private boolean validateForm(EditText email, EditText pass) {
        boolean valid = false;
        if (!email.getText().toString().contains("@") || email.getText().toString().length() < 4) {
            email.setError("Wrong format");
            email.requestFocus();
        } else if (pass.getText().toString().length() < 6) {
            pass.requestFocus();
            pass.setError("Password cannot be less than 6");
        } else {
            valid = true;
        }
        return valid;
    }

    private void signInUser(String email, String pass, Button signInB) {
        signInB.setEnabled(false);
        loginPb.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                updateUi(user);
                Toast.makeText(LoginActivity.this, "Welcome " + Objects.requireNonNull(user).getEmail(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                outProgress();
            }
        });
    }

    private boolean isLandlord(String role) {
        boolean isLandlord;
        if (role.equals(LANDLORD_ROLE)) {
            isLandlord = true;
        } else if (role.equals(TENANT_ROLE)) {
            isLandlord = false;
        } else {
            isLandlord = false;
            Toast.makeText(this, role_fail, Toast.LENGTH_SHORT).show();
        }
        return isLandlord;
    }

    private void updateUi(FirebaseUser user) {
        if (user != null) {
            inProgress();
            Toast.makeText(LoginActivity.this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
            getRole(user.getUid());
        } else {
            outProgress();
            Toast.makeText(LoginActivity.this, "Sign in to continue", Toast.LENGTH_SHORT).show();
        }
    }

    private void inProgress() {
        loginButton.setEnabled(false);
        loginPb.setVisibility(View.VISIBLE);
    }

    private void outProgress() {
        loginButton.setEnabled(true);
        loginPb.setVisibility(View.GONE);
    }

    private void getRole(String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USER_DB).document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                try {
                    role = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get(ROLE)).toString();
                } catch (Exception e ) {
                    FirebaseAuth.getInstance().signOut();
                    Snackbar.make(findViewById(android.R.id.content), "Failed to get role", Snackbar.LENGTH_LONG).show();
                    updateUi(FirebaseAuth.getInstance().getCurrentUser());
                }
                chooseRole();
            } else {
                role = role_fail;
                chooseRole();
                Toast.makeText(LoginActivity.this, "Failed to get role", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void chooseRole() {
        switch (role) {
            case role_fail:
                outProgress();
                Toast.makeText(this, "Failed to get role. Seek help", Toast.LENGTH_SHORT).show();
                break;
            case LANDLORD_ROLE:
                startActivity(new Intent(LoginActivity.this, Landlord_Home_Activity.class));
                Toast.makeText(this, "Welcome " + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TENANT_ROLE:
                startActivity(new Intent(LoginActivity.this, Tenant_Home_Activity.class));
                Toast.makeText(this, "Welcome " + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(), Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backPressed = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        backPressed = false;
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
    protected void onDestroy() {
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        super.onDestroy();
    }

    public static String truncate(String value, int length) {
        // Ensure String length is longer than requested size.
        if (value.length() > length) {
            return value.substring(0, length);
        } else {
            return value;
        }
    }

}