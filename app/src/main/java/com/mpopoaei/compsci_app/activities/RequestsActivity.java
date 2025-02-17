package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mpopoaei.compsci_app.R;
import com.mpopoaei.compsci_app.client.ClientHandler;
import com.mpopoaei.compsci_app.client.models.Response;
import com.mpopoaei.compsci_app.utils.FriendRequestAdapter;

import java.util.ArrayList;
import java.util.List;

public class RequestsActivity extends AppCompatActivity {

    // Define the classes that I may need to use to display my visuals for friend requests
    private RecyclerView recyclerView;
    private FriendRequestAdapter adapter;
    private ArrayList<String> friendRequests;


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
            // TODO: Send requests to server
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

    private void removeFriendRequest(String friendName) {
        friendRequests.remove(friendName);
        adapter.notifyDataSetChanged();
    }
}