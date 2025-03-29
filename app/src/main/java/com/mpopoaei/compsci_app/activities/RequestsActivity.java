package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mpopoaei.compsci_app.R;
import com.mpopoaei.compsci_app.client.ClientHandler;
import com.mpopoaei.compsci_app.client.models.Response;
import com.mpopoaei.compsci_app.utils.FriendRequestAdapter;

import java.util.ArrayList;

/*
The friend requests screen.
 */
public class RequestsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FriendRequestAdapter adapter;
    private ArrayList<String> friendRequests;


    /*
    Gets called when the screen is created.
    The buttons are registered to their methods accordingly.
    Using the friend request adapter, we bind to the recycler view and add our list items dynamically
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        // Back button logic
        Button reqButtonBack = findViewById(R.id.reqBackButton);
        reqButtonBack.setOnClickListener(v -> {
            Intent intent = new Intent(RequestsActivity.this, FriendListActivity.class);
            startActivity(intent);
            this.finish();
        });


        Button addFriendButton = findViewById(R.id.reqAddFrBut);
        addFriendButton.setOnClickListener(v -> {
            Intent intent = new Intent(RequestsActivity.this, AddFriendActivity.class);
            startActivity(intent);
            this.finish();
        });

        // Visuals
        friendRequests = ClientHandler.getInstance().getFriendRequests();

        recyclerView = findViewById(R.id.recycler_view_friend_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FriendRequestAdapter(friendRequests, new FriendRequestAdapter.OnFriendRequestActionListener() {
            @Override
            public void onAccept(String friendName) {
                Response response = ClientHandler.getInstance().getClient().sendUserAcceptFriend(friendName);

                if (response.isResponseSuccess()){
                    Toast.makeText(RequestsActivity.this, friendName + " added as a friend.", Toast.LENGTH_SHORT).show();
                    removeFriendRequest(friendName);
                } else {
                    Toast.makeText(RequestsActivity.this, "An error occurred, please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDecline(String friendName) {
                Response response = ClientHandler.getInstance().getClient().sendUserDenyFriend(friendName);

                if (response.isResponseSuccess()){
                    Toast.makeText(RequestsActivity.this, friendName + "'s request has been declined.", Toast.LENGTH_SHORT).show();
                    removeFriendRequest(friendName);
                } else {
                    Toast.makeText(RequestsActivity.this, "An error occurred, please try again.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        recyclerView.setAdapter(adapter);
    }

    /*
    Updates the list visually to remove the item.
     */
    private void removeFriendRequest(String friendName) {
        friendRequests.remove(friendName);
        adapter.notifyDataSetChanged();
    }
}