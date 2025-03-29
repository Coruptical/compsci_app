package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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

/*
The chat screen.
This dynamically changes when it's created based on the friend the user clicked in the friends list screen.
 */
public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatMessagesAdapter adapter;
    private ArrayList<Message> messages;

    /*
    Gets called when the activity (screen) is created.
    The screen is set up with the friend username at the top.
    Messages are retrieved by the client and should appear on the left/right sides of the chat.
    The user can type in the text box and send a message as required.
     */
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

        if (!messages.isEmpty()){
            recyclerView.smoothScrollToPosition(messages.size() - 1);
        }

        // Set the handler of the ClientHandler class so that it can access this class's context
        Handler handler = new Handler(this.getMainLooper());

        ClientHandler.getInstance().setHandler(handler);
        ClientHandler.getInstance().setActiveChatUsername(friendName);
        ClientHandler.getInstance().setChatMessagesAdapter(this.adapter);
        ClientHandler.getInstance().setChatMessages(this.messages);
        ClientHandler.getInstance().setChatRecyclerView(this.recyclerView);
    }

    /*
    Attempts to send a message over to the friend
    Sends a user message request over to the server, then scrolls to the bottom of the chat.
     */
    private void onSend(View v, String targetUsername){
        EditText text = findViewById(R.id.message_input);
        String msg = text.getText().toString().strip();

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        Response response = ClientHandler.getInstance().getClient().sendUserMessage(msg, targetUsername);

        messages.add(new Message(ClientHandler.getInstance().getUsername(), msg, timestamp.toString()));
        adapter.notifyItemInserted(messages.size() - 1);
        recyclerView.smoothScrollToPosition(messages.size() - 1);
        text.setText("");
    }

    /*
    Intent to go back to the friend list screen.
    Also clears the active chat, so that incoming messages from the server are handled properly.
     */
    private void onBack(View v){
        ClientHandler.getInstance().setActiveChatUsername(null);

        Intent intent = new Intent(ChatActivity.this, FriendListActivity.class);
        startActivity(intent);
        this.finish();
    }
}