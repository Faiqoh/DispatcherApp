package com.example.appdispatcher.ui.detail;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appdispatcher.R;

public class DetailProgressProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_progress_project);

        String detail_pending = getIntent().getStringExtra("detail_pending");
        String detail_progress = getIntent().getStringExtra("detail_progress");
        if (detail_pending != null) {
            Fragment androidFragment = new DetailFragment();
            replaceFragment(androidFragment);
        } else if (detail_progress != null) {
            Fragment androidFragment2 = new DetailProgressTaskFragment();
            replaceFragment(androidFragment2);
        }
    }

    public void replaceFragment(Fragment destFragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.pending_fragment, destFragment);
        fragmentTransaction.commit();
    }
}
