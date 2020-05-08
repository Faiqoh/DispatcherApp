package com.example.appdispatcher.ui.home;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
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
import com.example.appdispatcher.ui.detail.DetailActivity;
import com.example.appdispatcher.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements JobListAdapter.JListAdapter {

    private HomeViewModel homeViewModel;
    //    ArrayList<HomeViewModel> mList = new ArrayList<>();
    public List<HomeViewModel> mList = new ArrayList<>();
    JobListAdapter mAdapter;
    ArrayList<RecomendJobViewModel> rList = new ArrayList<>();
    RecomenJobAdapter rAdapter;
    //    ArrayList<JobCategoryViewModel>  = new ArrayList<>();
    public List<JobCategoryViewModel> cList = new ArrayList<>();
    JobCategoryAdapter cAdapter;
    TextView name, detailUser;

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
        fillAccountUser();

        name = root.findViewById(R.id.text_name);
        detailUser = root.findViewById(R.id.text_detail);

        RecyclerView recyclerViewJobCategory = root.findViewById(R.id.recyclerViewJobCategory);
        LinearLayoutManager layoutManagerJobCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewJobCategory.setLayoutManager(layoutManagerJobCategory);
        cAdapter = new JobCategoryAdapter(this, cList);
        recyclerViewJobCategory.setAdapter(cAdapter);
        fillDataJobCategory();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewlistjob);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new JobListAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);
//        fillData();
        fillDataListJob();

        RecyclerView recyclerView2 = root.findViewById(R.id.recyclerViewrecomendjob);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(layoutManager2);
        rAdapter = new RecomenJobAdapter(rList);
        recyclerView2.setAdapter(rAdapter);
        fillData2();
        return root;
    }

    private void fillAccountUser() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.getUser + "/?id_user=" + 1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jUser = response.getJSONObject("users");

                    Log.i("users", jUser.toString());

                    name.setText("Hi " + jUser.getString("name"));
                    detailUser.setText(jUser.getString("email") + "\n\n" + jUser.getString("address"));

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

    private void fillDataListJob() {
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

                            itemCategory.setJudul(cat.getJSONObject("category").getString("category_name"));
                            itemCategory.setFoto(ResourcesCompat.getDrawable(getResources(), R.drawable.android, null));
                            itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                            itemCategory.setLocation(cat.getJSONObject("location").getString("location_name"));

                            mList.add(itemCategory);
                        }
                        mAdapter.notifyDataSetChanged();
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

    private void fillDataJobCategory() {

        final JSONObject jobj = new JSONObject();
        final String category_name = "null";
        try {
            jobj.put("category_name", category_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJobCategory, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONArray("job_category");

                    if (response.length() > 0) {
                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject items = jray.getJSONObject(i);

                            JobCategoryViewModel itemCategory = new JobCategoryViewModel();

                            itemCategory.setJudul(items.getString("category_name"));
                            itemCategory.setFoto(ResourcesCompat.getDrawable(getResources(), R.drawable.android, null));

                            cList.add(itemCategory);
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

    @Override
    public void doClick(int pos) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        startActivity(intent);
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

//    private void fillData() {
//        Resources resources = getResources();
//        String[] arJudul = resources.getStringArray(R.array.title);
//        TypedArray a = resources.obtainTypedArray(R.array.list_job);
//        Drawable[] arFoto = new Drawable[a.length()];
//        for (int i = 0; i < arFoto.length; i++) {
//            arFoto[i] = a.getDrawable(i);
//        }
//        a.recycle();
//
//        for (int i = 0; i < arJudul.length; i++) {
//            mList.add(new HomeViewModel(arJudul[i], arFoto[i]));
//        }
//        mAdapter.notifyDataSetChanged();
//    }
}