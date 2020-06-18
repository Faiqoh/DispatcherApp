package com.example.appdispatcher;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.appdispatcher.ui.fab.DoneFabFragment;
import com.example.appdispatcher.ui.fab.RequestFabFragment;

public class FabActivity extends AppCompatActivity {

    EditText etsum, etroot, etcounter;
    TextView tvIdJob;
    Button btn_upload;
    String summary, root, counter, id_job;

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
            getSupportActionBar().setTitle("Detail Job");
        } else if (getJob.equals("id_job_request")) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fab_fragment, new RequestFabFragment());
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