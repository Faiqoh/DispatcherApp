package com.example.appdispatcher.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.JobAppliedAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppliedFragment extends Fragment implements JobAppliedAdapter.AJListAdapter {

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    public List<AppliedViewModel> aList = new ArrayList<>();
    JobAppliedAdapter aAdapter;

    public static AppliedFragment newInstance() {
        return new AppliedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.applied_fragment, container, false);

        RecyclerView recyclerViewPendingJobList = view.findViewById(R.id.recyclerViewApplied);
        LinearLayoutManager layoutManagerPendingJobList = new LinearLayoutManager(getActivity());
        recyclerViewPendingJobList.setLayoutManager(layoutManagerPendingJobList);
        aList.clear();
        fillDataJobAppliedList();
        aAdapter = new JobAppliedAdapter(this, aList);
        recyclerViewPendingJobList.setAdapter(aAdapter);
        return view;
    }

    private void fillDataJobAppliedList() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJobListSumm, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONArray("job");

                    if (response.length() > 0) {

                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            AppliedViewModel itemCategory = new AppliedViewModel();
                            itemCategory.setCategory(cat.getJSONObject("category").getString("category_name"));
                            itemCategory.setJudul(cat.getString("job_name"));
                            itemCategory.setId_job(cat.getString("id"));
                            itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                            itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                            itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));

                            aList.add(itemCategory);
                        }
                        aAdapter.notifyDataSetChanged();
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
        Intent intent = new Intent(getContext(), ScrollingActivityDetailTask.class);
        intent.putExtra(ID_JOB, aAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_list");
        startActivity(intent);
    }
}