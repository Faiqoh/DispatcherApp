package com.example.appdispatcher.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.appdispatcher.Adapter.SectionPagerAdapterPayment;
import com.example.appdispatcher.R;
import com.google.android.material.tabs.TabLayout;

public class DetailFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        viewPager = root.findViewById(R.id.viewPagerDetail);

        tabLayout = root.findViewById(R.id.tabLayoutDetail);

//        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));

        return root;
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

        adapter.addFragment(new AppliedFragment(), "Applied");
        adapter.addFragment(new AcceptedFragment(), "Accepted");
        adapter.addFragment(new OnProgressFragment(), "Progress");
        adapter.addFragment(new DoneFragment(), "Done");

        viewPager.setAdapter(adapter);
    }
}
