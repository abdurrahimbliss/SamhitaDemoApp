package com.figsandolives.samhitademoapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mSendButton;
    private EditText mInputBox;
    private ListView mListView;
    private MyAdapter myAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesReference;

    private ChildEventListener mChildEventListener;

    private List<UserMessage> mMessages = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFirebase();

        mSendButton = (Button) findViewById(R.id.send_button);
        mInputBox = (EditText) findViewById(R.id.chat_box);
        mListView = (ListView) findViewById(R.id.list);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = mInputBox.getText().toString();

                if (!TextUtils.isEmpty(input)) {
                    UserMessage userMessage = new UserMessage(input, "Anonymous");
                    mMessagesReference.push().setValue(userMessage);
                    mInputBox.setText("");
                }


            }
        });

        ArrayList<UserMessage> items = new ArrayList<>();

        myAdapter = new MyAdapter(MainActivity.this, items);
        mListView.setAdapter(myAdapter);
  }

    private void initFirebase() {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesReference = mFirebaseDatabase.getReference("messages");
        attachChildEventListener();

    }

    private void attachChildEventListener () {

        if (mChildEventListener == null) {

            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    UserMessage userMessage = dataSnapshot.getValue(UserMessage.class);
                    myAdapter.add(userMessage);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mMessagesReference.addChildEventListener(mChildEventListener);
        }

    }

    private class MyAdapter extends ArrayAdapter<UserMessage> {

        MyAdapter(Context context, List<UserMessage> userMessageList) {
            super(context, 0, userMessageList);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

           if (convertView == null) {
               convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.messagelistlayout, null, false);
           }

        TextView messageView = (TextView) convertView.findViewById(R.id.usermessage);
        TextView userView = (TextView) convertView.findViewById(R.id.user);

        UserMessage userMessage = getItem(position);

        messageView.setText(userMessage.getMessage());
        userView.setText(userMessage.getSender());

        return convertView;
        }
    }

}
