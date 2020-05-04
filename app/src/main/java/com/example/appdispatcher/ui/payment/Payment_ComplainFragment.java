package com.example.appdispatcher.ui.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.appdispatcher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Payment_ComplainFragment extends Fragment {

    public Payment_ComplainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment__complain, container, false);
    }
}
