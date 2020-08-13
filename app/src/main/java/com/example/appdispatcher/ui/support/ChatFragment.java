package com.example.appdispatcher.ui.support;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.ChatAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatFragment extends Fragment {

    ImageButton btn_send;
    EditText et_send;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    List<ChatViewModel> mChat;

    String id_support;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        et_send = view.findViewById(R.id.text_send);
        btn_send = view.findViewById(R.id.btn_send);

        recyclerView = view.findViewById(R.id.rv_chat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getActivity().getIntent();

        Bundle extras = getActivity().getIntent().getExtras();
        final String id_engineer = extras.getString("id_engineer");
        id_support = extras.getString("id_support");
        Log.i("id support", id_support);
        final String id_job = extras.getString("id_job");

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = et_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(firebaseUser.getUid(), "moderator", msg, id_engineer);
                } else {
                    Toast.makeText(getContext(), "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                et_send.setText("");
            }
        });

        /*reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readMessage("engineer", "moderator");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        readMessage("engineer", "moderator");

        return view;

    }

    private void sendMessage(String sender, String receiver, String message, String id_engineer) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("id_engineer", id_engineer);

        reference.child("Chats").push().setValue(hashMap);*/

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("from", "engineer");
        hashMap2.put("message", message);
        hashMap2.put("time", timestamp.getTime() / 1000);

//        Log.i("firebase chat", "job_support/" + id_support + "/chat");
        reference.child("job_support/" + id_support + "/chat").push().setValue(hashMap2);
    }

    private void readMessage(final String engineer, final String moderator) {
        mChat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("job_support/" + id_support + "/chat");
        Log.i("isi userid", engineer);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                String checker = "2020-01-01";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    ChatViewModel chat = snapshot.getValue(ChatViewModel.class);
                    java.util.Date time = new java.util.Date((long) snapshot.child("time").getValue(Integer.class) * 1000);
                    String pattern = "d MMMM";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String date = simpleDateFormat.format(time);

                    String time2 = "HH mm";
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(time2);
                    String date3 = simpleDateFormat2.format(time);

                    ChatViewModel chat = new ChatViewModel(
                            snapshot.child("from").getValue(String.class),
                            snapshot.child("time").getValue(Integer.class),
                            snapshot.child("message").getValue(String.class),
                            date3
                    );


                    if (!checker.equals(date)) {
                        checker = date;
                        ChatViewModel chat2 = new ChatViewModel("date", 2020, date, "");
                        mChat.add(chat2);
                    }
                    mChat.add(chat);

                    chatAdapter = new ChatAdapter(getContext(), mChat);
                    recyclerView.setAdapter(chatAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.getdetailsupport_withtoken + "?id_support=" + id_support, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject sup = response.getJSONObject("job_support");
                    if (sup.getString("status").equals("Done")) {
                        et_send.setEnabled(false);
                        btn_send.setEnabled(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Accept", "applicaion/json");
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(StrReq);
    }
}