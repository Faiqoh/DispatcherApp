package com.example.appdispatcher.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appdispatcher.FabActivity;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AccountFragment extends Fragment {

    BottomNavigationView navigation;
    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    TextView tvname, tvemail, tvno, tvjobs, tvskill, tvfee, tvdate, tvaddress;
    /*public List<AppliedViewModel> aList = new ArrayList<>();
    DetailJobAplliedEngineerAdapter aAdapter;*/
    public static final String GET_ID_JOB = "get_id_job";
    ImageView ivuser;
    Button btn_logout;
    ShimmerFrameLayout shimmerFrameLayout;
    NestedScrollView nestedScrollView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        navigation = getActivity().findViewById(R.id.nav_view);

        tvname = root.findViewById(R.id.text_name);
        tvemail = root.findViewById(R.id.text_email);
        tvno = root.findViewById(R.id.text_number);
        tvjobs = root.findViewById(R.id.textViewtotal);
        tvskill = root.findViewById(R.id.textViewskill2);
        tvfee = root.findViewById(R.id.textViewfee2);
        tvdate = root.findViewById(R.id.textViewdate2);
        tvaddress = root.findViewById(R.id.textViewaddress2);
        ivuser = root.findViewById(R.id.ivuser);
        btn_logout = root.findViewById(R.id.btn_logout);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_container);
        nestedScrollView = root.findViewById(R.id.nestedaccount);

        tvjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Light);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_detail_engineer);
                dialog.show();
                RecyclerView rvdetail = dialog.findViewById(R.id.rvdetailengineer);
                LinearLayoutManager layoutManagerdetail = new LinearLayoutManager(getActivity());
                rvdetail.setLayoutManager(layoutManagerdetail);
                aList.clear();
                aAdapter = new DetailJobAplliedEngineerAdapter(this, aList);
                rvdetail.setAdapter(aAdapter);
                filldata();

                imgClose = dialog.findViewById(R.id.dismissfilter);
                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });*/
                Intent intent = new Intent(getContext(), FabActivity.class);
                intent.putExtra(GET_ID_JOB, "detail_engineer");
                startActivity(intent);
            }
        });

        fillaccount();

        return root;
    }

    /*private void filldata() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJob_withToken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONArray("job");
                    if (response.length() > 0) {

                        if (aList != null) {
                            aList.clear();
                        } else {
                            aList = new ArrayList<>();
                        }

                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            AppliedViewModel itemCategory = new AppliedViewModel();

                            if (cat.getString("job_status").equals("Ready") && cat.getJSONObject("working_engineer").getString("id_engineer").equals(jObj.getString("id_engineer")) ||
                                    cat.getString("job_status").equals("Progress") && cat.getJSONObject("working_engineer").getString("id_engineer").equals(jObj.getString("id_engineer")) ||
                                    cat.getString("job_status").equals("Done") && cat.getJSONObject("working_engineer").getString("id_engineer").equals(jObj.getString("id_engineer"))) {
                                itemCategory.setCategory(cat.getJSONObject("category").getString("category_name"));
                                itemCategory.setJudul(cat.getString("job_name"));
                                itemCategory.setId_job(cat.getString("id"));
                                itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                                itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                                itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));

                                aList.add(itemCategory);

                            }
                            aAdapter.notifyDataSetChanged();
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
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
        requestQueue.add(strReq);
    }*/

    private void fillaccount() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        final Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getuserwithToken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                nestedScrollView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                try {
                    JSONObject jUser = response.getJSONObject("user");
                    Log.i("users", jUser.toString());

                    tvname.setText(jUser.getString("name"));
                    tvemail.setText(jUser.getString("email"));
                    tvno.setText(jUser.getString("phone"));
                    tvaddress.setText(jUser.getString("address"));
                    Glide.with(getActivity()).load(jUser.getString("photo_image_url")).into(ivuser);
                    Date join_date = inputFormat.parse(jUser.getString("date_of_join"));
                    tvdate.setText(dateFormat.format(join_date));
                    tvjobs.setText(jUser.getString("job_engineer_count") + " Jobs");
                    tvskill.setText(jUser.getString("category_engineer"));
                    tvfee.setText(formatRupiah.format((Double.parseDouble(jUser.getString("fee_engineer_count")))));

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

//                headers.put("Authorization", "Bearer 14a1105cf64a44f47dd6d53f6b3beb79b65c1e929a6ee94a5c7ad30528d02c3e");
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

}
