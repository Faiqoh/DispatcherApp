package com.example.appdispatcher.ui.support;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.ChatInitiateAdapter;
import com.example.appdispatcher.Adapter.GetSupportAdapter;
import com.example.appdispatcher.R;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


public class ChatInitiateModeratorFragment extends Fragment implements ChatInitiateAdapter.ChatInitiateModeratorAdapter{

    public static final String ID_SUPPORT = "id_support";
    public static final String GET_ID_JOB = "get_id_job";
    public List<SupportViewModel> sList = new ArrayList<>();
    ChatInitiateAdapter sAdapter;
    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    NestedScrollView nestedScrollView;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_initiate_moderator, container, false);

        nestedScrollView = view.findViewById(R.id.nested_support);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_support);

        RecyclerView rvsupport = view.findViewById(R.id.recyclerViewHistorySupp);
        LinearLayoutManager lmsupport = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvsupport.setLayoutManager(lmsupport);
        sList.clear();
        fillData();
        sAdapter = new ChatInitiateAdapter(this, sList);
        rvsupport.setAdapter(sAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

                sList.clear();
                nestedScrollView.setVisibility(View.GONE);
                shimmerFrameLayout.startShimmerAnimation();
                fillData();

            }
        });

        return view;
    }

    private void fillData() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getsupport_withtoken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                nestedScrollView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONArray("job_support");
                    if (response.length() > 0) {
                        if (sList != null) {
                            sList.clear();
                        } else {
                            sList = new ArrayList<>();
                        }

                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject sup = jray.getJSONObject(i);

                            Date date_add = inputFormat.parse(sup.getString("date_add"));

                            SupportViewModel itemCategory = new SupportViewModel();
                            itemCategory.setId_support(sup.getString("id"));
                            itemCategory.setJudul(sup.getJSONObject("job").getString("job_name"));
                            itemCategory.setStatus_support(sup.getString("status"));
                            itemCategory.setDate(dateFormat.format(date_add));
                            itemCategory.setFoto(sup.getJSONObject("job_category").getString("category_image_url"));
                            sList.add(itemCategory);
                        }
                        sAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Accept", "applicaion/json");
                // Barer di bawah ini akan di simpan local masing-masing device engineer

//                headers.put("Authorization", "Bearer 14a1105cf64a44f47dd6d53f6b3beb79b65c1e929a6ee94a5c7ad30528d02c3e");
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    @Override
    public void doClick(int pos) {
        Intent intent = new Intent(getContext(), ScrollingActivityDetailTask.class);
        intent.putExtra(ID_SUPPORT, sAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_support");
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