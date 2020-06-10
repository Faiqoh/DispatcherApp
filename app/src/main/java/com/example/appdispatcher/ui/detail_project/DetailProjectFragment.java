package com.example.appdispatcher.ui.detail_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.ProgressTaskAdapter;
import com.example.appdispatcher.MainActivity;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.ProgressDoneViewModel;
import com.example.appdispatcher.ui.home.HomeFragment;
import com.example.appdispatcher.ui.home.HomeViewModel;
import com.example.appdispatcher.ui.home.ListJobCategory;
import com.example.appdispatcher.util.server;

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

public class DetailProjectFragment extends Fragment {

    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    ArrayList<ProgressDoneViewModel> pList = new ArrayList<>();
    ProgressTaskAdapter pAdapter;
    ImageView cat_backend;
    TextView textViewjob, textJobdesc, textRequirement, textBuilding, textloc, textLevel, textDate, textPIc, tvname, tv_idjob;
    Button btn_apply;
    String id_user, id_job;
    ProgressBar progressBar;

    private DetailProjectViewModel mViewModel;

    public static DetailProjectFragment newInstance() {
        return new DetailProjectFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_project, container, false);

        final HomeViewModel lead = (HomeViewModel) getActivity().getIntent().getSerializableExtra(HomeFragment.ID_JOB);

        HomeViewModel lead2 = (HomeViewModel) getActivity().getIntent().getSerializableExtra(ListJobCategory.ID_JOB);

        cat_backend = root.findViewById(R.id.cat_backend);
        textJobdesc = root.findViewById(R.id.job_desc_detail);
        textViewjob = root.findViewById(R.id.text_view_job);
        textRequirement = root.findViewById(R.id.requirement_detail);
        textBuilding = root.findViewById(R.id.building);
        textloc = root.findViewById(R.id.location);
        textLevel = root.findViewById(R.id.level);
        textDate = root.findViewById(R.id.date_job);
        textPIc = root.findViewById(R.id.pic_job);
        btn_apply = root.findViewById(R.id.btn_apply);
        tvname = root.findViewById(R.id.tv_name);
        tv_idjob = root.findViewById(R.id.tv_idjob);
        progressBar = root.findViewById(R.id.progressBar1);

        if (lead != null) {
            String id_job = lead.getId_job();
            fillDetail(id_job);
        } else if (lead2 != null) {
            String id_job = lead2.getId_job();
            fillDetail(id_job);
        }

        fillAccountUser();

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_user = tvname.getText().toString().trim();
                id_job = tv_idjob.getText().toString().trim();
                applyjob();
            }
        });


        return root;

    }

    private void fillAccountUser() {
        SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        Log.i("preferences_setting", mSetting.getString("Token", "missing"));

//        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.getUser + "/?id_user=" + 1, null, new Response.Listener<JSONObject>() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getUser_withToken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jUser = response.getJSONObject("users");

                    Log.i("users", jUser.toString());

                    tvname.setText(jUser.getString("id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

    private void fillDetail(String id_job) {
        Log.i(id_job, "fillDetail: ");
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.getJobOpen + "/?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressBar.setVisibility(View.GONE);

                try {

                    JSONObject job = response.getJSONObject("job");

                    JSONObject category = job.getJSONObject("category");

                    Log.i("job", category.toString());

                    Date date_start = inputFormat.parse(job.getString("date_start"));
                    Date date_end = inputFormat.parse(job.getString("date_end"));

                    tv_idjob.setText(job.getString("id"));
                    textViewjob.setText(job.getString("job_name"));
                    textJobdesc.setText(job.getString("job_description"));
                    textRequirement.setText(job.getString("job_requrment"));
                    textBuilding.setText(job.getJSONObject("customer").getString("customer_name"));
                    textloc.setText(job.getJSONObject("location").getString("location_name"));
                    textLevel.setText(job.getJSONObject("level").getString("level_name"));
                    textDate.setText(dateFormat.format(date_start) + " - " + dateFormat.format(date_end));
                    textPIc.setText(job.getJSONObject("pic").getString("pic_name") + "(" + job.getJSONObject("pic").getString("pic_phone") + ")");


                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(StrReq);

    }

    private void applyjob() {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("id_engineer", id_user);
            jobj.put("id_job", id_job);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.applyjob, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;
                Toast.makeText(getActivity(), "Job Applied :)", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        },
                new Response.ErrorListener() {
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
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailProjectViewModel.class);
        // TODO: Use the ViewModel
    }

}
