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
import com.example.appdispatcher.Adapter.JobAcceptedAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AcceptedFragment extends Fragment implements JobAcceptedAdapter.PJListAdapter {

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    public List<AcceptedViewModel> pList = new ArrayList<>();
    JobAcceptedAdapter pAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accepted, container, false);

        RecyclerView recyclerViewPendingJobList = view.findViewById(R.id.recyclerViewPending);
        LinearLayoutManager layoutManagerPendingJobList = new LinearLayoutManager(getActivity());
        recyclerViewPendingJobList.setLayoutManager(layoutManagerPendingJobList);
        pList.clear();
        fillDatJobPendingList();
        pAdapter = new JobAcceptedAdapter(this, pList);
        recyclerViewPendingJobList.setAdapter(pAdapter);

        return view;
    }

    private void fillDatJobPendingList() {

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJobStatus, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONArray("job");

                    if (response.length() > 0) {

                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            JSONArray japplied = cat.getJSONArray("apply_engineer");
                            for (int j = 0; j < japplied.length(); j++) {
                                JSONObject applied = japplied.getJSONObject(j);

                                AcceptedViewModel itemCategory = new AcceptedViewModel();
                                if (applied.getInt("id_engineer") == 1 && applied.getString("status").equals("Accept") && cat.getString("job_status").equals("Open")) {
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
        intent.putExtra(GET_ID_JOB, "id_list");
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
//        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fillDatJobPendingList();
    }
}
