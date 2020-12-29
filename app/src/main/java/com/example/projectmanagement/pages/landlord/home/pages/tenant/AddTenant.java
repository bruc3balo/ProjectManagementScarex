package com.example.projectmanagement.pages.landlord.home.pages.tenant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmanagement.R;
import com.example.projectmanagement.models.Models;
import com.example.projectmanagement.pages.login.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.projectmanagement.models.Models.TENANT_DB;
import static com.example.projectmanagement.models.Models.TENANT_ROLE;
import static com.example.projectmanagement.models.Models.TENANT_TYPE_COMPANY;
import static com.example.projectmanagement.models.Models.TENANT_TYPE_PERSON;
import static com.example.projectmanagement.models.Models.USER_DB;
import static com.example.projectmanagement.pages.landlord.home.Landlord_Home_Activity.updateUi;
import static com.example.projectmanagement.pages.login.SignUpActivity.time;

public class AddTenant extends AppCompatActivity {

    private boolean isPerson = false;
    private boolean inProgress = false;
    private LinearLayout personLayout;
    private LinearLayout companyLayout;
    private final Models.Tenant_Company tenant_company = new Models.Tenant_Company();
    private final Models.Tenant_Person tenant_person = new Models.Tenant_Person();
    private final String landlordUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);

        Toolbar addTenantTb = findViewById(R.id.addTenantTb);
        setSupportActionBar(addTenantTb);
        addTenantTb.setNavigationOnClickListener(v -> finish());

        personLayout = findViewById(R.id.personLayout);
        companyLayout = findViewById(R.id.companyLayout);

        TextView personChip = findViewById(R.id.personChip);
        TextView companyChip = findViewById(R.id.companyChip);


        personChip.setOnClickListener(v -> {
            isPerson = true;
            personChip.setBackground(getDrawable(R.drawable.pink_button_bg));
            companyChip.setBackground(getDrawable(R.drawable.pinkvariation_button_bg));
            setPersonLayout();
        });
        companyChip.setOnClickListener(v -> {
            isPerson = false;
            personChip.setBackground(getDrawable(R.drawable.pinkvariation_button_bg));
            companyChip.setBackground(getDrawable(R.drawable.pink_button_bg));
            setCompanyLayout();
        });
        setCompanyLayout();
    }

    private void showDobCal() {
        TextView dobPersonTenant = findViewById(R.id.dobPersonTenant);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTenant.this, (view, year, month, dayOfMonth) -> {
            String datePicked = dayOfMonth + "/" + ++month + "/" + year;
            dobPersonTenant.setText(datePicked);
            tenant_person.setDob(datePicked);
            Snackbar.make(findViewById(android.R.id.content), datePicked, Snackbar.LENGTH_LONG).show();
        }, 2008, 1, 1);
        datePickerDialog.show();
    }

    private void setPersonLayout() {
        isPerson = true;
        companyLayout.setVisibility(View.GONE);
        personLayout.setVisibility(View.VISIBLE);
    }

    private void setCompanyLayout() {
        isPerson = false;
        companyLayout.setVisibility(View.VISIBLE);
        personLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_property_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if (!inProgress) {
                if (isPerson) {
                    if (validatePersonForm()) {
                        createUserWithFirebase();
                    } else {
                        Toast.makeText(this, "Check details", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (validateCompanyForm()) {
                        createUserWithFirebase();
                    } else {
                        Toast.makeText(this, "Check details", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Wait", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validatePersonForm() {
        boolean valid = false;

        //Person
        EditText personTitleTenant = findViewById(R.id.personTitleTenant);
        EditText first_name_person_tenant = findViewById(R.id.first_name_person_tenant);
        EditText last_name_person_tenant = findViewById(R.id.last_name_person_tenant);

        EditText email_person_tenant = findViewById(R.id.email_person_tenant);
        EditText phone_number_person_tenant = findViewById(R.id.phone_number_person_tenant);
        EditText secretKeyPerson = findViewById(R.id.secret_key_person_tenant);

        TextView personDobClick = findViewById(R.id.personDobClick);
        personDobClick.setOnClickListener(v -> showDobCal());
        EditText personNotesTenant = findViewById(R.id.personNotesTenant);

        if (personTitleTenant.getText().toString().isEmpty()) {
            personTitleTenant.requestFocus();
            personTitleTenant.setError("Required");
        } else if (first_name_person_tenant.getText().toString().isEmpty()) {
            first_name_person_tenant.requestFocus();
            first_name_person_tenant.setError("Required");
        } else if (last_name_person_tenant.getText().toString().isEmpty()) {
            last_name_person_tenant.requestFocus();
            personTitleTenant.setError("Required");
        } else if (!email_person_tenant.getText().toString().contains("@")) {
            email_person_tenant.requestFocus();
            email_person_tenant.setError("Wrong format");
        } else if (phone_number_person_tenant.getText().toString().isEmpty()) {
            phone_number_person_tenant.requestFocus();
            phone_number_person_tenant.setError("Required");
        } else if (secretKeyPerson.getText().toString().length() < 6) {
            secretKeyPerson.requestFocus();
            secretKeyPerson.setError("Too short. Not less than 6");
        } else if (tenant_person.getDob() == null) {
            showDobCal();
        } else {
            if (!personNotesTenant.getText().toString().isEmpty()) {
                tenant_person.setNotes(personNotesTenant.getText().toString());
            } else {
                tenant_person.setNotes("");
            }
            tenant_person.setCreatedAt(time);
            tenant_person.setEmailAddress(email_person_tenant.getText().toString());

            tenant_person.setFirst_name(first_name_person_tenant.getText().toString());
            tenant_person.setLast_name(last_name_person_tenant.getText().toString());
            tenant_person.setPhoneNumber(phone_number_person_tenant.getText().toString());

            tenant_person.setSecret_key(secretKeyPerson.getText().toString());
            tenant_person.setRole(TENANT_ROLE);
            tenant_person.setLandlordUid(landlordUid);

            tenant_person.setTenantType(TENANT_TYPE_PERSON);

            valid = true;
        }

        return valid;
    }

    private boolean validateCompanyForm() {
        boolean valid = false;
        //Company
        EditText companyNameTenant = findViewById(R.id.companyNameTenant);
        EditText email_company_tenant = findViewById(R.id.email_company_tenant);
        EditText phone_company_tenant = findViewById(R.id.phone_company_tenant);

        EditText secretKeyCompany = findViewById(R.id.secret_key_company_tenant);
        EditText companyAddressTenant = findViewById(R.id.companyAddressTenant);
        EditText notesField = findViewById(R.id.notes_company_field);

        if (companyNameTenant.getText().toString().isEmpty()) {
            companyNameTenant.setError("Required");
            companyNameTenant.requestFocus();
        } else if (!email_company_tenant.getText().toString().contains("@")) {
            email_company_tenant.setError("Wrong format");
            email_company_tenant.requestFocus();
        } else if (phone_company_tenant.getText().toString().isEmpty()) {
            phone_company_tenant.requestFocus();
            phone_company_tenant.setError("Required");
        } else if (secretKeyCompany.getText().toString().length() < 6) {
            secretKeyCompany.setError("Too short");
            secretKeyCompany.requestFocus();
        } else if (companyAddressTenant.getText().toString().isEmpty()) {
            companyAddressTenant.requestFocus();
            companyAddressTenant.setError("Required");
        } else {
            if (!notesField.getText().toString().isEmpty()) {
                tenant_company.setNotes(notesField.getText().toString());
            } else {
                tenant_company.setNotes("");
            }
            tenant_company.setCreatedAt(time);
            tenant_company.setCompanyAddress(companyAddressTenant.getText().toString());

            tenant_company.setCompanyName(companyNameTenant.getText().toString());
            tenant_company.setSecret_key(secretKeyCompany.getText().toString());
            tenant_company.setEmailAddress(email_company_tenant.getText().toString());

            tenant_company.setPhoneNumber(phone_company_tenant.getText().toString());
            tenant_company.setRole(TENANT_ROLE);
            tenant_company.setLandlordUid(landlordUid);

            tenant_company.setTenantType(TENANT_TYPE_COMPANY);

            valid = true;
        }

        return valid;
    }

    private void createUserWithFirebase() {
        inProgress();
        if (isPerson) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(tenant_person.getEmailAddress(), tenant_person.getSecret_key()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddTenant.this, "Created person " + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(), Toast.LENGTH_SHORT).show();
                    tenant_person.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    saveToDataBase();
                } else {
                    outProgress();
                    Toast.makeText(AddTenant.this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(tenant_company.getEmailAddress(), tenant_company.getSecret_key()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddTenant.this, "Created company " + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(), Toast.LENGTH_SHORT).show();
                    tenant_company.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    saveToDataBase();
                } else {
                    outProgress();
                    Toast.makeText(AddTenant.this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveToDataBase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (isPerson) {
            db.collection(USER_DB).document(tenant_person.getUid()).set(tenant_person).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(this, "Saved Details successfully", Toast.LENGTH_SHORT).show();
                    outProgress();

                    updateUi(FirebaseAuth.getInstance().getCurrentUser(),AddTenant.this);
                } else {
                    Toast.makeText(this, "Failed to save details", Toast.LENGTH_SHORT).show();
                    Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).delete().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(AddTenant.this, "Cleared details. Try again", Toast.LENGTH_SHORT).show();
                        } else {
                            outProgress();
                            Toast.makeText(AddTenant.this, "Failed to clear details. Use different Email", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            db.collection(USER_DB).document(tenant_company.getUid()).set(tenant_company).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseAuth.getInstance().signOut();
                    outProgress();
                    Toast.makeText(this, "Saved Company Details successfully", Toast.LENGTH_SHORT).show();

                    updateUi(FirebaseAuth.getInstance().getCurrentUser(),AddTenant.this);
                } else {
                    Toast.makeText(this, "Failed to save details", Toast.LENGTH_SHORT).show();
                    Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).delete().addOnCompleteListener(task12 -> {
                    if (task12.isSuccessful()) {
                        Toast.makeText(AddTenant.this, "Cleared details. Try again", Toast.LENGTH_SHORT).show();
                    } else {
                        outProgress();
                        Toast.makeText(AddTenant.this, "Failed to clear details. Use different Email", Toast.LENGTH_SHORT).show();
                    }
                });
                }
            });
        }
    }

    private void inProgress() {
        inProgress = true;
        ProgressBar addTenantPb = findViewById(R.id.addTenantPb);
        addTenantPb.setVisibility(View.VISIBLE);
    }

    private void outProgress() {
        inProgress = false;
        ProgressBar addTenantPb = findViewById(R.id.addTenantPb);
        addTenantPb.setVisibility(View.GONE);
    }
}