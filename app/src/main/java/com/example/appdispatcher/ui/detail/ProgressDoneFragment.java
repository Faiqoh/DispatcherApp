package com.example.appdispatcher.ui.detail;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.appdispatcher.Adapter.ProgressTaskAdapter;
import com.example.appdispatcher.FabActivity;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.appbar.AppBarLayout;

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


/**
 * A simple {@link Fragment} subclass.
 */

public class ProgressDoneFragment extends Fragment {

    private static ProgressDoneFragment instance = null;

    public List<ProgressDoneViewModel> pList = new ArrayList<>();
    //    ProgressTaskAdapter pAdapter;
    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    ImageView cat_backend;
    TextView textViewjob, textJobdesc, textRequirement, tvidUser, tvidJob, textview_mail, textview_share;
    EditText etTask;
    Button btn_note, btn_submit, btn_done;
    ProgressTaskAdapter pAdapter;
    String id_user, id_jobb, detail_activity;
    ProgressBar progressBar, progressBarSubmit;
    CardView cvdesc, cvspec, cardView1;
    RelativeLayout relativelayoutprogress;
    ShimmerFrameLayout shimmerFrameLayout;
    Boolean isOpen = false;
    FloatingActionsMenu floatingActionsMenu;
    NestedScrollView NesteddetailTask;
    AppBarLayout appBarLayout;
//    boolean processClick = true;

    public static ProgressDoneFragment getInstance() {
        return instance;
    }

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
//        btn_note = root.findViewById(R.id.btnAddNote);
        tvidJob = root.findViewById(R.id.tv_idjob);
        tvidUser = root.findViewById(R.id.tv_id_user);
//        btn_done = root.findViewById(R.id.btnDone);
        progressBar = root.findViewById(R.id.progressBarDone);
        progressBarSubmit = root.findViewById(R.id.progressBarSubmit);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_container);
        cvdesc = root.findViewById(R.id.cvdesc);
        cvspec = root.findViewById(R.id.spec);
        relativelayoutprogress = root.findViewById(R.id.relativelayoutprogress);
        cardView1 = root.findViewById(R.id.cardview1);
        floatingActionsMenu = getActivity().findViewById(R.id.fab_menu);
        NesteddetailTask = getActivity().findViewById(R.id.Nested_detail_task);
        appBarLayout = getActivity().findViewById(R.id.app_bar);

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerViewprogresstask);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        pAdapter = new ProgressTaskAdapter(pList);
        recyclerView.setAdapter(pAdapter);

        FloatingActionButton Request = getActivity().findViewById(R.id.request);
        Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FabActivity.class);
                intent.putExtra(ID_JOB, tvidJob.getText().toString());
                intent.putExtra(GET_ID_JOB, "id_job_request");
                startActivity(intent);
                floatingActionsMenu.collapse();
            }
        });

        FloatingActionButton Progress = getActivity().findViewById(R.id.progress);
        Progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
//                etTask.setVisibility(View.VISIBLE);
//                btn_submit.setVisibility(View.VISIBLE);
//                focusOnView();
                //                appBarLayout.setExpanded(false);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.activity_request, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                floatingActionsMenu.collapse();
                btn_submit = mView.findViewById(R.id.btnSubmit);
                etTask = mView.findViewById(R.id.eTextTask);


                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_user = tvidUser.getText().toString().trim();
                        id_jobb = tvidJob.getText().toString().trim();
                        detail_activity = etTask.getText().toString().trim();
                        dialog.dismiss();
                        progressjob();
                    }

                });

            }

            public void focusOnView() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("cek nested", String.valueOf(NesteddetailTask));

                        Log.i("cek recycle", String.valueOf(recyclerView));
                        NesteddetailTask.scrollTo(0, 1);
                        ObjectAnimator.ofInt(NesteddetailTask, "scrollY", etTask.getBottom(), btn_submit.getBottom()).setDuration(700).start();

                    }
                });
            }
        });

        if (getJob.equals("id_job_progress")) {
            OnProgressViewModel detail = (OnProgressViewModel) getActivity().getIntent().getSerializableExtra(OnProgressFragment.ID_JOB);
            String id_job = detail.getId_job();
            fillDetail(id_job);
        } else {
            DoneViewModel detail = (DoneViewModel) getActivity().getIntent().getSerializableExtra(DoneFragment.ID_JOB);
            String id_job = detail.getId_job();
            fillDetail(id_job);
        }

        FloatingActionButton Done = getActivity().findViewById(R.id.done);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), FabActivity.class);
                intent.putExtra(ID_JOB, tvidJob.getText().toString());
                intent.putExtra(GET_ID_JOB, "id_job_done");
                startActivity(intent);
                floatingActionsMenu.collapse();
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    public void fillDetail(String id_job) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        progressBar.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.progreesjob_withToken + "?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);

                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);

                cvdesc.setVisibility(View.VISIBLE);
                cvspec.setVisibility(View.VISIBLE);
                relativelayoutprogress.setVisibility(View.VISIBLE);
                cardView1.setVisibility(View.VISIBLE);
//                floatingActionsMenu.setVisibility(View.VISIBLE);

                try {
                    JSONObject job = response.getJSONObject("job");

                    JSONObject category = job.getJSONObject("category");

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
                                floatingActionsMenu.collapse();

                                pList.add(progress);

                            } else if (task.getInt("id_activity") == 6) {
                                ProgressDoneViewModel progress = new ProgressDoneViewModel();

                                Date date_submit = inputFormat.parse(task.getString("date_time"));
                                progress.setDetail_activity(task.getString("detail_activity"));
                                progress.setDay("Day " + no++);
                                progress.setDate(dateFormat.format(date_submit));
                                floatingActionsMenu.setVisibility(View.GONE);

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
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        requestQueue2.add(StrReq2);
    }

    private void progressjob() {
        progressBarSubmit.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBarSubmit.setVisibility(View.VISIBLE);
        floatingActionsMenu.setVisibility(View.GONE);
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("id_job", id_jobb);
            jobj.put("detail_activity", detail_activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.postJobUpdate_withToken, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBarSubmit.setVisibility(View.GONE);
                JSONObject jObj = response;
                Toast.makeText(getActivity(), "Successfully :)", Toast.LENGTH_LONG).show();
//                startActivity(intent);
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

    private void donejob() {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("id_job", id_jobb);
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

    /*@Override
    public void doClick(int pos) {
        Intent intent = new Intent(getContext(), FabActivity.class);
        intent.putExtra(ID_JOB, pAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_job_progress");
        startActivity(intent);
    }*/
}
