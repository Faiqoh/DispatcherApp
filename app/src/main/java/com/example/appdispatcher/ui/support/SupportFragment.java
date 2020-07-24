package com.example.appdispatcher.ui.support;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.Adapter.GetSupportAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.ScrollingActivityDetailTask;

import java.util.ArrayList;
import java.util.List;

public class SupportFragment extends Fragment implements GetSupportAdapter.SupportAdapter {

    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    public List<SupportViewModel> sList = new ArrayList<>();
    GetSupportAdapter sAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_support, container, false);

        RecyclerView rvsupport = view.findViewById(R.id.recyclerViewHistorySupp);
        LinearLayoutManager lmsupport = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvsupport.setLayoutManager(lmsupport);
        sList.clear();
        fillData();
        sAdapter = new GetSupportAdapter(this, sList);
        rvsupport.setAdapter(sAdapter);

        return view;
    }

    private void fillData() {

    }

    @Override
    public void doClick(int pos) {
        Intent intent = new Intent(getContext(), ScrollingActivityDetailTask.class);
        intent.putExtra(ID_JOB, sAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_support");
        startActivity(intent);
    }
}
