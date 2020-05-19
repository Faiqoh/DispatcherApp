package com.example.appdispatcher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.DetailActivity;
import com.example.appdispatcher.ui.home.JobCategoryViewModel;

import java.util.List;

public class ChildAllCategoryAdapter extends RecyclerView.Adapter<ChildAllCategoryAdapter.ViewHolder> {
    public static final String CATEGORY_NAME = "category_name";
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final String ID_JOB_1 = "id_job_1";
        final String GET_ID_JOB = "get_id_job";

        final JobCategoryViewModel childlist = categoryList.get(position);
        Glide.with(context).load(childlist.getFoto()).into(holder.ivFoto);
        holder.tvJudul.setText(childlist.judul);
        holder.tvidCat.setText(String.valueOf(childlist.id_category));

        holder.ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("tess", holder.tvidCat.toString());
                Intent intent = new Intent(context, DetailActivity.class);
                int id_category = categoryList.get(position).getId_category();
                String category_name = categoryList.get(position).getJudul();
                intent.putExtra(ID_JOB_1, id_category);
                intent.putExtra(GET_ID_JOB, "id_all_category");
                intent.putExtra(CATEGORY_NAME, category_name);

                context.startActivity(intent);
            }
        });
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
