package com.example.appdispatcher.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.home.HomeViewModel;
import com.example.appdispatcher.ui.home.ListJobCategory;

import java.util.List;

public class DetailJobCategoryAdapter extends RecyclerView.Adapter<DetailJobCategoryAdapter.ViewHolder> {

    List<HomeViewModel> categoryJob;
    DetailJobCategoryAdapter.CListAdapter mCListAdapter;
    private ListJobCategory context;


    public DetailJobCategoryAdapter(ListJobCategory context, List<HomeViewModel> recomendJob) {
        super();
        this.categoryJob = recomendJob;
        this.context = context;
        mCListAdapter = context;
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
        holder.tvIdJob.setText(recomend.id_job);
        holder.tvNameCategory.setText(recomend.category_name);
        holder.tvJobname.setText(recomend.job_name);
    }

    @Override
    public int getItemCount() {
        if (categoryJob != null)
            return categoryJob.size();
        return 0;
    }

    public HomeViewModel getItem(int pos) {
        return categoryJob.get(pos);
    }

    public interface CListAdapter {
        void doClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul, tvLocation, tvIdJob, tvNameCategory, tvJobname;
        RelativeLayout RLlistjob;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlistjob);
            tvJudul = itemView.findViewById(R.id.customer);
            tvLocation = itemView.findViewById(R.id.location);
            RLlistjob = itemView.findViewById(R.id.list_job);
            tvIdJob = itemView.findViewById(R.id.TvIdJob);
            tvNameCategory = itemView.findViewById(R.id.category_name);
            tvJobname = itemView.findViewById(R.id.jobname);

            RLlistjob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCListAdapter.doClick(getAdapterPosition());
                }
            });

        }
    }
}
