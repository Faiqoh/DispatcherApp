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
import com.example.appdispatcher.ui.detail.AcceptedFragment;
import com.example.appdispatcher.ui.detail.AcceptedViewModel;

import java.util.List;

public class JobAcceptedAdapter extends RecyclerView.Adapter<JobAcceptedAdapter.ViewHolder> {

    List<AcceptedViewModel> pjList;
    private AcceptedFragment context;
    PJListAdapter pJListAdapter;

    public JobAcceptedAdapter(AcceptedFragment context, List<AcceptedViewModel> pjList) {
        super();
        this.pjList = pjList;
        this.context = context;
        pJListAdapter = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_job_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AcceptedViewModel pendingjoblist = pjList.get(position);
        holder.tvJudul.setText(pendingjoblist.judul);
        holder.tvLocation.setText(pendingjoblist.location);
        holder.tvcustomer.setText(pendingjoblist.customer);
        holder.tvId_job.setText(pendingjoblist.id_job);
        holder.tvCategory.setText(pendingjoblist.category);
        Glide.with(context).load(pendingjoblist.getFoto()).into(holder.ivFoto);
    }

    @Override
    public int getItemCount() {
        if (pjList != null)
            return pjList.size();
        return 0;
    }

    public AcceptedViewModel getItem(int pos) {
        return pjList.get(pos);
    }

    public interface PJListAdapter {
        void doClick(int pos);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul, tvLocation, tvcustomer, tvId_job, tvCategory;
        RelativeLayout headsub;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlist);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvLocation = itemView.findViewById(R.id.textViewLocation);
            tvcustomer = itemView.findViewById(R.id.textViewCustomer);
            tvId_job = itemView.findViewById(R.id.TvIdJob);
            headsub = itemView.findViewById(R.id.head_sub);
            tvCategory = itemView.findViewById(R.id.textViewCategory);

            headsub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pJListAdapter.doClick(getAdapterPosition());
                }
            });
        }
    }
}
