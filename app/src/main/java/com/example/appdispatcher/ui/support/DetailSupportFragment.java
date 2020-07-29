package com.example.appdispatcher.ui.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailSupportFragment extends Fragment {

    TextView tvproblem, tvreason, tvjob;
    ImageView ivfoto;
    ProgressBar progressBar;
    CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_support, container, false);

        tvproblem = view.findViewById(R.id.tvproblem);
        tvreason = view.findViewById(R.id.tvreason);
        ivfoto = view.findViewById(R.id.iv_support);
        progressBar = view.findViewById(R.id.progressBarsupport);
        cardView = view.findViewById(R.id.cvdesc);
        tvjob = view.findViewById(R.id.text_view_job);

        SupportViewModel detail = (SupportViewModel) getActivity().getIntent().getSerializableExtra(SupportFragment.ID_SUPPORT);
        String id_support = detail.getId_support();
        fillDetail(id_support);

        return view;
    }

    private void fillDetail(String id_support) {
//        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.getdetailsupport_withtoken + "?id_support=" + id_support, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);

                try {
                    JSONObject sup = response.getJSONObject("job_support");

                    JSONObject job = sup.getJSONObject("job");

                    tvjob.setText(job.getString("job_name"));
                    tvproblem.setText(sup.getString("problem_support"));
                    tvreason.setText(sup.getString("reason_support"));
                    Glide.with(getActivity()).load(sup.getString("picture_support_url")).into(ivfoto);

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