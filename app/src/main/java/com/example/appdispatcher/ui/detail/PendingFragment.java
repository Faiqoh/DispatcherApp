package com.example.appdispatcher.ui.detail;

import android.content.Intent;
import android.content.res.Resources;
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
import com.example.appdispatcher.Adapter.JobPendingAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PendingFragment extends Fragment implements JobPendingAdapter.PJListAdapter {

    public static final String ID_JOB = "id_job";
    public List<PendingViewModel> pList = new ArrayList<>();
    JobPendingAdapter pAdapter;

    public static PendingFragment newInstance() {
        return new PendingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        RecyclerView recyclerViewPendingJobList = view.findViewById(R.id.recyclerViewPending);
        LinearLayoutManager layoutManagerPendingJobList = new LinearLayoutManager(getActivity());
        recyclerViewPendingJobList.setLayoutManager(layoutManagerPendingJobList);
        pList.clear();
        fillDatJobPendingList();
        pAdapter = new JobPendingAdapter(this, pList);
        recyclerViewPendingJobList.setAdapter(pAdapter);

        return view;
    }

    private void fillDatJobPendingList() {

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJobListSumm, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONArray("job");

                    if (response.length() > 0) {
                        Resources resources = getResources();

//                        TypedArray a = resources.obtainTypedArray();
//                        String[] arFoto = new String[a.length()];
                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            String url = cat.getJSONObject("category").getString("category_image_url");

//                            String[] arFoto = new String[cat.getJSONObject("category").getString("category_image").length()];

                            PendingViewModel itemCategory = new PendingViewModel();
                            itemCategory.setJudul(cat.getJSONObject("category").getString("category_name"));
                            itemCategory.setId_job(cat.getString("id"));
                            itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                            itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                            itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));

                            pList.add(itemCategory);
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
        Intent intent = new Intent(getContext(), DetailProgressProjectActivity.class);
        intent.putExtra("detail_pending", "id");
        startActivity(intent);
    }
}
