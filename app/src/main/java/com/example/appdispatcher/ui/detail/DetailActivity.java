package com.example.appdispatcher.ui.detail;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdispatcher.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setTitle("Detail Job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
