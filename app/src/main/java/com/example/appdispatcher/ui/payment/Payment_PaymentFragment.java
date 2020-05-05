package com.example.appdispatcher.ui.payment;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.Adapter.PaymentAdapter;
import com.example.appdispatcher.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Payment_PaymentFragment extends Fragment {

    ArrayList<PaymentViewModel> pList = new ArrayList<>();
    PaymentAdapter pAdapter;

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
        pAdapter = new PaymentAdapter(pList);
        recyclerViewPaymentList.setAdapter(pAdapter);

        return root;
    }

    private void fillDataPaymentList() {
        Resources resources = getResources();
        String[] arJudul = resources.getStringArray(R.array.title_job_payment);
        String[] arLocation = resources.getStringArray(R.array.location);
        TypedArray a = resources.obtainTypedArray(R.array.drawable_job_payment);
        Drawable[] arFoto = new Drawable[a.length()];
        for (int i = 0; i < arFoto.length; i++) {
            arFoto[i] = a.getDrawable(i);
        }
        a.recycle();

        for (int i = 0; i < arJudul.length; i++) {
            pList.add(new PaymentViewModel(arJudul[i], arFoto[i], arLocation[i]));
        }
        Log.e("dataLog", String.valueOf(pList.size()));
    }
}
