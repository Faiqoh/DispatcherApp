package com.example.appdispatcher.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.home.JobCategoryViewModel;

import java.util.List;

public class ChildAllCategoryAdapter extends RecyclerView.Adapter<ChildAllCategoryAdapter.ViewHolder> {

    List<JobCategoryViewModel> categoryList;
    Context context;
//    private AllFragmentCategory context;


    public ChildAllCategoryAdapter(List<JobCategoryViewModel> chidlList, Context mcontrxt) {
        super();
        this.categoryList = chidlList;
        this.context = mcontrxt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_category_childrow, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobCategoryViewModel childlist = categoryList.get(position);
        Glide.with(context).load(childlist.getFoto()).into(holder.ivFoto);
        holder.tvJudul.setText(childlist.judul);
    }

    @Override
    public int getItemCount() {
        if (categoryList != null)
            return categoryList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvJudul, tvidCat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.imageViewlistjob);
            tvJudul = itemView.findViewById(R.id.textViewJudul);
            tvidCat = itemView.findViewById(R.id.TvIdCat);

        }
    }

}
