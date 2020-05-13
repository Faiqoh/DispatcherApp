package com.example.appdispatcher.ui.detail;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.Adapter.JobOnProgressAdapter;
import com.example.appdispatcher.R;

import java.util.ArrayList;

public class OnProgressFragment extends Fragment {

    private OnProgressViewModel mViewModel;

    ArrayList<OnProgressViewModel> pList = new ArrayList<>();
    JobOnProgressAdapter pAdapter;

    public static OnProgressFragment newInstance() {
        return new OnProgressFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_progress, container, false);

        RecyclerView recyclerViewProgressJobList = view.findViewById(R.id.recyclerViewProgress);
        LinearLayoutManager layoutManagerProgressJobList = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewProgressJobList.setLayoutManager(layoutManagerProgressJobList);
        pList.clear();
        fillDatJobPendingList();
        pAdapter = new JobOnProgressAdapter(pList);
        recyclerViewProgressJobList.setAdapter(pAdapter);


        return view;
    }

    private void fillDatJobPendingList() {
        Resources resources = getResources();
        String[] arJudul = resources.getStringArray(R.array.title_job_progress);
        String[] arLocation = resources.getStringArray(R.array.location_progress);
        TypedArray a = resources.obtainTypedArray(R.array.drawable_job_progress);
        Drawable[] arFoto = new Drawable[a.length()];
        for (int i = 0; i < arFoto.length; i++) {
            arFoto[i] = a.getDrawable(i);
        }
        a.recycle();

        for (int i = 0; i < arJudul.length; i++) {
            pList.add(new OnProgressViewModel(arJudul[i], arFoto[i], arLocation[i]));
        }
        Log.e("dataLog", String.valueOf(pList.size()));
    }

}
