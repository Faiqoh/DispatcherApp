package com.example.appdispatcher.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.PendingFragment;
import com.example.appdispatcher.ui.detail.PendingViewModel;

import java.util.List;

public class JobPendingAdapter extends RecyclerView.Adapter<JobPendingAdapter.ViewHolder> {

    List<PendingViewModel> pjList;
    private PendingFragment context;
    PJListAdapter pJListAdapter;

    public JobPendingAdapter(PendingFragment context, List<PendingViewModel> pjList) {
        super();
        this.pjList = pjList;
        this.context = context;
        pJListAdapter = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_job_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PendingViewModel pendingjoblist = pjList.get(position);
        holder.tvJudul.setText(pendingjoblist.judul);
        holder.tvLocation.setText(pendingjoblist.location);
        holder.tvcustomer.setText(pendingjoblist.customer);
        Glide.with(context).load(pendingjoblist.getFoto()).into(holder.ivFoto);
    }

    @Override
    public int getItemCount() {
        if (pjList != null)
            return pjList.size();
        return 0;
    }

    public interface PJListAdapter {
        void doClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul;
        TextView tvLocation;
        TextView tvcustomer;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlist);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvLocation = itemView.findViewById(R.id.textViewLocation);
            tvcustomer = itemView.findViewById(R.id.textViewCustomer);
        }
    }
}
