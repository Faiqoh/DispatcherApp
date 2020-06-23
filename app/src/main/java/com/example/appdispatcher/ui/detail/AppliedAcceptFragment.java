package com.example.appdispatcher.ui.detail;

import android.content.Context;
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
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class AppliedAcceptFragment extends Fragment {

    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    ImageView cat_backend;
    TextView textViewjob, textJobdesc, textRequirement, textBuilding, textloc, textLevel, textDate, textPIc, tvidUser, tvidJob;
    Button btn_start;
    String id_user, id_job;
    ProgressBar progressBar;
    ShimmerFrameLayout shimmerFrameLayout;
    CardView cardViewApplied, cardViewApplied2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_applied_accept, container, false);

        Bundle extras = getActivity().getIntent().getExtras();

        String getJob = extras.getString("get_id_job");

        cat_backend = root.findViewById(R.id.cat_backend);
        textJobdesc = root.findViewById(R.id.job_desc_detail);
        textViewjob = root.findViewById(R.id.text_view_job);
        textRequirement = root.findViewById(R.id.requirement_detail);
        textBuilding = root.findViewById(R.id.building);
        textloc = root.findViewById(R.id.location);
        textLevel = root.findViewById(R.id.level);
        textDate = root.findViewById(R.id.date_job);
        textPIc = root.findViewById(R.id.pic_job);
        btn_start = root.findViewById(R.id.btn_start);
        tvidUser = root.findViewById(R.id.tv_id_user);
        tvidJob = root.findViewById(R.id.tv_idjob);
        progressBar = root.findViewById(R.id.progressBarApplied);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_container);
        cardViewApplied = root.findViewById(R.id.cardviewApplied);
        cardViewApplied2 = root.findViewById(R.id.cardviewApplied2);


        if (getJob.equals("id_list")) {
            AcceptedViewModel detail = (AcceptedViewModel) getActivity().getIntent().getSerializableExtra(AcceptedFragment.ID_JOB);
            String id_job = detail.getId_job();
            fillDetail(id_job);
        } else {
            AppliedViewModel detail2 = (AppliedViewModel) getActivity().getIntent().getSerializableExtra(AppliedFragment.ID_JOB);
            String id_job = detail2.getId_job();
            btn_start.setVisibility(View.GONE);
            fillDetail(id_job);
        }

        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_user = tvidUser.getText().toString().trim();
                id_job = tvidJob.getText().toString().trim();
                startjob();
            }
        });

        return root;
    }

    private void fillDetail(String id_job) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        progressBar.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.getJobOpen + "/?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                cardViewApplied.setVisibility(View.VISIBLE);
                cardViewApplied2.setVisibility(View.VISIBLE);

                try {
                    JSONObject job = response.getJSONObject("job");

                    JSONObject category = job.getJSONObject("category");

                    Log.i("job", category.toString());

                    Date date_start = inputFormat.parse(job.getString("date_start"));
                    Date date_end = inputFormat.parse(job.getString("date_end"));

                    textViewjob.setText(job.getString("job_name"));
                    textJobdesc.setText(job.getString("job_description"));
                    textRequirement.setText(job.getString("job_requrment"));
                    textBuilding.setText(job.getJSONObject("customer").getString("customer_name"));
                    textloc.setText(job.getJSONObject("location").getString("location_name"));
                    textLevel.setText(job.getJSONObject("level").getString("level_name"));
                    textDate.setText(dateFormat.format(date_start) + " - " + dateFormat.format(date_end));
                    textPIc.setText(job.getJSONObject("pic").getString("pic_name") + "(" + job.getJSONObject("pic").getString("pic_phone") + ")");
                    tvidJob.setText(job.getString("id"));
                    Glide.with(getActivity()).load(category.getString("category_image_url")).into(cat_backend);


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

    private void startjob() {
        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.JobStart_WithToken + "?id_job=" + id_job, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;
                Toast.makeText(getActivity(), "Job Started :)", Toast.LENGTH_LONG).show();
                getActivity().finish();

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
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
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
