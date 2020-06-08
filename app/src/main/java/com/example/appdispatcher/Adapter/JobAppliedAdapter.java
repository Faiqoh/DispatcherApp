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
import com.example.appdispatcher.ui.detail.AppliedFragment;
import com.example.appdispatcher.ui.detail.AppliedViewModel;

import java.util.List;

public class JobAppliedAdapter extends RecyclerView.Adapter<JobAppliedAdapter.ViewHolder> {

    List<AppliedViewModel> ajList;
    AJListAdapter aJListAdapter;
    private AppliedFragment context;

    public JobAppliedAdapter(AppliedFragment context, List<AppliedViewModel> ajList) {
        super();
        this.ajList = ajList;
        this.context = context;
        aJListAdapter = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_job_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppliedViewModel appliedjoblist = ajList.get(position);
        holder.tvJudul.setText(appliedjoblist.judul);
        holder.tvLocation.setText(appliedjoblist.location);
        holder.tvCustomer.setText(appliedjoblist.customer);
        holder.tvId_job.setText(appliedjoblist.id_job);
        holder.tvCategory.setText(appliedjoblist.category);
        Glide.with(context).load(appliedjoblist.getFoto()).into(holder.ivFoto);
    }

    @Override
    public int getItemCount() {
        if (ajList != null)
            return ajList.size();
        return 0;
    }

    public AppliedViewModel getItem(int pos) {
        return ajList.get(pos);
    }

    public interface AJListAdapter {
        void doClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul, tvLocation, tvCustomer, tvId_job, tvCategory;
        RelativeLayout headsub;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlist);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvLocation = itemView.findViewById(R.id.textViewLocation);
            tvCustomer = itemView.findViewById(R.id.textViewCustomer);
            tvId_job = itemView.findViewById(R.id.TvIdJob);
            headsub = itemView.findViewById(R.id.head_sub);
            tvCategory = itemView.findViewById(R.id.textViewCategory);

            headsub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aJListAdapter.doClick(getAdapterPosition());
                }
            });
        }
    }

}
