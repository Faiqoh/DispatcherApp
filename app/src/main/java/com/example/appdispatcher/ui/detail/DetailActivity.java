package com.example.appdispatcher.ui.detail;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.home.HomeFragment;
import com.example.appdispatcher.ui.home.HomeViewModel;
import com.example.appdispatcher.ui.home.JobCategoryViewModel;
import com.example.appdispatcher.ui.home.ListJobCategory;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Memulai transaksi
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // mengganti isi container dengan fragment baru
        ft.replace(R.id.fragmentDetail, new ListJobCategory());
        // atau ft.add(R.id.your_placeholder, new FooFragment());
        // mulai melakukan hal di atas (jika belum di commit maka proses di atas belum dimulai)
        ft.commit();

        HomeViewModel leadJobList = (HomeViewModel) getIntent().getSerializableExtra(HomeFragment.ID_JOB);

        JobCategoryViewModel leadJobCat = (JobCategoryViewModel) getIntent().getSerializableExtra(HomeFragment.ID_JOB2);

        getSupportActionBar().setTitle("Detail Job");
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
