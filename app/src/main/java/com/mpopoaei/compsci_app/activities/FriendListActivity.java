package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mpopoaei.compsci_app.R;
import com.mpopoaei.compsci_app.client.ClientHandler;
import com.mpopoaei.compsci_app.client.models.Response;
import com.mpopoaei.compsci_app.utils.FriendListAdapter;
import com.mpopoaei.compsci_app.utils.FriendRequestAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FriendListAdapter adapter;
    private ArrayList<String> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        // TODO: Add friend interactions
        Button requestsButton = findViewById(R.id.buttonRequests);
        requestsButton.setOnClickListener(v -> handleButtonRequests(v));


        friends = ClientHandler.getInstance().getFriends();
        Log.d("client-debug", friends.toString());

        recyclerView = findViewById(R.id.recycler_view_friend_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FriendListAdapter(friends, new FriendListAdapter.onFriendListActionListener() {
            // TODO: Send requests to server
            @Override
            public void onChat(String friendName) {
                Intent intent = new Intent(FriendListActivity.this, ChatActivity.class);
                intent.putExtra("friendName", friendName);
                startActivity(intent);
            }

            @Override
            public void onRemove(String friendName) {
                Response response = ClientHandler.getInstance().getClient().sendUserRemoveFriend(friendName);

                if (response.isResponseSuccess()){
                    removeFriendFromList(friendName);
                } else{
                    Toast.makeText(FriendListActivity.this, "An error occurred, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void handleButtonRequests(View v){
        Intent intent = new Intent(FriendListActivity.this, RequestsActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void removeFriendFromList(String friendName) {
        friends.remove(friendName);
        adapter.notifyDataSetChanged();
    }
}