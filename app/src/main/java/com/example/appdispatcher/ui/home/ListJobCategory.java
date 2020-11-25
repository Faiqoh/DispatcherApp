package com.example.appdispatcher.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.DetailJobCategoryAdapter;
import com.example.appdispatcher.FabActivity;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.ModalBottomSheet;
import com.example.appdispatcher.ui.detail.ScrollingActivityDetailTask;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListJobCategory extends Fragment implements DetailJobCategoryAdapter.CListAdapter, ModalBottomSheet.ActionListener {

    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB_CATEGORY = "get_id_job_category";
    public static final String GET_ID_JOB = "get_id_job";
    ArrayList<HomeViewModel> cList = new ArrayList<>();
    DetailJobCategoryAdapter cAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    NestedScrollView nestedScrollView;
    RelativeLayout rvNotFound;

    public ListJobCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_job_category, container, false);

        Bundle extras = getActivity().getIntent().getExtras();

        String getJob = extras.getString("get_id_job");

        shimmerFrameLayout = root.findViewById(R.id.shimer_view_detail_job_list);
        nestedScrollView = root.findViewById(R.id.nested_detail_job_list);
        rvNotFound = root.findViewById(R.id.RvNotFound);

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
        } else if (getJob.equals("id_all_category")) {
            Integer id_category = extras.getInt("id_job_1");
            fillData2(id_category);
        } else {
            fillData();
        }

        return root;
    }


    @SuppressLint("ResourceType")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);
        MenuItem item = menu.findItem(R.id.item_search);
        MenuItem item1 = menu.findItem(R.id.item_filter_date);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_filter_date) {
            ModalBottomSheet modalBottomSheet = new ModalBottomSheet();
            modalBottomSheet.setActionListener((ModalBottomSheet.ActionListener) getActivity());
            modalBottomSheet.show(getFragmentManager(), "modal_filter_date");
        }
        return super.onOptionsItemSelected(item);
    }

    private void filter(String s) {
        ArrayList<HomeViewModel> filteredList = new ArrayList<>();
        for (HomeViewModel item : cList) {
            if (item.getJob_name().toLowerCase().contains(s.toLowerCase()) || item.getCustomer().toLowerCase().contains(s.toLowerCase()) ||
                item.getCategory_name().toLowerCase().contains(s.toLowerCase()) || item.getLocation().toLowerCase().contains(s.toLowerCase())) {
                filteredList.add(item);
            }
        }
        cAdapter.filterList(filteredList);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item3 = menu.findItem(R.id.item_notif);
        MenuItem item2 = menu.findItem(R.id.item_filter);
        item2.setVisible(false);
        item3.setVisible(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void fillData() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJobByEngineer, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jObj = response;
                nestedScrollView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                try {
                    JSONArray jray = jObj.getJSONArray("job");

                    if (response.length() > 0) {
                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            Date date_start = inputFormat.parse(cat.getString("date_start"));
                            Date date_end = inputFormat.parse(cat.getString("date_end"));

                            HomeViewModel itemCategory = new HomeViewModel();
                            itemCategory.setId_job(cat.getString("id"));
                            itemCategory.setCategory_name(cat.getJSONObject("category").getString("category_name"));
                            itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                            itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                            itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));
                            itemCategory.setJob_name(cat.getString("job_name"));
                            itemCategory.setEnd_date(dateFormat.format(date_end));
                            itemCategory.setStart_date(dateFormat.format(date_start));

                            cList.add(itemCategory);
                        }
                        cAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                NetworkResponse response = error.networkResponse;
                String errorMsg = "";
                if (response != null && response.data != null) {
                    String errorString = new String(response.data);
                    Log.i("log error", errorString);
                }
                Toast.makeText(getActivity(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Accept", "applicaion/json");
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    private void fillData2(final Integer id_category) {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJobListSumm, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                try {
                    JSONArray jray = jObj.getJSONArray("job");

                    if (response.length() > 0) {
                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            HomeViewModel itemCategory = new HomeViewModel();
                            if (cat.getInt("id_category") == id_category) {
                                if (cat.getString("job_status").equals("Open")) {
                                    itemCategory.setId_job(cat.getString("id"));
                                    itemCategory.setCategory_name(cat.getJSONObject("category").getString("category_name"));
                                    itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                                    itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                                    itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));
                                    itemCategory.setJob_name(cat.getString("job_name"));

                                    cList.add(itemCategory);

                                    Log.i("cek total list", String.valueOf(cList.size()));
                                    if (cList.size() > 0) {
                                        rvNotFound.setVisibility(View.GONE);
                                        nestedScrollView.setVisibility(View.VISIBLE);
                                    } else {
                                        nestedScrollView.setVisibility(View.GONE);
                                        rvNotFound.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            cAdapter.notifyDataSetChanged();
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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }


    public void doClick(int pos) {
        Intent intent = new Intent(getActivity(), ScrollingActivityDetailTask.class);
        intent.putExtra(ID_JOB, cAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_job");
        startActivity(intent);
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

    @Override
    public void onButtonClick(int id) {

    }
}
