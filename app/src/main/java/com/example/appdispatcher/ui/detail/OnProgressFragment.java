package com.example.appdispatcher.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OnProgressFragment extends Fragment implements JobOnProgressAdapter.ProJListAdapter {

    private OnProgressViewModel mViewModel;

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    public List<OnProgressViewModel> pList = new ArrayList<>();
    JobOnProgressAdapter pAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    NestedScrollView nestedScrollView;

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
                fillDataJobProgressList();
            }
        });

        return view;
    }

    private void fillDataJobProgressList() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJobStatus, null, new Response.Listener<JSONObject>() {
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

                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            JSONArray japplied = cat.getJSONArray("apply_engineer");
                            for (int j = 0; j < japplied.length(); j++) {
                                JSONObject applied = japplied.getJSONObject(j);

                                OnProgressViewModel itemCategory = new OnProgressViewModel();
                                if (applied.getInt("id_engineer") == 1 && applied.getString("status").equals("Accept") && cat.getString("job_status").equals("Progress")) {
                                    itemCategory.setCategory(cat.getJSONObject("category").getString("category_name"));
                                    itemCategory.setJudul(cat.getString("job_name"));
                                    itemCategory.setId_job(cat.getString("id"));
                                    itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                                    itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                                    itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));

                                    pList.add(itemCategory);
                                }
                            }
                        }
                        pAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
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
}
