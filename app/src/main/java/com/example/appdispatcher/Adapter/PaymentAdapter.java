package com.example.appdispatcher.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.payment.PaymentViewModel;
import com.example.appdispatcher.ui.payment.Payment_PaymentFragment;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    List<PaymentViewModel> paymentList;
    PayListAdapter payListAdapter;
    private Payment_PaymentFragment context;

    public PaymentAdapter(Payment_PaymentFragment context, List<PaymentViewModel> paymentList) {
        super();
        this.paymentList = paymentList;
        this.context = context;
        payListAdapter = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentViewModel paymentlist = paymentList.get(position);
        holder.tvJudul.setText(paymentlist.judul);
        holder.tvLocation.setText(paymentlist.status_payment);
        holder.tv_IdJob.setText(paymentlist.id_job);
        holder.ivFoto.setImageDrawable(paymentlist.foto);
    }

    @Override
    public int getItemCount() {
        if (paymentList != null)
            return paymentList.size();
        return 0;
    }


    public PaymentViewModel getItem(int pos) {
        return paymentList.get(pos);
    }

    public interface PayListAdapter {
        void doClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul, tvLocation, tv_IdJob;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlist);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvLocation = itemView.findViewById(R.id.textViewLocation);
            tv_IdJob = itemView.findViewById(R.id.TvIdJob);
        }
    }
}
