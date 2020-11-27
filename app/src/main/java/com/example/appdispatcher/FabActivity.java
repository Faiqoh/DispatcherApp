package com.example.appdispatcher;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.appdispatcher.ui.account.DetailAccountFragment;
import com.example.appdispatcher.ui.account.EditAccountFragment;
import com.example.appdispatcher.ui.fab.DoneFabFragment;
import com.example.appdispatcher.ui.fab.ProgressJobFabFragment;
import com.example.appdispatcher.ui.fab.RequestFabFragment;
import com.example.appdispatcher.ui.fab.SupportFabFragment;
import com.example.appdispatcher.ui.home.NotifFragment;
import com.example.appdispatcher.ui.support.ChatFragment;

public class FabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);

        Bundle extras = getIntent().getExtras();

        String getJob = extras.getString("get_id_job");

        if (getJob.equals("id_job_done")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fab_fragment, new DoneFabFragment());
            ft.commit();
            getSupportActionBar().setTitle("Form Job Done");
        } else if (getJob.equals("id_job_request")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fab_fragment, new RequestFabFragment());
            ft.commit();
            getSupportActionBar().setTitle("Form Job Request");
        } else if (getJob.equals("detail_engineer")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fab_fragment, new DetailAccountFragment());
            ft.commit();
            getSupportActionBar().setTitle("Job Applied");
        } else if (getJob.equals("notif")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fab_fragment, new NotifFragment());
            ft.commit();
            getSupportActionBar().setTitle("Notification");
        } else if (getJob.equals("id_job_support")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fab_fragment, new SupportFabFragment());
            ft.commit();
            getSupportActionBar().setTitle("Form Get Support");
        } else if (getJob.equals("id_support_detail")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fab_fragment, new ChatFragment());
            ft.commit();
            getSupportActionBar().setTitle("Get Support");
        } else if (getJob.equals("edit_profile")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fab_fragment, new EditAccountFragment());
            ft.commit();
            getSupportActionBar().setTitle("Edit Profile");
        } else if (getJob.equals("id_progress_job")){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fab_fragment, new ProgressJobFabFragment());
            ft.commit();
            getSupportActionBar().setTitle("Form Progress Job");
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