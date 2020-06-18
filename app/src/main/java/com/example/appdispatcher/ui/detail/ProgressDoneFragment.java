package com.example.appdispatcher.ui.detail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
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
import com.bumptech.glide.Glide;
import com.example.appdispatcher.Adapter.ProgressTaskAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.clans.fab.FloatingActionButton;

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

//import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressDoneFragment extends Fragment {

    public List<ProgressDoneViewModel> pList = new ArrayList<>();
    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    ImageView cat_backend;
    TextView textViewjob, textJobdesc, textRequirement, tvidUser, tvidJob, textview_mail, textview_share;
    EditText etTask;
    Button btn_note, btn_submit, btn_done;
    ProgressTaskAdapter pAdapter;
    String id_user, id_job, detail_activity;
    ProgressBar progressBar;
    CardView cvdesc, cvspec, cardView1;
    RelativeLayout relativelayoutprogress;
    ShimmerFrameLayout shimmerFrameLayout;
    Boolean isOpen = false;
    private FloatingActionButton fab_main, fab1_mail, fab2_share;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_progress_done, container, false);

        Bundle extras = getActivity().getIntent().getExtras();
        String getJob = extras.getString("get_id_job");

        cat_backend = root.findViewById(R.id.cat_backend);
        textJobdesc = root.findViewById(R.id.job_desc_detail);
        textViewjob = root.findViewById(R.id.text_view_job);
        textRequirement = root.findViewById(R.id.requirement_detail);
        etTask = root.findViewById(R.id.eTextTask);
        btn_submit = root.findViewById(R.id.btnSubmit);
        btn_note = root.findViewById(R.id.btnAddNote);
        tvidJob = root.findViewById(R.id.tv_idjob);
        tvidUser = root.findViewById(R.id.tv_id_user);
        btn_done = root.findViewById(R.id.btnDone);
        progressBar = root.findViewById(R.id.progressBarDone);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_container);
        cvdesc = root.findViewById(R.id.cvdesc);
        cvspec = root.findViewById(R.id.spec);
        relativelayoutprogress = root.findViewById(R.id.relativelayoutprogress);
        cardView1 = root.findViewById(R.id.cardview1);

        FloatingActionButton Request = root.findViewById(R.id.request);
        Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Request Barang", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton Done = root.findViewById(R.id.done);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton Progress = root.findViewById(R.id.progress);
        Progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Progress", Toast.LENGTH_SHORT).show();
            }
        });


        if (getJob.equals("id_job_progress")) {
            OnProgressViewModel detail = (OnProgressViewModel) getActivity().getIntent().getSerializableExtra(OnProgressFragment.ID_JOB);
            String id_job = detail.getId_job();
            fillDetail(id_job);
            btn_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etTask.setVisibility(View.VISIBLE);
                    btn_submit.setVisibility(View.VISIBLE);
                }
            });
            btn_done.setVisibility(View.GONE);
        } else {
            DoneViewModel detail = (DoneViewModel) getActivity().getIntent().getSerializableExtra(DoneFragment.ID_JOB);
            String id_job = detail.getId_job();
            fillDetail(id_job);
            btn_note.setVisibility(View.GONE);
            btn_done.setVisibility(View.GONE);
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_user = tvidUser.getText().toString().trim();
                id_job = tvidJob.getText().toString().trim();
                detail_activity = etTask.getText().toString().trim();
                progressjob();
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_user = tvidUser.getText().toString().trim();
                id_job = tvidJob.getText().toString().trim();
                donejob();
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewprogresstask);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        pAdapter = new ProgressTaskAdapter(pList);
        recyclerView.setAdapter(pAdapter);

        return root;
    }

    private void fillDetail(String id_job) {
        Log.i("id_jobku", id_job);
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        progressBar.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.progreesjob_withToken + "?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response bisa", String.valueOf(response));
                progressBar.setVisibility(View.GONE);

                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);

                cvdesc.setVisibility(View.VISIBLE);
                cvspec.setVisibility(View.VISIBLE);
                relativelayoutprogress.setVisibility(View.VISIBLE);
                cardView1.setVisibility(View.VISIBLE);

                try {
                    JSONObject job = response.getJSONObject("job");

                    JSONObject category = job.getJSONObject("category");

                    Log.i("saaaaaaaaaaaa", category.toString());

                    textViewjob.setText(job.getString("job_name"));
                    textJobdesc.setText(job.getString("job_description"));
                    textRequirement.setText(job.getString("job_requrment"));
                    tvidJob.setText(job.getString("id"));
                    Glide.with(getActivity()).load(category.getString("category_image_url")).into(cat_backend);

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
        requestQueue.add(StrReq);

        JsonObjectRequest StrReq2 = new JsonObjectRequest(Request.Method.GET, server.progreesjob_withToken + "?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONObject("job").getJSONArray("progress");
                    if (response.length() > 0) {
                        int no = 1;
                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject task = jray.getJSONObject(i);
                            if (task.getInt("id_activity") == 5) {

                                ProgressDoneViewModel progress = new ProgressDoneViewModel();

                                Date date_submit = inputFormat.parse(task.getString("date_time"));
                                progress.setDetail_activity(task.getString("detail_activity"));
                                progress.setDay("Day " + no++);
                                progress.setDate(dateFormat.format(date_submit));
                                btn_done.setVisibility(View.VISIBLE);
                                btn_note.setVisibility(View.VISIBLE);

                                pList.add(progress);

                            } else if (task.getInt("id_activity") == 6) {
                                ProgressDoneViewModel progress = new ProgressDoneViewModel();

                                Date date_submit = inputFormat.parse(task.getString("date_time"));
                                progress.setDetail_activity(task.getString("detail_activity"));
                                progress.setDay("Day " + no++);
                                progress.setDate(dateFormat.format(date_submit));
                                btn_done.setVisibility(View.GONE);
                                btn_note.setVisibility(View.GONE);

                                pList.add(progress);
                            }


                            pAdapter.notifyDataSetChanged();
                        }
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
        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        requestQueue2.add(StrReq2);
    }

    private void progressjob() {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("id_job", id_job);
            jobj.put("detail_activity", detail_activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.postJobUpdate_withToken, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;
                Toast.makeText(getActivity(), "Successfully :)", Toast.LENGTH_LONG).show();

                /*Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);*/
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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    private void donejob() {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("id_job", id_job);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.jobdone_withToken, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;
                Toast.makeText(getActivity(), "Successfully :)", Toast.LENGTH_LONG).show();
                /*Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);*/
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
