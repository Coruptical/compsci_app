package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mpopoaei.compsci_app.R;
import com.mpopoaei.compsci_app.client.ClientHandler;
import com.mpopoaei.compsci_app.client.models.Response;
import com.mpopoaei.compsci_app.utils.FriendListAdapter;

import java.util.ArrayList;

/*
The friend list screen.
On creation, this screen displays all of the user's friends in a list, and gives them the option to chat or remove them.
 */
public class FriendListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FriendListAdapter adapter;
    private ArrayList<String> friends;

    /*
    Called on creation of this activity.
    Registers all of the buttons and what their clicks.
    Binds an adapter to the friend list recycler view, so that we can dynamically work with it.
    This allows us to then add our interactive friend item to the friend list.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Button requestsButton = findViewById(R.id.buttonRequests);
        requestsButton.setOnClickListener(v -> handleButtonRequests(v));


        friends = ClientHandler.getInstance().getFriends();
        Log.d("client-debug", friends.toString());

        recyclerView = findViewById(R.id.recycler_view_friend_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FriendListAdapter(friends, new FriendListAdapter.onFriendListActionListener() {
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

    /*
    Handles the button to take the user to the friend requests page.
     */
    private void handleButtonRequests(View v){
        Intent intent = new Intent(FriendListActivity.this, RequestsActivity.class);
        startActivity(intent);
        this.finish();
    }

    /*
    Updates the adapter so the friend gets visually removed from the list.
     */
    private void removeFriendFromList(String friendName) {
        friends.remove(friendName);
        adapter.notifyDataSetChanged();
    }
}