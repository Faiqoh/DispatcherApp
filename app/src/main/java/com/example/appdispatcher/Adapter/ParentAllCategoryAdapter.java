package com.example.appdispatcher.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.home.JobCategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class ParentAllCategoryAdapter extends RecyclerView.Adapter<ParentAllCategoryAdapter.ViewHolder> {

    public List<JobCategoryViewModel> childList = new ArrayList<>();
    List<JobCategoryViewModel> categoryList;
    //    private AllFragmentCategory context;
    Context context;
    ChildAllCategoryAdapter childAdapter;

    public ParentAllCategoryAdapter(Context context, List<JobCategoryViewModel> childList) {
        super();
        this.categoryList = childList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_category_parentrow, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        JobCategoryViewModel clist = categoryList.get(position);
        holder.tvmainJudul.setText(clist.main_category);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.childRV.setLayoutManager(layoutManager);
        holder.childRV.setHasFixedSize(true);

        Log.i("isinya", clist.details.get(0).foto);
//
        ChildAllCategoryAdapter childAllCategoryAdapter = new ChildAllCategoryAdapter(clist.details, context);
        holder.childRV.setAdapter(childAllCategoryAdapter);
        childAllCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (categoryList != null)
            return categoryList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvmainJudul;
        RecyclerView childRV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmainJudul = itemView.findViewById(R.id.textViewHead);
            childRV = itemView.findViewById(R.id.childRVAllCategory);

        }
    }
}