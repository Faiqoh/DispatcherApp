package com.example.appdispatcher.ui.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.ProgressTaskAdapter;
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
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailProgressTaskFragment extends Fragment {

    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    ProgressTaskAdapter pAdapter;
    //    ArrayList<DetailProgressViewModel> pList = new ArrayList<>();
    public List<DetailProgressViewModel> pList = new ArrayList<>();
    TextView textJobdesc, textViewjob;

    public DetailProgressTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_progress_task, container, false);

        DoneViewModel detail = (DoneViewModel) getActivity().getIntent().getSerializableExtra(DoneFragment.ID_JOB);

        textJobdesc = view.findViewById(R.id.job_desc_detail);
        textViewjob = view.findViewById(R.id.text_view_job);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewprogresstask);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        pAdapter = new ProgressTaskAdapter(pList);
        recyclerView.setAdapter(pAdapter);
        if (detail != null) {
            String id_job = detail.getId_job();
            fillData(id_job);
        }

        return view;
    }

    private void fillData(String id_job) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.getJobOpen + "/?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject job = response.getJSONObject("job");

                    JSONObject category = job.getJSONObject("category");

                    Log.i("job", category.toString());

                    textViewjob.setText(category.getString("category_name"));
                    textJobdesc.setText(job.getString("job_description"));


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

        JsonObjectRequest strReq2 = new JsonObjectRequest(Request.Method.GET, server.getJobProgress, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job progress", response.toString());
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONObject("job").getJSONArray("progress");
                    if (response.length() > 0) {
                        int no = 1;
                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject task = jray.getJSONObject(i);
                            if (task.getInt("id_activity") == 5) {

                                DetailProgressViewModel progress = new DetailProgressViewModel();

                                Date date_submit = inputFormat.parse(task.getString("date_time"));

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
        requestQueue2.add(strReq2);
    }
}
