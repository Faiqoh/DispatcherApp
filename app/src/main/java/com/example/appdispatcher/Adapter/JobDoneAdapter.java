package com.example.appdispatcher.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.DoneViewModel;

import java.util.ArrayList;

public class JobDoneAdapter extends RecyclerView.Adapter<JobDoneAdapter.ViewHolder> {

    ArrayList<DoneViewModel> doneJobList;

    public JobDoneAdapter(ArrayList<DoneViewModel> doneJobList) {
        this.doneJobList = doneJobList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_job_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoneViewModel donejoblist = doneJobList.get(position);
        holder.tvJudul.setText(donejoblist.judul);
        holder.ivFoto.setImageDrawable(donejoblist.foto);
        holder.tvLocation.setText(donejoblist.location);
    }

    @Override
    public int getItemCount() {
        if (doneJobList != null)
            return doneJobList.size();
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
