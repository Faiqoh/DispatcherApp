package com.example.appdispatcher.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.R;

public class NotifFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notif, container, false);

        RecyclerView rvnotif = view.findViewById(R.id.recyclerViewNotif);
        LinearLayoutManager lmnotif = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvnotif.setLayoutManager(lmnotif);

        return view;
    }
}