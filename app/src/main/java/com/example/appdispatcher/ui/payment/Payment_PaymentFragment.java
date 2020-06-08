package com.example.appdispatcher.ui.payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    TextView idpayment;

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
        fillDataPaymentList();
        payAdapter = new PaymentAdapter(this, pList);
        recyclerViewPaymentList.setAdapter(payAdapter);

        idpayment = root.findViewById(R.id.idPayment);

        return root;
    }

    private void fillDataPaymentList() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getpayment + "/?id_engineer=" + 1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONArray("payment");
                    Log.i("response payment", jray.toString());

                    if (response.length() > 0) {

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
        });
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
}
