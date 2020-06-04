package com.example.appdispatcher.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.ProgressDoneViewModel;

import java.util.List;

public class ProgressTaskAdapter extends RecyclerView.Adapter<ProgressTaskAdapter.ViewHolder> {

    //    ArrayList<DetailProgressViewModel> progressList;
    List<ProgressDoneViewModel> TaskList;
    ProgressTaskAdapter pTaskAdapter;
    private ProgressTaskAdapter context;

    public ProgressTaskAdapter(List<ProgressDoneViewModel> pList) {
        super();
        this.TaskList = pList;
        this.context = context;
        pTaskAdapter = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProgressDoneViewModel progresslist = TaskList.get(position);
        holder.tvDate.setText(progresslist.date);
        holder.tvDay.setText(progresslist.day);
        holder.tvIdJob.setText(progresslist.id_job);
        holder.expandabeLayout.setVisibility(View.GONE);
        holder.tvdetail.setText(progresslist.detail_activity);

        boolean isExpended = TaskList.get(position).isExpended();
        holder.expandabeLayout.setVisibility(isExpended ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        if (TaskList != null)
            return TaskList.size();
        return 0;
    }

    public ProgressDoneViewModel getItem(int pos) {
        return TaskList.get(pos);
    }

    public interface PListAdapter {
        void doClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvDay, tvIdJob, tvdetail;
        LinearLayout expandabeLayout;
        RelativeLayout headsub;
        ImageView ivRow;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.textViewDate);
            tvDay = itemView.findViewById(R.id.textViewDay);
            tvIdJob = itemView.findViewById(R.id.TvIdJob);
            tvdetail = itemView.findViewById(R.id.detail_activity);

            ivRow = itemView.findViewById(R.id.row_down);
            expandabeLayout = itemView.findViewById(R.id.expandableLayout);
            headsub = itemView.findViewById(R.id.head_sub);

            headsub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProgressDoneViewModel DPVM = TaskList.get(getAdapterPosition());
                    DPVM.setExpended(!DPVM.isExpended());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

}
