package com.example.appdispatcher.ui.payment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.PaymentAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.ScrollingActivityDetailTask;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Payment_PaymentFragment extends Fragment implements PaymentAdapter.PayListAdapter {

    public static final String GET_ID_PAYMENT = "get_id_payment";
    PaymentAdapter pAdapter;
    public List<PaymentViewModel> pList = new ArrayList<>();
    PaymentAdapter payAdapter;
    //    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView idpayment;
    NestedScrollView scrollView;
    BottomNavigationView navigation;

    public Payment_PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_payment__payment, container, false);

        RecyclerView recyclerViewPaymentList = root.findViewById(R.id.recyclerViewPayment);
        LinearLayoutManager layoutManagerPaymentList = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewPaymentList.setLayoutManager(layoutManagerPaymentList);

        payAdapter = new PaymentAdapter(this, pList);
        recyclerViewPaymentList.setAdapter(payAdapter);

        pList.clear();
        fillDataPaymentList();

        idpayment = root.findViewById(R.id.idPayment);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_payment);
        scrollView = root.findViewById(R.id.scroll_payment);
        navigation = getActivity().findViewById(R.id.nav_view);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            boolean isNavigationHide = false;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) { // up
                    animateNavigation(false);
                }
                if (scrollY > oldScrollY) { // down
                    animateNavigation(true);
                }
            }

            private void animateNavigation(boolean hide) {
                if (isNavigationHide && hide || !isNavigationHide && !hide) return;
                isNavigationHide = hide;
                int moveY = hide ? (2 * navigation.getHeight()) : 0;
                navigation.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
            }
        });

        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

                scrollView.setVisibility(View.GONE);
                pList.clear();
                fillDataPaymentList();
            }
        });

        return root;
    }

    private void fillDataPaymentList() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getpayment_withToken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                scrollView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                try {
                    JSONArray jray = jObj.getJSONArray("payment");
                    if (response.length() > 0) {
                        if (pList != null) {
                            pList.clear();
                        } else {
                            pList = new ArrayList<>();
                        }

                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            PaymentViewModel itemCategory = new PaymentViewModel();
                            itemCategory.setId_payment(cat.getString("id"));
                            itemCategory.setJudul(cat.getJSONObject("job").getString("job_name"));
                            itemCategory.setStatus_payment(cat.getJSONObject("lastest_progress").getString("activity"));
                            if (cat.getJSONObject("lastest_progress").getString("activity").equals("Make Payment")) {
                                itemCategory.setFoto(R.drawable.make_payment);
                            } else if (cat.getJSONObject("lastest_progress").getString("activity").equals("Update Payment")) {
                                itemCategory.setFoto(R.drawable.payment_update);
                            } else if (cat.getJSONObject("lastest_progress").getString("activity").equals("Confirm Payment")) {
                                itemCategory.setFoto(R.drawable.payment_complete);
                            }
                            pList.add(itemCategory);
                        }
                        payAdapter.notifyDataSetChanged();
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
    }

    @Override
    public void doClick(int pos) {
        Intent intent = new Intent(getContext(), ScrollingActivityDetailTask.class);
        intent.putExtra(GET_ID_PAYMENT, payAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_payment");
        startActivity(intent);
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
