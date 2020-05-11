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

import com.example.appdispatcher.Adapter.JobDoneAdapter;
import com.example.appdispatcher.R;

import java.util.ArrayList;

public class DoneFragment extends Fragment {

    private DoneViewModel mViewModel;

    ArrayList<DoneViewModel> dList = new ArrayList<>();
    JobDoneAdapter dAdapter;

    public static DoneFragment newInstance() {
        return new DoneFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done, container, false);

        RecyclerView recyclerViewDoneJobList = view.findViewById(R.id.recyclerViewDone);
        LinearLayoutManager layoutManagerDoneJobList = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewDoneJobList.setLayoutManager(layoutManagerDoneJobList);
        fillDatJobPendingList();
        dAdapter = new JobDoneAdapter(dList);
        recyclerViewDoneJobList.setAdapter(dAdapter);

        return view;
    }

    private void fillDatJobPendingList() {
        Resources resources = getResources();
        String[] arJudul = resources.getStringArray(R.array.title_job_done);
        String[] arLocation = resources.getStringArray(R.array.location_done);
        TypedArray a = resources.obtainTypedArray(R.array.drawable_job_done);
        Drawable[] arFoto = new Drawable[a.length()];
        for (int i = 0; i < arFoto.length; i++) {
            arFoto[i] = a.getDrawable(i);
        }
        a.recycle();

        for (int i = 0; i < arJudul.length; i++) {
            dList.add(new DoneViewModel(arJudul[i], arFoto[i], arLocation[i]));
        }
        Log.e("dataLog", String.valueOf(dList.size()));
    }

}
