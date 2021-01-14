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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NotifFragment extends Fragment {

    DataHelper dbHelper;
    NotificationAdapter adapter;
    private ArrayList<String> title_list;
    private ArrayList<String> message_list;
    private ArrayList<String> date_list;
    public static final String DATE_FORMAT_5 = "dd-MM-yyyy HH:mm";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notif, container, false);

        dbHelper = new DataHelper(getContext());
        title_list = new ArrayList<>();
        message_list = new ArrayList<>();
        date_list = new ArrayList<String>();
        try {
            getData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewNotif);
        adapter = new NotificationAdapter(getContext(), title_list, message_list, date_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void getData() throws ParseException {
        Cursor cursor = dbHelper.readAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                title_list.add(cursor.getString(1));
                message_list.add(cursor.getString(2));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date newDate = null;
                try {
                    String dateTime = cursor.getString(3);
                    newDate = format.parse(dateTime);
                    format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    String date = format.format(newDate);
                    date_list.add(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}