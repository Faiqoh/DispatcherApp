package com.example.appdispatcher.ui.home;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
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
import com.example.appdispatcher.PrefManager;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.ModalBottomSheet;
import com.example.appdispatcher.ui.detail.ScrollingActivityDetailTask;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListJobCategory extends Fragment implements DetailJobCategoryAdapter.CListAdapter {

    public static final String DATE_FORMAT_5 = "yyyy-MM-dd";

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB_CATEGORY = "get_id_job_category";
    public static final String GET_ID_JOB = "get_id_job";
    public static final String STATUS_JOB = "status_job";
    ArrayList<HomeViewModel> cList = new ArrayList<>();
    DetailJobCategoryAdapter cAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    NestedScrollView nestedScrollView;
    RelativeLayout rvNotFound, rvJobList;
    private PrefManager prefManager;
    String end_date, start_date;

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
        rvJobList = root.findViewById(R.id.rvJobList);

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
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        if (id == R.id.item_filter_date) {
            final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
            View sheetView = getActivity().getLayoutInflater().inflate(R.layout.modal_filter_date, null);
            mBottomSheetDialog.setContentView(sheetView);
            mBottomSheetDialog.show();
            final TextView date_start = (TextView) sheetView.findViewById(R.id.date_start);
            final TextView date_end = (TextView) sheetView.findViewById(R.id.date_end);
            final Button btn_submit = (Button) sheetView.findViewById(R.id.btn_submit);
            date_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar newCalendar = Calendar.getInstance();

                    DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            date_start.setText(dateFormatter.format(newDate.getTime()));
                            start_date = date_start.getText().toString().trim();
                            date_start.setError(null);
                        }

                    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    dpd.show();
                }
            });

            date_end.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar newCalendar = Calendar.getInstance();

                    DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            date_end.setText(dateFormatter.format(newDate.getTime()));
                            end_date = date_end.getText().toString().trim();
                            date_end.setError(null);
                        }

                    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    dpd.show();
                }
            });

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (date_start.getText().toString().equals("Select start date")) {
                            date_start.setError("Please select start date");
                        } else if (date_end.getText().toString().equals("Select end date")) {
                            date_end.setError("Please select end date");
                        } else {
                            filterdate();
                            mBottomSheetDialog.dismiss();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        return super.onOptionsItemSelected(item);
    }

    private void filterdate() throws ParseException {
        ArrayList<HomeViewModel> filteredList = new ArrayList<>();

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date start_date_format = dateFormatter.parse(start_date);
        Date end_date_format = dateFormatter.parse(end_date);

        for (HomeViewModel item : cList) {
            Date start_date_format2 = dateFormatter.parse(item.start_date);
            Date end_date_format2 = dateFormatter.parse(item.end_date);

            if ((start_date_format.compareTo(start_date_format2) * start_date_format2.compareTo(end_date_format) >= 0) && (start_date_format.compareTo(end_date_format2) * end_date_format2.compareTo(end_date_format) >= 0)){
                filteredList.add(item);
            }

            if (filteredList.size() > 0) {
                rvNotFound.setVisibility(View.GONE);
                rvJobList.setBackgroundColor(getResources().getColor(R.color.colorBackgroundTwo));
            } else {
                rvNotFound.setVisibility(View.VISIBLE);
            }
        }

        cAdapter.filterList(filteredList);
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
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJobByEngineer, null, new Response.Listener<JSONObject>() {
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


    public void doClick(int pos) {
        Intent intent = new Intent(getActivity(), ScrollingActivityDetailTask.class);
        intent.putExtra(ID_JOB, cAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_job");
        intent.putExtra(STATUS_JOB, "status_job");
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
}
