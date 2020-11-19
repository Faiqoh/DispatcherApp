package com.example.appdispatcher.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.appdispatcher.FabActivity;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail_project.DetailProjectFragment;
import com.example.appdispatcher.ui.payment.DetailPaymentFragment;
import com.example.appdispatcher.ui.support.DetailSupportFragment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ScrollingActivityDetailTask extends AppCompatActivity {
    FloatingActionsMenu floatingActionsMenu;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_detail_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        Bundle extras = getIntent().getExtras();
        floatingActionsMenu = findViewById(R.id.fab_menu);
        floatingActionButton = findViewById(R.id.fab_message);

        String getJob = extras.getString("get_id_job");

        if (getJob.equals("id_list") || getJob.equals("id_job_applied")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.pending_fragment, new AppliedAcceptFragment());
            ft.commit();
            getSupportActionBar().setTitle("Detail Job");

            floatingActionsMenu.setVisibility(View.GONE);
            floatingActionButton.setVisibility(View.GONE);
        } else if (getJob.equals("id_job_progress") || getJob.equals("id_job_done")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.pending_fragment, new ProgressDoneFragment());
            ft.commit();
            getSupportActionBar().setTitle("Job Progress Detail");

            floatingActionsMenu.setVisibility(View.GONE);
            floatingActionButton.setVisibility(View.GONE);
        } else if (getJob.equals("id_payment")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.pending_fragment, new DetailPaymentFragment());
            ft.commit();
            getSupportActionBar().setTitle("Detail Payment");

            floatingActionsMenu.setVisibility(View.GONE);
            floatingActionButton.setVisibility(View.GONE);
        } else if (getJob.equals("id_job")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.pending_fragment, new DetailProjectFragment());
            ft.commit();
            getSupportActionBar().setTitle("Detail Job");

            floatingActionsMenu.setVisibility(View.GONE);
            floatingActionButton.setVisibility(View.GONE);
        } else if (getJob.equals("id_support")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.pending_fragment, new DetailSupportFragment());
            ft.commit();
            getSupportActionBar().setTitle("Detail Support");

            floatingActionsMenu.setVisibility(View.GONE);
            floatingActionButton.setVisibility(View.VISIBLE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1  && resultCode  == 1) {

                String id_job = data.getStringExtra("id_job");
                ProgressDoneFragment frag = (ProgressDoneFragment) getSupportFragmentManager().findFragmentById(R.id.pending_fragment);
                frag.fillDetail(id_job);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}