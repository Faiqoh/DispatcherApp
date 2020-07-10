package com.example.appdispatcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    Button b1;
    EditText ed1, ed2;
    SharedPreferences mSetting;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b1 = findViewById(R.id.buttonSubmitLogin);
        ed1 = findViewById(R.id.editTextTextEmailAddress);
        ed2 = findViewById(R.id.editTextTextPassword);

        mSetting = this.getSharedPreferences("Setting", Context.MODE_PRIVATE);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = ed1.getText().toString().trim();
                password = ed2.getText().toString().trim();
                if (!email.isEmpty() || !password.isEmpty()) {
                    login_fucntion(email, password);
                } else {
                    ed1.setError("Please Insert Email!");
                    ed2.setError("Please insert Password!");
                }
            }
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private void login_fucntion(final String email, final String password) {
        Log.i("email", ed1.getText().toString());
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
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
                    String success = jResponse.getString("success");
                    Log.i("jResponse", String.valueOf(jResponse));

                    if (success.equals("200")) {

                        SharedPreferences.Editor editor = mSetting.edit();
                        editor.putString("Token", "Bearer " + jResponse.getString("token"));
                        editor.apply();
                        Log.i("preferences_setting", String.valueOf(mSetting));
                        Log.i("preferences_setting", mSetting.getString("Token", "missing"));
                        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("users", String.valueOf(error));
                Toast.makeText(LoginActivity.this, "Email atau password tidak sesuai", Toast.LENGTH_LONG).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);

    }
}