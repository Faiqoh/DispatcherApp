package com.example.appdispatcher.Adapter;

import android.graphics.Color;
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
import com.example.appdispatcher.ui.support.SupportFragment;
import com.example.appdispatcher.ui.support.SupportViewModel;

import java.util.List;

public class GetSupportAdapter extends RecyclerView.Adapter<GetSupportAdapter.ViewHolder> {

    List<SupportViewModel> sList;
    SupportAdapter sListAdapter;
    private SupportFragment context;

    public GetSupportAdapter(SupportFragment context, List<SupportViewModel> sList) {
        super();
        this.sList = sList;
        this.context = context;
        sListAdapter = context;
    }

    @NonNull
    @Override
    public GetSupportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_support_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GetSupportAdapter.ViewHolder holder, int position) {
        SupportViewModel supportlist = sList.get(position);
        holder.tvJudul.setText(supportlist.judul);
//        holder.tvStatus.setText(supportlist.status_support);
        holder.tvDate.setText(supportlist.date);
        holder.tvidSupport.setText(supportlist.id_support);
        Glide.with(context).load(supportlist.getFoto()).into(holder.ivFoto);
        if (supportlist.getStatus_support().equals("Open")) {
            holder.tvStatus.setText(supportlist.status_support);
            holder.tvStatus.setBackgroundColor(Color.parseColor("#e3342f"));
        } else if (supportlist.getStatus_support().equals("Progress")) {
            holder.tvStatus.setText(supportlist.status_support);
            holder.tvStatus.setBackgroundColor(Color.parseColor("#3490dc"));
        } else if (supportlist.getStatus_support().equals("Done")) {
            holder.tvStatus.setText(supportlist.status_support);
            holder.tvStatus.setBackgroundColor(Color.parseColor("#38c172"));
        }
    }

    @Override
    public int getItemCount() {
        if (sList != null)
            return sList.size();
        return 0;
    }

    public SupportViewModel getItem(int pos) {
        return sList.get(pos);
    }

    public interface SupportAdapter {
        void doClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul, tvDate, tvidSupport, tvStatus;
        RelativeLayout head_sub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlist);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvDate = itemView.findViewById(R.id.textViewDate);
            head_sub = itemView.findViewById(R.id.head_sub);
            tvidSupport = itemView.findViewById(R.id.idSupport);
            tvStatus = itemView.findViewById(R.id.textViewStatus);

            head_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sListAdapter.doClick(getAdapterPosition());
                }
            });
        }
    }
}
