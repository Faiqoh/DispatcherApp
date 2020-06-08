package com.example.appdispatcher.ui.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appdispatcher.R;

public class DetailPaymentFragment extends Fragment {

    public static DetailPaymentFragment newInstance() {
        return new DetailPaymentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_payment, container, false);

        Bundle extras = getActivity().getIntent().getExtras();

        String getJob = extras.getString("get_id_job");

        return view;

    }

}