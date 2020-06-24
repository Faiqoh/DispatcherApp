package com.example.appdispatcher.ui.home;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.ChildAllCategoryAdapter;
import com.example.appdispatcher.Adapter.ParentAllCategoryAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragmentCategory extends Fragment {

    public List<JobCategoryViewModel> cList = new ArrayList<>();
    public List<JobCategoryViewModel> childList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    ParentAllCategoryAdapter cAdapter;
    ChildAllCategoryAdapter childAdapter;
    ProgressBar progressBar;
    LinearLayout LlAllCategory;

    public AllFragmentCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_all_category, container, false);

        progressBar = v.findViewById(R.id.progressBarApplied);
        LlAllCategory = v.findViewById(R.id.LlAllCategory);
        RecyclerView recyclerViewJobAllCategory = v.findViewById(R.id.recyclerviewAllCategory);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewJobAllCategory.setLayoutManager(layoutManager);
        recyclerViewJobAllCategory.setHasFixedSize(true);
        cAdapter = new ParentAllCategoryAdapter(getActivity(), cList);
        recyclerViewJobAllCategory.setAdapter(cAdapter);
//        LinearLayoutManager layoutManagerJobCategory = new LinearLayoutManager(getActivity());
//        recyclerViewJobAllCategory.setLayoutManager(layoutManagerJobCategory);
//        cAdapter = new ParentAllCategoryAdapter(this,cList);
//        recyclerViewJobAllCategory.setAdapter(cAdapter);

        fillAllCategory();
        return v;
    }

    private void fillAllCategory() {
        progressBar.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);
        LlAllCategory.setVisibility(View.GONE);
        Log.i("!!", "Fill!!");
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getAllCategory, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressBar.setVisibility(View.GONE);
                LlAllCategory.setVisibility(View.VISIBLE);
                //iki isine piye?
                //pdoo tapi mek nampilne judul e
                //ono category ne pisan?
                //nampilne keseluruhan tiap kategori
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONArray("job_category_all");

                    if (response.length() > 0) {

                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            JSONArray cray = cat.getJSONArray("category");

                            JobCategoryViewModel items = new JobCategoryViewModel();

                            items.setMain_category(cat.getString("category_main_name"));
                            Integer cat_main_id = cat.getInt("id");

                            List<JobCategoryViewModel> details = new ArrayList<>();
                            for (int j = 0; j < cray.length(); j++) {

                                JSONObject det_cat = cray.getJSONObject(j);

                                JobCategoryViewModel itemsdetail = new JobCategoryViewModel();

                                itemsdetail.setFoto(det_cat.getString("category_image_url"));
                                itemsdetail.setJudul(det_cat.getString("category_name"));
                                itemsdetail.setId_category(det_cat.getInt("id"));

                                details.add(itemsdetail);
                            }

                            items.setDetails(details);

                            cList.add(items);
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
                error.printStackTrace();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }
}
