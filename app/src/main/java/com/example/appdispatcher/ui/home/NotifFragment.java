package com.example.appdispatcher.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.appdispatcher.DataHelper;
import com.example.appdispatcher.R;

public class NotifFragment extends Fragment {

    protected Cursor cursor;
    DataHelper dbHelper;
    Button ton1;
    EditText text1, text2, text3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notif, container, false);

        /*RecyclerView rvnotif = view.findViewById(R.id.recyclerViewNotif);
        LinearLayoutManager lmnotif = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvnotif.setLayoutManager(lmnotif);*/

        /*dbHelper = new DataHelper(getContext());
        text1 = (EditText) view.findViewById(R.id.editText1);
        text2 = (EditText) view.findViewById(R.id.editText2);
        text3 = (EditText) view.findViewById(R.id.editText3);
        ton1 = (Button) view.findViewById(R.id.button1);

        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("insert into notif(no, title, message) values('" +
                        text1.getText().toString() + "','" +
                        text2.getText().toString() + "','" +
                        text3.getText().toString() + "')");
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });*/

        return view;
    }

}