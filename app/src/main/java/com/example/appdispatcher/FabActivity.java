package com.example.appdispatcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.util.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FabActivity extends AppCompatActivity {

    EditText etsum, etroot, etcounter;
    TextView tvIdJob;
    Button btn_upload;
    String summary, root, counter, id_job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);

        Bundle extras = getIntent().getExtras();
        String id_jobb = extras.getString("id_job");

        etsum = findViewById(R.id.eTextTask);
        etroot = findViewById(R.id.eTextTask2);
        etcounter = findViewById(R.id.eTextTask3);
        btn_upload = findViewById(R.id.btn_upload);
        tvIdJob = findViewById(R.id.tv_id_job);
        fillDetail(id_jobb);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                summary = etsum.getText().toString().trim();
                root = etroot.getText().toString().trim();
                counter = etcounter.getText().toString().trim();
                id_job = tvIdJob.getText().toString().trim();
                submit();
            }
        });

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
                SharedPreferences mSetting = getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(StrReq);
    }

    private void submit() {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("id_job", id_job);
            jobj.put("job_summary", summary);
            jobj.put("job_rootcause", root);
            jobj.put("job_countermeasure", counter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.jobdone_withToken, jobj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                Toast.makeText(getApplicationContext(), "Successfully :)", Toast.LENGTH_LONG).show();

                /*Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);*/
//                getActivity().finish();

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
                        Toast.makeText(getApplicationContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                SharedPreferences mSetting = getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }

}