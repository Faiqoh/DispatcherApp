package com.example.appdispatcher;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.appdispatcher.util.server.postSaveToken;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    public SharedPreferences mSetting;
    public String msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("asdfasd", "sdfasdfads");
        super.onCreate(savedInstanceState);
        mSetting = this.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String Token_account = mSetting.getString("Token", "missing");
        if (Token_account.equals("missing")) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else {
            Log.i("Token", Token_account);
        }
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_detail, R.id.navigation_payment, R.id.navigation_message, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//        String url = "http://117.102.120.152:8080/job/getJobProgress?id_job=1";
//        Log.i("JsonLog","This is result of Json Request");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("NotifApps", "NotifyApps", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        getCurrentFirebaseToken();
        mQueue = Volley.newRequestQueue(this);


    }

    public void onFirebaseTokenSuccess(String token) {
        Log.i("JsonLog", "This is result of Json Request");
        String url = postSaveToken;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("JsonLog", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("JsonLog", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences mSetting = getApplicationContext().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Accept", "applicaion/json");
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        mQueue.add(jsonObjectRequest);
    }

    private void getCurrentFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("currentToken", token);

                        // Log and toast
                        msg = getString(R.string.msg_token_fmt, token);
                        Log.d("TAG", msg);
                        onFirebaseTokenSuccess(token);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}
