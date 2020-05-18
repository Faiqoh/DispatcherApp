package com.example.appdispatcher.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.DetailJobCategoryAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.DetailActivity;
import com.example.appdispatcher.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListJobCategory extends Fragment implements DetailJobCategoryAdapter.CListAdapter {

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB_CATEGORY = "get_id_job_category";
    public static final String GET_ID_JOB = "get_id_job";
    ArrayList<HomeViewModel> cList = new ArrayList<>();
    DetailJobCategoryAdapter cAdapter;

    public ListJobCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_job_category, container, false);

        Bundle extras = getActivity().getIntent().getExtras();

        String getJob = extras.getString("get_id_job");

        // Inflate the layout for this fragment
        RecyclerView recyclerView2 = root.findViewById(R.id.recyclerViewlistJobCategory);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(layoutManager2);
        cAdapter = new DetailJobCategoryAdapter(this, cList);
        recyclerView2.setAdapter(cAdapter);
        if (getJob.equals("id_category")) {
            JobCategoryViewModel leadJobCat = (JobCategoryViewModel) getActivity().getIntent().getSerializableExtra(HomeFragment.ID_JOB2);
            Integer id_category = leadJobCat.id_category;
            fillData2(id_category);
        } else {
            fillData();
        }

        return root;
    }

    private void fillData() {
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

                            HomeViewModel itemCategory = new HomeViewModel();
                            if (cat.getString("job_status").equals("Open")) {
                                itemCategory.setId_job(cat.getString("id"));
                                itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                                itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                                itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));

                                cList.add(itemCategory);
                            }
                        }
                        cAdapter.notifyDataSetChanged();
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

    private void fillData2(final Integer id_category) {
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

                            HomeViewModel itemCategory = new HomeViewModel();
                            if (cat.getInt("id_category") == id_category) {
                                if (cat.getString("job_status").equals("Open")) {
                                    itemCategory.setId_job(cat.getString("id"));
                                    itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                                    itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                                    itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));

                                    cList.add(itemCategory);
                                }
                            }
                        }
                        cAdapter.notifyDataSetChanged();
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


    public void doClick(int pos) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(ID_JOB, cAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_job");
        startActivity(intent);
    }
}
