package com.example.appdispatcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.util.server;

import org.json.JSONException;
import org.json.JSONObject;

public class LayoutActivity extends AppCompatActivity {

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    Button b1;
    EditText ed1, ed2;
    SharedPreferences mSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        b1 = (Button) findViewById(R.id.buttonSubmitLogin);
        ed1 = (EditText) findViewById(R.id.editTextTextEmailAddress);
        ed2 = (EditText) findViewById(R.id.editTextTextPassword);

        mSetting = this.getSharedPreferences("Setting", Context.MODE_PRIVATE);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_fucntion();
            }
        });


    }

    private void login_fucntion() {
        Log.i("email", ed1.getText().toString());

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", ed1.getText().toString());
            jsonBody.put("password", ed2.getText().toString());
//            jsonBody.put("email", "agastya@sinergy.co.id");
//            jsonBody.put("password", "asdasdasd");
            Log.i("json", "success get");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("json", String.valueOf(jsonBody));
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.login_url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jResponse = response.getJSONObject("response");
                    Log.i("jResponse", String.valueOf(jResponse));
                    SharedPreferences.Editor editor = mSetting.edit();
                    editor.putString("Token", "Bearer " + jResponse.getString("token"));
                    editor.apply();
                    Log.i("preferences_setting", String.valueOf(mSetting));
                    Log.i("preferences_setting", mSetting.getString("Token", "missing"));
//                    int LAUNCH_SECOND_ACTIVITY = 1;
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("users", String.valueOf(error));

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);

//        Intent intent = new Intent(this, ScrollingActivityDetailTask.class);
//        startActivityForResult(intent, 1);

    }
}