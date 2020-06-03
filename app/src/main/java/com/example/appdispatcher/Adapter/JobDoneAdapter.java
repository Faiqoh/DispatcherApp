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
import com.example.appdispatcher.ui.detail.DoneFragment;
import com.example.appdispatcher.ui.detail.DoneViewModel;

import java.util.List;

public class JobDoneAdapter extends RecyclerView.Adapter<JobDoneAdapter.ViewHolder> {

    List<DoneViewModel> djList;
    DJListAdapter dJListAdapter;
    private DoneFragment context;

    public JobDoneAdapter(DoneFragment context, List<DoneViewModel> djList) {
        super();
        this.djList = djList;
        this.context = context;
        dJListAdapter = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_job_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoneViewModel donejoblist = djList.get(position);
        holder.tvJudul.setText(donejoblist.judul);
        holder.tvLocation.setText(donejoblist.location);
        holder.tvCustomer.setText(donejoblist.customer);
        holder.tvId_job.setText(donejoblist.id_job);
        holder.tvCategory.setText(donejoblist.category);
        Glide.with(context).load(donejoblist.getFoto()).into(holder.ivFoto);
    }

    @Override
    public int getItemCount() {
        if (djList != null)
            return djList.size();
        return 0;
    }

    public DoneViewModel getItem(int pos) {
        return djList.get(pos);
    }

    public interface DJListAdapter {
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
                    dJListAdapter.doClick(getAdapterPosition());
                }
            });
        }
    }
}
