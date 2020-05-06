package com.example.appdispatcher.ui.detail;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.Adapter.ProgressTaskAdapter;
import com.example.appdispatcher.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailProgressTaskFragment extends Fragment {

    ArrayList<DetailProgressViewModel> pList = new ArrayList<>();
    ProgressTaskAdapter pAdapter;

    public DetailProgressTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_progress_task, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewprogresstask);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        pAdapter = new ProgressTaskAdapter(pList);
        recyclerView.setAdapter(pAdapter);
        fillData();

        return view;
    }

    private void fillData() {
        Resources resources = getResources();
        String[] arDate = resources.getStringArray(R.array.title_date);
        String[] arDay = resources.getStringArray(R.array.title_day);

        for (int i = 0; i < arDate.length; i++) {
            pList.add(new DetailProgressViewModel(arDate[i], arDay[i]));
        }
        pAdapter.notifyDataSetChanged();
    }
}
