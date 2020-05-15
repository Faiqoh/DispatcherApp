package com.example.appdispatcher.ui.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.appdispatcher.R;

public class DetailProgressProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_progress_project);

//        JobCategoryViewModel leadJobCat = (JobCategoryViewModel) getIntent().getSerializableExtra(HomeFragment.ID_JOB2);

        Bundle extras = getIntent().getExtras();

        String getJob = extras.getString("get_id_job");


        Log.i(String.valueOf(getJob), "isi getJob2 ");

        if (getJob.equals("id_list")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // mengganti isi container dengan fragment baru
            ft.replace(R.id.pending_fragment, new DetailFragment());
            // atau ft.add(R.id.your_placeholder, new FooFragment());
            // mulai melakukan hal di atas (jika belum di commit maka proses di atas belum dimulai)
            ft.commit();
            getSupportActionBar().setTitle("Detail Job");
        } else if (getJob.equals("id_job_progress")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // mengganti isi container dengan fragment baru
            ft.replace(R.id.pending_fragment, new WritingTaskFragment());
            // atau ft.add(R.id.your_placeholder, new FooFragment());
            // mulai melakukan hal di atas (jika belum di commit maka proses di atas belum dimulai)
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
