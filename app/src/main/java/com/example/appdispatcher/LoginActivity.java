package com.example.appdispatcher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.util.server;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    Button b1;
    EditText ed1, ed2;
    SharedPreferences mSetting;
    String email, password;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        b1 = findViewById(R.id.buttonSubmitLogin);
        ed1 = findViewById(R.id.editTextTextEmailAddress);
        ed2 = findViewById(R.id.editTextTextPassword);

        auth = FirebaseAuth.getInstance();

        mSetting = this.getSharedPreferences("Setting", Context.MODE_PRIVATE);

        String Token_account = mSetting.getString("Token", "missing");
        if (!Token_account.equals("missing")) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = ed1.getText().toString().trim();
                password = ed2.getText().toString().trim();
                if (!email.isEmpty() || !password.isEmpty()) {
                    login_function(email, password);
//                    login(email, password);
                } else {
                    ed1.setError("Please Insert Email!");
                    ed2.setError("Please insert Password!");
                }


            }
        });
    }

//    private void login(final String email, final String password) {
//
//        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
////                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                    startActivity(intent);
//                    Toast.makeText(LoginActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                FirebaseUser firebaseUser = auth.getCurrentUser();
//                                assert firebaseUser != null;
//                                String userid = firebaseUser.getUid();
//
//                                reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
//
//                                HashMap<String, String> hashMap = new HashMap<>();
//                                hashMap.put("id", userid);
//                                hashMap.put("email", email);
//
//                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
////                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//                                    }
//                                });
//                            } else {
//                                Toast.makeText(LoginActivity.this, "You can't login with this email or password", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//            }
//        });
//    }

    private void login_function(final String email, final String password) {
        final ProgressDialog pd = new ProgressDialog(this, R.style.MyTheme);
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();

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

                    SharedPreferences.Editor editor = mSetting.edit();
                    editor.putString("Token", "Bearer " + jResponse.getString("token"));
                    editor.putString("ID", "id user" + jResponse.getString("id_user"));
                    editor.apply();
                    Log.i("preferences_setting", String.valueOf(mSetting));
                    Log.i("preferences_setting", mSetting.getString("Token", "missing"));
                    Log.i("preferences_setting", mSetting.getString("ID", "missing"));
                    pd.dismiss();
                    startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("users", String.valueOf(error));
                pd.dismiss();
                Toast.makeText(LoginActivity.this, "Email atau password tidak sesuai", Toast.LENGTH_LONG).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);

    }
}