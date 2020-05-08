package com.example.appdispatcher.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.Animations;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.DetailProgressViewModel;

import java.util.ArrayList;

public class ProgressTaskAdapter extends RecyclerView.Adapter<ProgressTaskAdapter.ViewHolder> {

    ArrayList<DetailProgressViewModel> progressList;

    public ProgressTaskAdapter(ArrayList<DetailProgressViewModel> paymentList) {
        this.progressList = progressList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailProgressViewModel progresslist = progressList.get(position);
        holder.tvDate.setText(progresslist.date);
        holder.tvDay.setText(progresslist.day);
    }

    @Override
    public int getItemCount() {
        if (progressList != null)
            return progressList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvDay;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.textViewDate);
            tvDay = itemView.findViewById(R.id.textViewDay);
        }
    }

    private boolean toggleLayout(boolean isExpanded, View v, LinearLayout layoutExpand) {
        Animations.toggleArrow(v, isExpanded);
        return isExpanded;

    }
}
