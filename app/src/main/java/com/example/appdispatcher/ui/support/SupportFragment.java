package com.example.appdispatcher.ui.support;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.GetSupportAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.ScrollingActivityDetailTask;
import com.example.appdispatcher.util.server;

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

public class SupportFragment extends Fragment implements GetSupportAdapter.SupportAdapter {

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    public List<SupportViewModel> sList = new ArrayList<>();
    GetSupportAdapter sAdapter;
    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    NestedScrollView nestedScrollView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_support, container, false);

        nestedScrollView = view.findViewById(R.id.nested_support);

        RecyclerView rvsupport = view.findViewById(R.id.recyclerViewHistorySupp);
        LinearLayoutManager lmsupport = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvsupport.setLayoutManager(lmsupport);
        sList.clear();
        fillData();
        sAdapter = new GetSupportAdapter(this, sList);
        rvsupport.setAdapter(sAdapter);

        return view;
    }

    private void fillData() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getsupport_withtoken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                nestedScrollView.setVisibility(View.VISIBLE);
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
        intent.putExtra(ID_JOB, sAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_support");
        startActivity(intent);
    }
}
