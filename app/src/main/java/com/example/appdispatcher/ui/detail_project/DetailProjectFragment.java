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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
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
    ImageView cat_backend, logo_success;
    TextView textViewjob, textJobdesc, textRequirement, textBuilding, textloc, textLevel, textDate, textPIc, tvname, tv_idjob, tv_price;
    Button btn_apply, btn_accept, btn_cancel;
    Animation fromsmall, tonothing, fromnothing, forlogo;
    LinearLayout mykonten, overbox, modal_success;
    String id_user, id_job;
    ProgressBar progressBar;
    ShimmerFrameLayout shimmerFrameLayout;
    CardView cardViewApplied, cardViewheader;

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

        btn_accept = root.findViewById(R.id.btn_accept);
        btn_cancel = root.findViewById(R.id.btn_cancel);
        mykonten = root.findViewById(R.id.mykonten);
        modal_success = root.findViewById(R.id.modal_success);
        overbox = root.findViewById(R.id.overbox);
        logo_success = root.findViewById(R.id.logo_success);

        fromsmall = AnimationUtils.loadAnimation(getActivity(), R.anim.fromsmall);
        tonothing = AnimationUtils.loadAnimation(getActivity(), R.anim.tonothing);
        fromnothing = AnimationUtils.loadAnimation(getActivity(), R.anim.fromnothing);
        forlogo = AnimationUtils.loadAnimation(getActivity(), R.anim.forlogo);

        mykonten.setAlpha(0);
        overbox.setAlpha(0);
        modal_success.setAlpha(0);
        logo_success.setVisibility(View.GONE);


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
        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_container);
        cardViewApplied = root.findViewById(R.id.cardviewdetailapply);
        cardViewheader = root.findViewById(R.id.cardViewHeader);
        tv_price = root.findViewById(R.id.tv_price);

        if (lead != null) {
            String id_job = lead.getId_job();
            fillDetail(id_job);
        } else if (lead2 != null) {
            String id_job = lead2.getId_job();
            fillDetail(id_job);
        }

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mykonten.setAlpha(1);
                mykonten.startAnimation(fromsmall);

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mykonten.setAlpha(0);
                mykonten.startAnimation(tonothing);
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mykonten.setAlpha(0);
                mykonten.startAnimation(tonothing);

                modal_success.setAlpha(1);
                modal_success.startAnimation(fromsmall);

                logo_success.setVisibility(View.VISIBLE);
                logo_success.startAnimation(forlogo);

                id_user = tvname.getText().toString().trim();
                id_job = tv_idjob.getText().toString().trim();
                applyjob();
            }
        });

        return root;

    }

    private void fillDetail(String id_job) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        final Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.getJobOpen + "?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressBar.setVisibility(View.GONE);

                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                cardViewApplied.setVisibility(View.VISIBLE);
                cardViewheader.setVisibility(View.VISIBLE);

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
                    tv_price.setText(formatRupiah.format((Double.parseDouble(job.getString("job_price")))));

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
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        cardViewApplied.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.JobApply_withToken + "?id_job=" + id_job, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailProjectViewModel.class);
        // TODO: Use the ViewModel
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
