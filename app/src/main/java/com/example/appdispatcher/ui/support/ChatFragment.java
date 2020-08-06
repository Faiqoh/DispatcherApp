package com.example.appdispatcher.ui.support;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.appdispatcher.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChatFragment extends Fragment {

    ImageButton btn_send;
    EditText et_send;

    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        et_send = view.findViewById(R.id.text_send);
        btn_send = view.findViewById(R.id.btn_send);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getActivity().getIntent();
        final String userId = (String) intent.getSerializableExtra("userid");

        Bundle extras = getActivity().getIntent().getExtras();
        final String id_engineer = extras.getString("id_engineer");
//        Log.i("id_engineer", id_engineer);

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
    }
}