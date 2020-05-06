package com.example.appdispatcher.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appdispatcher.Adapter.ProgressTaskAdapter;
import com.example.appdispatcher.R;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    ArrayList<DetailProgressViewModel> pList = new ArrayList<>();
    ProgressTaskAdapter pAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        return view;
    }
}