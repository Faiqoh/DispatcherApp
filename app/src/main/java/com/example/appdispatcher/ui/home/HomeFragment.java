package com.example.appdispatcher.ui.home;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.JobCategoryAdapter;
import com.example.appdispatcher.Adapter.JobListAdapter;
import com.example.appdispatcher.Adapter.RecomenJobAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ArrayList<HomeViewModel> mList = new ArrayList<>();
    JobListAdapter mAdapter;
    ArrayList<RecomendJobViewModel> rList = new ArrayList<>();
    RecomenJobAdapter rAdapter;
    //    ArrayList<JobCategoryViewModel>  = new ArrayList<>();
    public List<JobCategoryViewModel> cList = new ArrayList<>();
    JobCategoryAdapter cAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_name);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        RecyclerView recyclerViewJobCategory = root.findViewById(R.id.recyclerViewJobCategory);
        LinearLayoutManager layoutManagerJobCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewJobCategory.setLayoutManager(layoutManagerJobCategory);
        cAdapter = new JobCategoryAdapter(this, cList);
        recyclerViewJobCategory.setAdapter(cAdapter);
        fillDataJobCategory();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewlistjob);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new JobListAdapter(mList);
        recyclerView.setAdapter(mAdapter);
        fillData();

        RecyclerView recyclerView2 = root.findViewById(R.id.recyclerViewrecomendjob);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(layoutManager2);
        rAdapter = new RecomenJobAdapter(rList);
        recyclerView2.setAdapter(rAdapter);
        fillData2();
        return root;
    }

    private void fillDataJobCategory() {
//        Resources resources = getResources();
//        String[] arJudul = resources.getStringArray(R.array.title);
//        TypedArray a = resources.obtainTypedArray(R.array.job_category);
//        Drawable[] arFoto = new Drawable[a.length()];
//        for (int i = 0; i < arFoto.length; i++) {
//            arFoto[i] = a.getDrawable(i);
//        }
//        a.recycle();
//
//        for (int i = 0; i < arFoto.length; i++) {
//            cList.add(new JobCategoryViewModel(arFoto[i]));
//        }
//        cAdapter.notifyDataSetChanged();

        final JSONObject jobj = new JSONObject();
        final String category_name = "null";
        try {
            jobj.put("category_name", category_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJobCategory, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;

                Log.i("response", String.valueOf(jobj.length()));

                if (response.length() > 0) {
                    for (int i = 0; i < jObj.length(); i++) {
                        JobCategoryViewModel itemCategory = new JobCategoryViewModel();
                        try {
                            itemCategory.setJudul(jObj.getString("category_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        cList.add(itemCategory);
                    }
                    cAdapter.notifyDataSetChanged();
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


    private void fillData2() {
        Resources resources = getResources();
        String[] arJudul2 = resources.getStringArray(R.array.titlejob);
        TypedArray a = resources.obtainTypedArray(R.array.recomend_job);
        Drawable[] arFoto2 = new Drawable[a.length()];
        for (int i = 0; i < arFoto2.length; i++) {
            arFoto2[i] = a.getDrawable(i);
        }
        a.recycle();

        for (int i = 0; i < arJudul2.length; i++) {
            rList.add(new RecomendJobViewModel(arJudul2[i], arFoto2[i]));
        }
        rAdapter.notifyDataSetChanged();
    }

    private void fillData() {
        Resources resources = getResources();
        String[] arJudul = resources.getStringArray(R.array.title);
        TypedArray a = resources.obtainTypedArray(R.array.list_job);
        Drawable[] arFoto = new Drawable[a.length()];
        for (int i = 0; i < arFoto.length; i++) {
            arFoto[i] = a.getDrawable(i);
        }
        a.recycle();

        for (int i = 0; i < arJudul.length; i++) {
            mList.add(new HomeViewModel(arJudul[i], arFoto[i]));
        }
        mAdapter.notifyDataSetChanged();
    }
}