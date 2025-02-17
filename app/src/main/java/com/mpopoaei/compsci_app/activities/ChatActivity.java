package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mpopoaei.compsci_app.R;
import com.mpopoaei.compsci_app.client.ClientHandler;
import com.mpopoaei.compsci_app.client.models.Message;
import com.mpopoaei.compsci_app.client.models.Response;
import com.mpopoaei.compsci_app.utils.ChatMessagesAdapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatMessagesAdapter adapter;
    private ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TextView title = findViewById(R.id.username_title);
        recyclerView = findViewById(R.id.recycler_view_messages);

        String friendName = getIntent().getStringExtra("friendName");

        title.setText(friendName);

        Button buttonSend = findViewById(R.id.send_button);
        buttonSend.setOnClickListener(v -> onSend(v, friendName));

        Button buttonBack = findViewById(R.id.chatBackButton);
        buttonBack.setOnClickListener(v -> onBack(v));

        // Get users messages
        Response response = ClientHandler.getInstance().getClient().sendGetMessages(friendName, 0);
        messages = ClientHandler.getMessagesFromData(response.getMessage());

        String currentUsername = ClientHandler.getInstance().getUsername();

        adapter = new ChatMessagesAdapter(messages, currentUsername);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void onSend(View v, String targetUsername){
        EditText text = findViewById(R.id.message_input);
        String msg = text.getText().toString().strip();

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        Response response = ClientHandler.getInstance().getClient().sendUserMessage(msg, targetUsername);



        messages.add(new Message(ClientHandler.getInstance().getUsername(), msg, timestamp.toString()));
        adapter.notifyItemInserted(messages.size() - 1);
        text.setText("");
    }

    private void onBack(View v){
        Intent intent = new Intent(ChatActivity.this, FriendListActivity.class);
        startActivity(intent);
        this.finish();
    }
}