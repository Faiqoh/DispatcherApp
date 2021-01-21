package com.example.appdispatcher.ui.detail2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdispatcher.Adapter.SectionPagerAdapterPayment;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.AcceptedFragment;
import com.example.appdispatcher.ui.detail.AppliedFragment;
import com.example.appdispatcher.ui.detail.DoneFragment;
import com.example.appdispatcher.ui.detail.OnProgressFragment;
import com.google.android.material.tabs.TabLayout;

public class Detail2Fragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail2, container, false);

        viewPager = view.findViewById(R.id.viewPagerDetail);

        tabLayout = view.findViewById(R.id.tabLayoutDetail);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapterPayment adapter = new SectionPagerAdapterPayment(getChildFragmentManager());

        adapter.addFragment(new JobProgressFragment(), "Progress");
        adapter.addFragment(new JobDoneFragment(), "Done");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setAdapter(adapter);
    }
}