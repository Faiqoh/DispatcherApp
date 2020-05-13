package com.example.appdispatcher.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.home.RecomendJobViewModel;

import java.util.ArrayList;

public class DetailJobCategoryAdapter extends RecyclerView.Adapter<DetailJobCategoryAdapter.ViewHolder> {
    ArrayList<RecomendJobViewModel> recomendJob;


    public DetailJobCategoryAdapter(ArrayList<RecomendJobViewModel> recomendJob) {
        this.recomendJob = recomendJob;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_list_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailJobCategoryAdapter.ViewHolder holder, int position) {
        RecomendJobViewModel recomend = recomendJob.get(position);
        holder.tvJudul.setText(recomend.judul);
        holder.ivFoto.setImageDrawable(recomend.foto);
        holder.tvLocation.setText(recomend.location);
    }

    @Override
    public int getItemCount() {
        if (recomendJob != null)
            return recomendJob.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul, tvLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlistjob);
            tvJudul = itemView.findViewById(R.id.customer);
            tvLocation = itemView.findViewById(R.id.location);
        }
    }
}
