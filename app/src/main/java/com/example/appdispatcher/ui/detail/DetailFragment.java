package com.example.appdispatcher.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.appdispatcher.Adapter.SectionPagerAdapterPayment;
import com.example.appdispatcher.FabActivity;
import com.example.appdispatcher.R;
import com.google.android.material.tabs.TabLayout;

public class DetailFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;

    public static final String GET_ID_JOB = "get_id_job";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        viewPager = root.findViewById(R.id.viewPagerDetail);

        tabLayout = root.findViewById(R.id.tabLayoutDetail);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item3 = menu.findItem(R.id.item_filter);
        MenuItem item2 = menu.findItem(R.id.item_search);
        MenuItem item4 = menu.findItem(R.id.item_filter_date);
        item4.setVisible(false);
        item3.setVisible(false);
        item2.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_notif) {
            Intent intent = new Intent(getContext(), FabActivity.class);
            intent.putExtra(GET_ID_JOB, "notif");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setAdapter(adapter);
    }


}
