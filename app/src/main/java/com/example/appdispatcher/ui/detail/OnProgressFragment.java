package com.example.appdispatcher.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.JobOnProgressAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnProgressFragment extends Fragment implements JobOnProgressAdapter.ProJListAdapter {

    private OnProgressViewModel mViewModel;

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    public List<OnProgressViewModel> pList = new ArrayList<>();
    JobOnProgressAdapter pAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    NestedScrollView nestedScrollView;
    RelativeLayout rvNotFound, rvProgress;
    BottomNavigationView navigation;

    public static OnProgressFragment newInstance() {
        return new OnProgressFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_progress, container, false);

        RecyclerView recyclerViewProgressJobList = view.findViewById(R.id.recyclerViewProgress);
        LinearLayoutManager layoutManagerProgressJobList = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewProgressJobList.setLayoutManager(layoutManagerProgressJobList);
        pList.clear();
        fillDataJobProgressList();
        pAdapter = new JobOnProgressAdapter(this, pList);
        recyclerViewProgressJobList.setAdapter(pAdapter);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        nestedScrollView = view.findViewById(R.id.nested_accept);

        rvNotFound = view.findViewById(R.id.RvNotFound);

        rvProgress = view.findViewById(R.id.relativelayoutprogress);

        navigation = getActivity().findViewById(R.id.nav_view);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            boolean isNavigationHide = false;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) { // up
                    animateNavigation(false);
                }
                if (scrollY > oldScrollY) { // down
                    animateNavigation(true);
                }
            }

            private void animateNavigation(boolean hide) {
                if (isNavigationHide && hide || !isNavigationHide && !hide) return;
                isNavigationHide = hide;
                int moveY = hide ? (2 * navigation.getHeight()) : 0;
                navigation.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.refreshProgress);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

                pList.clear();
                nestedScrollView.setVisibility(View.GONE);
                shimmerFrameLayout.startShimmerAnimation();
                fillDataJobProgressList();
            }
        });

        return view;
    }

    private void fillDataJobProgressList() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJob_withToken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                nestedScrollView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                try {
                    JSONArray jray = jObj.getJSONArray("job");

                    if (response.length() > 0) {

                        if (pList != null) {
                            pList.clear();
                        } else {
                            pList = new ArrayList<>();
                        }

                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            OnProgressViewModel itemCategory = new OnProgressViewModel();
                            if (cat.getString("job_status").equals("Progress") && cat.getJSONObject("working_engineer").getString("id_engineer").equals(jObj.getString("id_engineer"))) {
                                itemCategory.setCategory(cat.getJSONObject("category").getString("category_name"));
                                itemCategory.setJudul(cat.getString("job_name"));
                                itemCategory.setId_job(cat.getString("id"));
                                itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                                itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                                itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));

                                pList.add(itemCategory);
                            }

                            if (pList.size() > 0) {
                                rvNotFound.setVisibility(View.GONE);
                                rvProgress.setBackgroundColor(getResources().getColor(R.color.colorBackgroundTwo));
                            } else {
                                rvNotFound.setVisibility(View.VISIBLE);
                            }

                            pAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Accept", "applicaion/json");
                // Barer di bawah ini akan di simpan local masing-masing device engineer

//                headers.put("Authorization", "Bearer 14a1105cf64a44f47dd6d53f6b3beb79b65c1e929a6ee94a5c7ad30528d02c3e");
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    @Override
    public void doClick(int pos) {
        int LAUNCH_SECOND_ACTIVITY = 1;
        Intent intent = new Intent(getContext(), ScrollingActivityDetailTask.class);
        intent.putExtra(ID_JOB, pAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_job_progress");
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
//        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fillDataJobProgressList();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }
}
