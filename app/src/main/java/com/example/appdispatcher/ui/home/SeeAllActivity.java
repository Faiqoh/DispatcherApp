package com.example.appdispatcher.ui.home;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.appdispatcher.R;

public class SeeAllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        Bundle extras = getIntent().getExtras();

        String getJob = extras.getString("get_id_job");

        if (getJob.equals("job_category")) {
            getSupportActionBar().setTitle("Job Category");
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
            // Memulai transaksi
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // mengganti isi container dengan fragment baru
            ft.replace(R.id.fragmentAllCategory, new AllFragmentCategory());
            // atau ft.add(R.id.your_placeholder, new FooFragment());
            // mulai melakukan hal di atas (jika belum di commit maka proses di atas belum dimulai)
            ft.commit();
        } else if (getJob.equals("id_category")) {
            // Memulai transaksi
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // mengganti isi container dengan fragment baru
            ft.replace(R.id.fragmentAllCategory, new ListJobCategory());
            // atau ft.add(R.id.your_placeholder, new FooFragment());
            // mulai melakukan hal di atas (jika belum di commit maka proses di atas belum dimulai)
            ft.commit();

            JobCategoryViewModel leadJobCat = (JobCategoryViewModel) getIntent().getSerializableExtra(HomeFragment.ID_JOB2);

            getSupportActionBar().setTitle(leadJobCat.judul);
        } else {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentAllCategory, new ListJobCategory());
            getSupportActionBar().setTitle("Job List");
            ft.commit();
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
