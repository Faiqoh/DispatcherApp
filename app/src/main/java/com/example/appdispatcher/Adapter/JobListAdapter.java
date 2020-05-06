package com.example.appdispatcher.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.ivFoto.setImageDrawable(joblist.foto);
    }

    @Override
    public int getItemCount() {
        if (jobList != null)
            return jobList.size();
        return 0;
    }

    public interface JListAdapter {
        void doClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlistjob);
            tvJudul = itemView.findViewById(R.id.textViewJudul);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mJListAdapter.doClick(getAdapterPosition());
                }
            });
        }
    }
}
