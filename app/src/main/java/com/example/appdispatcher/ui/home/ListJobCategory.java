package com.example.appdispatcher.ui.home;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.Adapter.DetailJobCategoryAdapter;
import com.example.appdispatcher.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListJobCategory extends Fragment {

    ArrayList<RecomendJobViewModel> rList = new ArrayList<>();
    DetailJobCategoryAdapter rAdapter;

    public ListJobCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_job_category, container, false);

        // Inflate the layout for this fragment
        RecyclerView recyclerView2 = root.findViewById(R.id.recyclerViewlistJobCategory);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(layoutManager2);
        rAdapter = new DetailJobCategoryAdapter(rList);
        recyclerView2.setAdapter(rAdapter);
        fillData2();
        return root;
    }

    private void fillData2() {
        Resources resources = getResources();
        String[] arJudul2 = resources.getStringArray(R.array.titlejob);
        String[] arlocation2 = resources.getStringArray(R.array.long_location);
        TypedArray a = resources.obtainTypedArray(R.array.recomend_job);
        Drawable[] arFoto2 = new Drawable[a.length()];
        for (int i = 0; i < arFoto2.length; i++) {
            arFoto2[i] = a.getDrawable(i);
        }
        a.recycle();

        for (int i = 0; i < arJudul2.length; i++) {
            rList.add(new RecomendJobViewModel(arJudul2[i], arlocation2[i], arFoto2[i]));
        }
        rAdapter.notifyDataSetChanged();
    }
}
