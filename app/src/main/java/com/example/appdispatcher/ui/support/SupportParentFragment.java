package com.example.appdispatcher.ui.support;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdispatcher.Adapter.SectionPagerAdapterPayment;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.payment.Payment_ComplainFragment;
import com.example.appdispatcher.ui.payment.Payment_PaymentFragment;
import com.google.android.material.tabs.TabLayout;

public class SupportParentFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support_parent, container, false);

        viewPager = view.findViewById(R.id.viewPagerSupport);
        tabLayout = view.findViewById(R.id.tabLayoutSupport);

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

        adapter.addFragment(new SupportFragment(), "Support");
        adapter.addFragment(new ChatInitiateModeratorFragment(), "Moderator");

        viewPager.setAdapter(adapter);
    }
}