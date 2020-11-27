package com.example.appdispatcher.ui.fab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdispatcher.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressJobFabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressJobFabFragment extends Fragment {

    public ProgressJobFabFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ProgressJobFabFragment newInstance(String param1, String param2) {
        ProgressJobFabFragment fragment = new ProgressJobFabFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress_job_fab, container, false);
    }
}