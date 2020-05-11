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
import com.example.appdispatcher.ui.home.JobCategoryViewModel;

import java.util.List;

public class JobCategoryAdapter extends RecyclerView.Adapter<JobCategoryAdapter.ViewHolder> {

    //    ArrayList<JobCategoryViewModel> cList;
    List<JobCategoryViewModel> categoryList;
    private HomeFragment context;


    public JobCategoryAdapter(HomeFragment context, List<JobCategoryViewModel> cList) {
        super();
        this.categoryList = cList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobCategoryViewModel clist = categoryList.get(position);
        holder.tvJudul.setText(clist.judul);
        holder.ivFoto.setImageDrawable(clist.foto);
    }

    @Override
    public int getItemCount() {
        if (categoryList != null)
            return categoryList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.imageViewlistjob);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
        }
    }
}