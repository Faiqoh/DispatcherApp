package com.example.appdispatcher.ui.payment;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appdispatcher.Adapter.DetailPaymentAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailPaymentFragment extends Fragment {

    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    public List<DetailPaymentViewModel> dpList = new ArrayList<>();
    DetailPaymentAdapter dpAdapter;
    ImageView iv_cat, iv_tf;
    TextView tvjob, tv_idpayment;

    public static DetailPaymentFragment newInstance() {
        return new DetailPaymentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_payment, container, false);

        Bundle extras = getActivity().getIntent().getExtras();

        String getJob = extras.getString("get_id_payment");


        iv_cat = view.findViewById(R.id.cat_backend);
        iv_tf = view.findViewById(R.id.iv_payment);
        tvjob = view.findViewById(R.id.text_view_job);
        tv_idpayment = view.findViewById(R.id.tv_idpayment);

        RecyclerView recyclerViewdetailpaymentList = view.findViewById(R.id.recyclerviewdetailpayment);
        LinearLayoutManager layoutManagerdetailpaymentList = new LinearLayoutManager(getActivity());
        recyclerViewdetailpaymentList.setLayoutManager(layoutManagerdetailpaymentList);
        dpList.clear();

        dpAdapter = new DetailPaymentAdapter(this, dpList);
        recyclerViewdetailpaymentList.setAdapter(dpAdapter);

        PaymentViewModel detail = (PaymentViewModel) getActivity().getIntent().getSerializableExtra(Payment_PaymentFragment.GET_ID_PAYMENT);
        String id_payment = detail.getId_payment();
        fillDataDetailPaymentList(id_payment);

        return view;

    }

    private void fillDataDetailPaymentList(String id_payment) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getdetailpayment + "/?id_payment=" + id_payment, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                try {
                    JSONObject payment = jObj.getJSONObject("payment");

                    JSONObject job = payment.getJSONObject("job");

                    tvjob.setText(job.getString("job_name"));
                    tv_idpayment.setText(payment.getString("id"));
                    DetailPaymentViewModel detail = new DetailPaymentViewModel();
                    detail.setFt_transfer(payment.getString("payment_invoice_URL"));
                    Glide.with(getActivity()).load(payment.getString("payment_invoice_URL")).into(iv_tf);

                    JSONArray progress = payment.getJSONArray("progress");

                    if (progress.length() > 0) {

                        for (int i = 0; i < progress.length(); i++) {
                            DetailPaymentViewModel detail2 = new DetailPaymentViewModel();
                            JSONObject items = progress.getJSONObject(i);
                            Date date_submit = inputFormat.parse(items.getString("date_time"));
                            detail2.setDate(dateFormat.format(date_submit));
                            detail2.setStatus(items.getString("activity"));
                            if (items.getString("activity").equals("Make Payment")) {
                                detail2.setIcon(R.drawable.make_payment);
                            } else if (items.getString("activity").equals("Update Payment")) {
                                detail2.setIcon(R.drawable.payment_update);
                            } else if (items.getString("activity").equals("Confirm Payment")) {
                                detail2.setIcon(R.drawable.payment_complete);
                            }
                            dpList.add(detail2);
                        }
                        dpAdapter.notifyDataSetChanged();
                    }
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
        requestQueue.add(strReq);
    }

}