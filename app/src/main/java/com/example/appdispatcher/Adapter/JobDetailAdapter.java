package com.example.appdispatcher.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
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
import com.example.appdispatcher.ui.detail.ScrollingActivityDetailTask;
import com.example.appdispatcher.ui.detail2.Detail2Fragment;
import com.example.appdispatcher.ui.detail2.Detail2ViewModel;
import com.example.appdispatcher.ui.home.HomeViewModel;
import com.example.appdispatcher.ui.home.ListJobCategory;

import java.util.ArrayList;
import java.util.List;

public class JobDetailAdapter extends RecyclerView.Adapter<JobDetailAdapter.ViewHolder> {

    List<Detail2ViewModel> dList;
    Detail2Fragment dListAdapter;
    private Detail2Fragment context;

    public static final String ID_JOB = "id_job";
    public static final String STATUS_JOB = "status_job";
    public static final String GET_ID_JOB = "get_id_job";

    public JobDetailAdapter(Detail2Fragment context, List<Detail2ViewModel> recomendJob) {
        super();
        this.dList = recomendJob;
        this.context = context;
        dListAdapter = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_job_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JobDetailAdapter.ViewHolder holder, int position) {
        final Detail2ViewModel appliedjoblist = dList.get(position);
        holder.tvJudul.setText(appliedjoblist.judul);
        holder.tvLocation.setText(appliedjoblist.location);
        holder.tvCustomer.setText(appliedjoblist.customer);
        holder.id_job.setText(appliedjoblist.id_job);
        holder.tvCategory.setText(appliedjoblist.category);
        Glide.with(context).load(appliedjoblist.getFoto()).into(holder.ivFoto);
        if (appliedjoblist.getStatus().equals("Open")) {
            holder.tvStatus.setText("Applied");
            holder.tvStatus.setBackgroundColor(Color.parseColor("#e3342f"));
        } else if (appliedjoblist.getStatus().equals("Ready")) {
            holder.tvStatus.setText("Accepted");
            holder.tvStatus.setBackgroundColor(Color.parseColor("#ffc800"));
        } else if (appliedjoblist.getStatus().equals("Progress")) {
            holder.tvStatus.setText(appliedjoblist.status);
            holder.tvStatus.setBackgroundColor(Color.parseColor("#3490dc"));
        } else if (appliedjoblist.getStatus().equals("Done")) {
            holder.tvStatus.setText(appliedjoblist.status);
            holder.tvStatus.setBackgroundColor(Color.parseColor("#38c172"));
        }
        holder.headsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getActivity(), ScrollingActivityDetailTask.class);
                intent.putExtra(ID_JOB, appliedjoblist);
                intent.putExtra(STATUS_JOB, appliedjoblist.status);
                intent.putExtra(GET_ID_JOB, "id_list2");
                dListAdapter.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
//        if (dList != null)
            return dList.size();
//        return 0;
    }

    public Detail2ViewModel getItem(int pos) {
        return dList.get(pos);
    }

    public void filterList(ArrayList<Detail2ViewModel> filteredList) {
        dList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul, tvLocation, tvCustomer, id_job, tvCategory, tvStatus;
        RelativeLayout headsub;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlist);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvLocation = itemView.findViewById(R.id.textViewLocation);
            tvCustomer = itemView.findViewById(R.id.textViewCustomer);
            id_job = itemView.findViewById(R.id.TvIdJob);
            headsub = itemView.findViewById(R.id.head_sub);
            tvCategory = itemView.findViewById(R.id.textViewCategory);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvStatus.setVisibility(View.VISIBLE);
        }
    }
}


