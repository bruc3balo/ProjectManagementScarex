package com.example.projectmanagement.pages.getstarted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectmanagement.R;

public class GetStarted2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started2);
    }

    public void goGetStarted3(View view) {
        startActivity(new Intent(GetStarted2.this,GetStarted3.class));
        finish();
    }
}