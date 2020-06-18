package com.example.appdispatcher.ui.fab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestFabFragment extends Fragment {

    EditText etname, etnominal, etreason;
    TextView tvIdJob;
    Button btn_upload;
    String name, nominal, reason, id_job;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_fab, container, false);

        Bundle extras = getActivity().getIntent().getExtras();
        String id_jobb = extras.getString("id_job");

        etname = view.findViewById(R.id.eTextTask);
        etnominal = view.findViewById(R.id.eTextTask2);
        etreason = view.findViewById(R.id.eTextTask3);
        btn_upload = view.findViewById(R.id.btn_upload);
        tvIdJob = view.findViewById(R.id.tv_id_job);
        fillDetail(id_jobb);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etname.getText().toString().trim();
                nominal = etnominal.getText().toString().trim();
                reason = etreason.getText().toString().trim();
                id_job = tvIdJob.getText().toString().trim();
                submit();
            }
        });

        return view;

    }


    private void fillDetail(String id_job) {
        Log.i("id_jobku", id_job);

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.progreesjob_withToken + "?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.i("response bisa", String.valueOf(response));

                try {
                    JSONObject job = response.getJSONObject("job");

                    tvIdJob.setText(job.getString("id"));

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
    }

    private void submit() {
    }
}