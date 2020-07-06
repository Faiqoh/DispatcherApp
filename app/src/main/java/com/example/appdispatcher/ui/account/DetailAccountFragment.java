package com.example.appdispatcher.ui.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.DetailJobAplliedEngineerAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.AppliedViewModel;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailAccountFragment extends Fragment {

    public List<AppliedViewModel> aList = new ArrayList<>();
    DetailJobAplliedEngineerAdapter aAdapter;
    NestedScrollView nestedScrollView;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_account, container, false);
        RecyclerView rvdetail = view.findViewById(R.id.rvdetailengineer);
        LinearLayoutManager layoutManagerdetail = new LinearLayoutManager(getActivity());
        rvdetail.setLayoutManager(layoutManagerdetail);
        aList.clear();
        aAdapter = new DetailJobAplliedEngineerAdapter(this, aList);
        rvdetail.setAdapter(aAdapter);
        filldata();
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        nestedScrollView = view.findViewById(R.id.nested_detail_account);
        return view;

    }

    private void filldata() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJob_withToken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                nestedScrollView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONArray("job");
                    if (response.length() > 0) {

                        if (aList != null) {
                            aList.clear();
                        } else {
                            aList = new ArrayList<>();
                        }

                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            AppliedViewModel itemCategory = new AppliedViewModel();

                            if (cat.getString("job_status").equals("Ready") && cat.getJSONObject("working_engineer").getString("id_engineer").equals(jObj.getString("id_engineer")) ||
                                    cat.getString("job_status").equals("Progress") && cat.getJSONObject("working_engineer").getString("id_engineer").equals(jObj.getString("id_engineer")) ||
                                    cat.getString("job_status").equals("Done") && cat.getJSONObject("working_engineer").getString("id_engineer").equals(jObj.getString("id_engineer"))) {
                                itemCategory.setCategory(cat.getJSONObject("category").getString("category_name"));
                                itemCategory.setJudul(cat.getString("job_name"));
                                itemCategory.setId_job(cat.getString("id"));
                                itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                                itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                                itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));
                                itemCategory.setStatus(cat.getString("job_status"));
                                aList.add(itemCategory);

                            }
                            aAdapter.notifyDataSetChanged();
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