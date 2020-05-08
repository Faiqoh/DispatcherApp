package com.example.appdispatcher.ui.detail_project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.ProgressTaskAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.DetailProgressViewModel;
import com.example.appdispatcher.ui.detail.DetailViewModel;
import com.example.appdispatcher.util.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DetailProjectFragment extends Fragment {

    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    ArrayList<DetailProgressViewModel> pList = new ArrayList<>();
    ProgressTaskAdapter pAdapter;
    ImageView cat_backend;
    TextView textViewjob, textJobdesc, textRequirement, textBuilding, textloc, textLevel, textDate, textPIc;
    private DetailViewModel dashboardViewModel;

    private DetailProjectViewModel mViewModel;

    public static DetailProjectFragment newInstance() {
        return new DetailProjectFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_project, container, false);

        fillDetail();
        cat_backend = root.findViewById(R.id.cat_backend);
        textJobdesc = root.findViewById(R.id.job_desc_detail);
        textViewjob = root.findViewById(R.id.text_view_job);
        textRequirement = root.findViewById(R.id.requirement_detail);
        textBuilding = root.findViewById(R.id.building);
        textloc = root.findViewById(R.id.location);
        textLevel = root.findViewById(R.id.level);
        textDate = root.findViewById(R.id.date_job);
        textPIc = root.findViewById(R.id.pic_job);

        return root;

    }

    private void fillDetail() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.getJobOpen, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject job = response.getJSONObject("job");

                    JSONObject category = job.getJSONObject("category");

                    Log.i("job", category.toString());

                    Date date_start = inputFormat.parse(job.getString("date_start"));
                    Date date_end = inputFormat.parse(job.getString("date_end"));

                    textViewjob.setText(category.getString("category_name"));
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailProjectViewModel.class);
        // TODO: Use the ViewModel
    }

}
