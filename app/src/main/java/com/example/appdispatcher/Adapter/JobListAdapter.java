package com.example.appdispatcher.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.Animations;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.home.HomeFragment;
import com.example.appdispatcher.ui.home.HomeViewModel;

import java.util.List;


public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {

    //    ArrayList<HomeViewModel> jobList;
    List<HomeViewModel> jobList;
    private HomeFragment context;
    JListAdapter mJListAdapter;

    public JobListAdapter(HomeFragment context, List<HomeViewModel> jobList) {
//        this.jobList = jobList;
        super();
        this.jobList = jobList;
        this.context = context;
        mJListAdapter = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JobListAdapter.ViewHolder holder, int position) {
        HomeViewModel joblist = jobList.get(position);
        holder.tvJudul.setText(joblist.judul);
        holder.ivFoto.setImageURI(Uri.parse(joblist.foto));
        holder.tvLocation.setText(joblist.location);
        holder.tvCustomer.setText(joblist.customer);
        holder.tvIdJob.setText(joblist.id_job);
        holder.expandabeLayout.setVisibility(View.GONE);

        boolean isExpended = jobList.get(position).isExpended();
        holder.expandabeLayout.setVisibility(isExpended ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        if (jobList != null)
            return jobList.size();
        return 0;
    }

    public HomeViewModel getItem(int pos) {
        return jobList.get(pos);
    }

    public interface JListAdapter {
        void doClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto, ivRow;
        TextView tvJudul, tvLocation, tvCustomer, tvIdJob;
        LinearLayout expandabeLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlistjob);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvCustomer = itemView.findViewById(R.id.customer);
            tvLocation = itemView.findViewById(R.id.location);
            tvIdJob = itemView.findViewById(R.id.TvIdJob);

            ivRow = itemView.findViewById(R.id.row_down);
            expandabeLayout = itemView.findViewById(R.id.expandableLayout);

            expandabeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mJListAdapter.doClick(getAdapterPosition());
                }
            });

            tvJudul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeViewModel HVM = jobList.get(getAdapterPosition());
                    HVM.setExpended(!HVM.isExpended());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

    private boolean toggleLayout(boolean isExpanded, View v, LinearLayout layoutExpand) {
        Animations.toggleArrow(v, isExpanded);
        return isExpanded;

    }
}
