package com.example.appdispatcher.ui.support;

import android.content.Intent;
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

import com.example.appdispatcher.Adapter.ChatAdapter;
import com.example.appdispatcher.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readMessage(firebaseUser.getUid(), "moderator");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }

    private void sendMessage(String sender, String receiver, String message, String id_engineer) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("id_engineer", id_engineer);

        reference.child("Chats").push().setValue(hashMap);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("from", "engineer");
        hashMap2.put("message", message);
        hashMap2.put("time", timestamp.getTime() / 1000);

//        Log.i("firebase chat", "job_support/" + id_support + "/chat");
        reference.child("job_support/" + id_support + "/chat").push().setValue(hashMap2);
    }

    private void readMessage(final String userid, final String moderator) {
        mChat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        Log.i("isi userid", userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatViewModel chat = snapshot.getValue(ChatViewModel.class);
                    if (chat.getSender().equals(userid) || chat.getReceiver().equals(userid)) {
                        mChat.add(chat);
                    }

                    chatAdapter = new ChatAdapter(getContext(), mChat);
                    recyclerView.setAdapter(chatAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}