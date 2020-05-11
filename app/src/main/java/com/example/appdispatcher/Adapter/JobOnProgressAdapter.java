package com.example.appdispatcher.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.OnProgressViewModel;

import java.util.ArrayList;

public class JobOnProgressAdapter extends RecyclerView.Adapter<JobOnProgressAdapter.ViewHolder> {

    ArrayList<OnProgressViewModel> progressJobList;

    public JobOnProgressAdapter(ArrayList<OnProgressViewModel> progressJobList) {
        this.progressJobList = progressJobList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_job_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OnProgressViewModel progressjoblist = progressJobList.get(position);
        holder.tvJudul.setText(progressjoblist.judul);
        holder.ivFoto.setImageDrawable(progressjoblist.foto);
        holder.tvLocation.setText(progressjoblist.location);
    }

    @Override
    public int getItemCount() {
        if (progressJobList != null)
            return progressJobList.size();
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
