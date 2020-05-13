package com.example.appdispatcher.ui.detail;

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
import com.example.appdispatcher.Adapter.JobDoneAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoneFragment extends Fragment implements JobDoneAdapter.DJListAdapter {

    private DoneViewModel mViewModel;

    public List<DoneViewModel> dList = new ArrayList<>();
    JobDoneAdapter dAdapter;

    public static DoneFragment newInstance() {
        return new DoneFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done, container, false);

        RecyclerView recyclerViewDoneJobList = view.findViewById(R.id.recyclerViewDone);
        LinearLayoutManager layoutManagerDoneJobList = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewDoneJobList.setLayoutManager(layoutManagerDoneJobList);
        dList.clear();
        fillDatJobPendingList();
        dAdapter = new JobDoneAdapter(this, dList);
        recyclerViewDoneJobList.setAdapter(dAdapter);

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

                            DoneViewModel itemCategory = new DoneViewModel();
                            itemCategory.setJudul(cat.getJSONObject("category").getString("category_name"));
                            itemCategory.setId_job(cat.getString("id"));
                            itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                            itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                            itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));

                            dList.add(itemCategory);
                        }
                        dAdapter.notifyDataSetChanged();
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

    }
}
