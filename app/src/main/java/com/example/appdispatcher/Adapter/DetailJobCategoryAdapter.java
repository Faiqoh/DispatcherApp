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
import com.example.appdispatcher.ui.home.HomeViewModel;
import com.example.appdispatcher.ui.home.ListJobCategory;

import java.util.ArrayList;
import java.util.List;

public class DetailJobCategoryAdapter extends RecyclerView.Adapter<DetailJobCategoryAdapter.ViewHolder> {

    List<HomeViewModel> categoryJob;
    JobCategoryAdapter.CListAdapter mCListAdapter;
    private ListJobCategory context;


    public DetailJobCategoryAdapter(ArrayList<HomeViewModel> recomendJob, ListJobCategory context) {
        this.categoryJob = recomendJob;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_list_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailJobCategoryAdapter.ViewHolder holder, int position) {
        HomeViewModel recomend = categoryJob.get(position);
        holder.tvJudul.setText(recomend.customer);
        Glide.with(context).load(recomend.getFoto()).into(holder.ivFoto);
        holder.tvLocation.setText(recomend.location);
    }

    @Override
    public int getItemCount() {
        if (categoryJob != null)
            return categoryJob.size();
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
