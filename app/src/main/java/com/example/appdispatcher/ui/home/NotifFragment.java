package com.example.appdispatcher.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdispatcher.Adapter.NotificationAdapter;
import com.example.appdispatcher.DataHelper;
import com.example.appdispatcher.R;

import java.util.ArrayList;

public class NotifFragment extends Fragment {

    DataHelper dbHelper;
    NotificationAdapter adapter;
    private ArrayList<String> title_list, message_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notif, container, false);

        dbHelper = new DataHelper(getContext());
        title_list = new ArrayList<>();
        message_list = new ArrayList<>();
        getData();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewNotif);
        adapter = new NotificationAdapter(getContext(), title_list, message_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        Log.d("data", String.valueOf(title_list));

        return view;
    }

    private void getData() {
        Cursor cursor = dbHelper.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
//                Toast.makeText(getContext(), "datane ono", Toast.LENGTH_SHORT).show();
                title_list.add(cursor.getString(1));
                message_list.add(cursor.getString(2));

            }
        }
    }
}