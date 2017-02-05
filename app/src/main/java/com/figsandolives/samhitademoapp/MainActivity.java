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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mSendButton;
    private EditText mInputBox;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSendButton = (Button) findViewById(R.id.send_button);
        mInputBox = (EditText) findViewById(R.id.chat_box);
        mListView = (ListView) findViewById(R.id.list);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = mInputBox.getText().toString();

                if (!TextUtils.isEmpty(input)) {

                    Toast.makeText(MainActivity.this, input, Toast.LENGTH_SHORT).show();

                    mInputBox.setText("");
                }


            }
        });

        ArrayList<UserMessage> items = new ArrayList<>();

        UserMessage userMessage = new UserMessage("Samhita", "Sam");
        UserMessage userMessage1 = new UserMessage("Samhita", "Sam");
        UserMessage userMessage2 = new UserMessage("Samhita", "Sam");

        items.add(userMessage);
        items.add(userMessage1);
        items.add(userMessage2);

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, items);
        mListView.setAdapter(myAdapter);
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
