package com.example.appdispatcher.ui.payment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.chrisbanes.photoview.PhotoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailPaymentFragment extends Fragment {

    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    public List<DetailPaymentViewModel> dpList = new ArrayList<>();
    DetailPaymentAdapter dpAdapter;
    ImageView iv_fto;
    TextView tvjob, tv_idpayment;
    ShimmerFrameLayout shimmerFrameLayout;
    ScrollView scrollView;
    RelativeLayout relativePayment;
    ProgressBar progressBar;
    PhotoView iv_tf;
    CardView cvFoto;

    public static DetailPaymentFragment newInstance() {
        return new DetailPaymentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_payment, container, false);

        Bundle extras = getActivity().getIntent().getExtras();

//        String getJob = extras.getString("get_id_payment");

        iv_fto = view.findViewById(R.id.cat_backend);
        iv_tf = view.findViewById(R.id.iv_payment);
        tvjob = view.findViewById(R.id.text_view_job);
        tv_idpayment = view.findViewById(R.id.tv_idpayment);
        progressBar = view.findViewById(R.id.progressBar1);
        cvFoto = view.findViewById(R.id.cardviewimage);

        iv_tf.setImageResource(R.drawable.bg_grey_awas_ae_sek_gak_iso);

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

        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);
        cvFoto.setVisibility(View.GONE);

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getdetailpayment_withToken + "/?id_payment=" + id_payment, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                progressBar.setVisibility(View.GONE);
                cvFoto.setVisibility(View.VISIBLE);

                try {
                    JSONObject payment = jObj.getJSONObject("payment");

                    JSONObject job = payment.getJSONObject("job");

                    tvjob.setText(job.getString("job_name"));
                    tv_idpayment.setText(payment.getString("id"));
                    DetailPaymentViewModel detail = new DetailPaymentViewModel();
                    detail.setFt_transfer(payment.getString("payment_invoice_URL"));
                    Glide.with(getActivity()).load(payment.getString("payment_invoice_URL")).into(iv_tf);
                    Glide.with(getActivity()).load(payment.getString("job_category_image")).into(iv_fto);

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
    }

    /*@Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }*/

}