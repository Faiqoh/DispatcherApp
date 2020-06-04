package com.example.appdispatcher.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.appdispatcher.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressDoneFragment extends Fragment {

    public List<ProgressDoneViewModel> pList = new ArrayList<>();
    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    ImageView cat_backend;
    TextView textViewjob, textJobdesc, textRequirement, tvidUser, tvidJob;
    EditText etTask;
    Button btn_note, btn_submit;
    ProgressTaskAdapter pAdapter;
    String id_user, id_job, detail_activity;

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
        } else {
            DoneViewModel detail = (DoneViewModel) getActivity().getIntent().getSerializableExtra(DoneFragment.ID_JOB);
            String id_job = detail.getId_job();
            fillDetail(id_job);
            btn_note.setVisibility(View.GONE);
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

        fillAccountUser();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewprogresstask);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        pAdapter = new ProgressTaskAdapter(pList);
        recyclerView.setAdapter(pAdapter);

        return root;
    }

    private void fillAccountUser() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.getUser + "/?id_user=" + 1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jUser = response.getJSONObject("users");

                    Log.i("users", jUser.toString());

                    tvidUser.setText(jUser.getString("id"));

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

    private void fillDetail(String id_job) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.getJobOpen + "/?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject job = response.getJSONObject("job");

                    JSONObject category = job.getJSONObject("category");

                    Log.i("job", category.toString());

                    textViewjob.setText(job.getString("job_name"));
                    textJobdesc.setText(job.getString("job_description"));
                    textRequirement.setText(job.getString("job_requrment"));
                    tvidJob.setText(job.getString("id"));


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
        requestQueue.add(StrReq);

        JsonObjectRequest StrReq2 = new JsonObjectRequest(Request.Method.GET, server.getJobProgress + "/?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
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
        });
        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        requestQueue2.add(StrReq2);
    }

    private void progressjob() {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("id_engineer", id_user);
            jobj.put("id_job", id_job);
            jobj.put("detail_activity", detail_activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.progreejob, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;
                Toast.makeText(getActivity(), "Successfully :)", Toast.LENGTH_LONG).show();
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
}
