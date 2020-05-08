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

import com.example.appdispatcher.Adapter.JobPendingAdapter;
import com.example.appdispatcher.R;

import java.util.ArrayList;

public class PendingFragment extends Fragment {

    ArrayList<PendingViewModel> pList = new ArrayList<>();
    JobPendingAdapter pAdapter;

    public static PendingFragment newInstance() {
        return new PendingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        RecyclerView recyclerViewPendingJobList = view.findViewById(R.id.recyclerViewPending);
        LinearLayoutManager layoutManagerPendingJobList = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewPendingJobList.setLayoutManager(layoutManagerPendingJobList);
        fillDatJobPendingList();
        pAdapter = new JobPendingAdapter(pList);
        recyclerViewPendingJobList.setAdapter(pAdapter);

        return view;
    }

    private void fillDatJobPendingList() {
        Resources resources = getResources();
        String[] arJudul = resources.getStringArray(R.array.title_job_pending);
        String[] arLocation = resources.getStringArray(R.array.location_pending);
        TypedArray a = resources.obtainTypedArray(R.array.drawable_job_pending);
        Drawable[] arFoto = new Drawable[a.length()];
        for (int i = 0; i < arFoto.length; i++) {
            arFoto[i] = a.getDrawable(i);
        }
        a.recycle();

        for (int i = 0; i < arJudul.length; i++) {
            pList.add(new PendingViewModel(arJudul[i], arFoto[i], arLocation[i]));
        }
        Log.e("dataLog", String.valueOf(pList.size()));
    }

}
