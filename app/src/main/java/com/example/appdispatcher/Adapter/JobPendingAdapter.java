package com.example.appdispatcher.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.PendingViewModel;

import java.util.ArrayList;

public class JobPendingAdapter extends RecyclerView.Adapter<JobPendingAdapter.ViewHolder> {

    ArrayList<PendingViewModel> pendingJobList;

    public JobPendingAdapter(ArrayList<PendingViewModel> pendingJobList) {
        this.pendingJobList = pendingJobList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_job_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PendingViewModel pendingjoblist = pendingJobList.get(position);
        holder.tvJudul.setText(pendingjoblist.judul);
        holder.ivFoto.setImageDrawable(pendingjoblist.foto);
        holder.tvLocation.setText(pendingjoblist.location);
    }

    @Override
    public int getItemCount() {
        if (pendingJobList != null)
            return pendingJobList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul;
        TextView tvLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlist);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvLocation = itemView.findViewById(R.id.textViewLocation);
        }
    }
}
