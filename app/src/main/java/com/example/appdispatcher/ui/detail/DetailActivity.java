package com.example.appdispatcher.ui.detail;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail_project.DetailProjectFragment;
import com.example.appdispatcher.ui.home.HomeFragment;
import com.example.appdispatcher.ui.home.JobCategoryViewModel;
import com.example.appdispatcher.ui.home.ListJobCategory;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();

        String getJob = extras.getString("get_id_job");
//
        if (getJob.equals("id_category")) {
            // Memulai transaksi
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // mengganti isi container dengan fragment baru
            ft.replace(R.id.fragmentDetail, new ListJobCategory());
            // atau ft.add(R.id.your_placeholder, new FooFragment());
            // mulai melakukan hal di atas (jika belum di commit maka proses di atas belum dimulai)
            ft.commit();

            JobCategoryViewModel leadJobCat = (JobCategoryViewModel) getIntent().getSerializableExtra(HomeFragment.ID_JOB2);

            getSupportActionBar().setTitle(leadJobCat.judul);
        } else if (getJob.equals("id_list") || getJob.equals("id_job")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // mengganti isi container dengan fragment baru
            ft.replace(R.id.fragmentDetail, new DetailProjectFragment());
            // atau ft.add(R.id.your_placeholder, new FooFragment());
            // mulai melakukan hal di atas (jika belum di commit maka proses di atas belum dimulai)
            ft.commit();
            getSupportActionBar().setTitle("Detail Job");
        } else if (getJob.equals("id_all_category")) {
            // Memulai transaksi
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // mengganti isi container dengan fragment baru
            ft.replace(R.id.fragmentDetail, new ListJobCategory());
            // atau ft.add(R.id.your_placeholder, new FooFragment());
            // mulai melakukan hal di atas (jika belum di commit maka proses di atas belum dimulai)
            ft.commit();

            String getcategory = extras.getString("category_name");

            getSupportActionBar().setTitle(getcategory);
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
