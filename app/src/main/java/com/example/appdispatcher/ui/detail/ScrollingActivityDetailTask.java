package com.example.appdispatcher.ui.detail;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail_project.DetailProjectFragment;
import com.example.appdispatcher.ui.payment.DetailPaymentFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ScrollingActivityDetailTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_detail_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        Bundle extras = getIntent().getExtras();

        String getJob = extras.getString("get_id_job");

        if (getJob.equals("id_list") || getJob.equals("id_job_applied")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.pending_fragment, new AppliedAcceptFragment());
            ft.commit();
            getSupportActionBar().setTitle("Detail Job");
        } else if (getJob.equals("id_job_progress") || getJob.equals("id_job_done")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.pending_fragment, new ProgressDoneFragment());
            ft.commit();
            getSupportActionBar().setTitle("Detail Job");
        } else if (getJob.equals("id_payment")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.pending_fragment, new DetailPaymentFragment());
            ft.commit();
            getSupportActionBar().setTitle("Detail Payment");
        } else if (getJob.equals("id_job")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.pending_fragment, new DetailProjectFragment());
            ft.commit();
            getSupportActionBar().setTitle("Detail Job");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}