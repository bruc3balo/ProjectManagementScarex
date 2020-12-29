package com.example.projectmanagement.pages.getstarted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectmanagement.R;

public class GetStarted1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started1);
    }

    public void goGetStarted2(View view) {
        startActivity(new Intent(GetStarted1.this,GetStarted2.class));
        finish();
    }
}