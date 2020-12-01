package com.example.appdispatcher.ui.fab;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.ProgressDoneViewModel;
import com.example.appdispatcher.util.server;

import org.json.JSONArray;
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

public class RequestDoneFabFragment extends Fragment {

    TextView tvname, tvnominal, tvreason, tv_id_job;
    ImageView imgupload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_request_done_fab, container, false);
        tvname = view.findViewById(R.id.tvNama2);
        tvnominal = view.findViewById(R.id.tvNominal2);
        tvreason = view.findViewById(R.id.tvAlasan2);
        imgupload = view.findViewById(R.id.imgUpload);
        tv_id_job = view.findViewById(R.id.tv_id_job);

        Bundle extras = getActivity().getIntent().getExtras();
        String id_jobb = extras.getString("id_job");

        fillDetail(id_jobb);

        return view;
    }

    public void fillDetail(String id_jobb) {
        final Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.progreesjob_withToken + "?id_job=" + id_jobb, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject job = response.getJSONObject("job");
                    JSONObject history_request = job.getJSONObject("latest_job_request");

                    tvname.setText(history_request.getString("name_item"));
                    tvreason.setText(history_request.getString("function_item"));
                    tvnominal.setText(formatRupiah.format((Double.parseDouble(history_request.getString("price_item")))));
                    Glide.with(getActivity()).load(history_request.getString("documentation_item_url")).into(imgupload);
                    tv_id_job.setText(job.getString("id"));

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
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(StrReq);
    }
}