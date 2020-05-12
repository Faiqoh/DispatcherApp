package com.example.appdispatcher.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.appdispatcher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListJobCategory extends Fragment {

    public ListJobCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_job_category, container, false);
    }
}
