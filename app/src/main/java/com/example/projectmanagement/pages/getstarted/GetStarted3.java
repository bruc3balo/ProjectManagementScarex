package com.example.projectmanagement.pages.getstarted;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projectmanagement.R;
import com.example.projectmanagement.pages.landlord.home.Landlord_Home_Activity;
import com.example.projectmanagement.pages.login.LoginActivity;
import com.example.projectmanagement.pages.property.NewProperty1;
import com.example.projectmanagement.pages.tenant.home.Tenant_Home_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.projectmanagement.models.Models.LANDLORD_ROLE;
import static com.example.projectmanagement.models.Models.ROLE;
import static com.example.projectmanagement.models.Models.TENANT_ROLE;
import static com.example.projectmanagement.models.Models.USER_DB;
import static com.example.projectmanagement.pages.login.LoginActivity.role_fail;

public class GetStarted3 extends AppCompatActivity {

    private String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started3);
        getRole(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
    }

    public void finishOnBoarding(View view) {
        if (isLandlord(role)) {
            askPropertyInput();
        } else {
            startActivity(new Intent(GetStarted3.this, Tenant_Home_Activity.class));
        }
    }

    private void askPropertyInput() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(GetStarted3.this);
        builder1.setMessage("Welcome \n Would you like to enter a property now?");

        builder1.setCancelable(false);

        builder1.setPositiveButton("Add Now", (dialog, id) -> startActivity(new Intent(GetStarted3.this, NewProperty1.class)));
        builder1.setNegativeButton("Later", (dialog, id) -> startActivity(new Intent(GetStarted3.this, Landlord_Home_Activity.class)));

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void getRole(String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USER_DB).document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                role = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get(ROLE)).toString();
            } else {
                role = role_fail;
                Toast.makeText(GetStarted3.this, "Failed to get role", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Failed to get role", Toast.LENGTH_SHORT).show();
        }
        return isLandlord;
    }
}