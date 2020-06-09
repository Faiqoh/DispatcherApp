package com.example.appdispatcher.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.payment.DetailPaymentFragment;
import com.example.appdispatcher.ui.payment.DetailPaymentViewModel;

import java.util.List;

public class DetailPaymentAdapter extends RecyclerView.Adapter<DetailPaymentAdapter.ViewHolder> {

    List<DetailPaymentViewModel> dpList;
    private DetailPaymentFragment context;

    public DetailPaymentAdapter(DetailPaymentFragment context, List<DetailPaymentViewModel> dpList) {
        super();
        this.dpList = dpList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_payment_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailPaymentViewModel detaillist = dpList.get(position);
        holder.tvDate.setText(detaillist.date);
        holder.tvStatus.setText(detaillist.status);
        holder.tvidPayment.setText(detaillist.id_payment);
        ContextCompat.getDrawable(context.getContext(), detaillist.icon);
        holder.ivFoto.setImageResource(detaillist.icon);
    }

    @Override
    public int getItemCount() {
        if (dpList != null)
            return dpList.size();
        return 0;
    }

    public DetailPaymentViewModel getItem(int pos) {
        return dpList.get(pos);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvDate, tvStatus, tvidPayment;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlist);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvidPayment = itemView.findViewById(R.id.TvIdPayment);
        }
    }
}
