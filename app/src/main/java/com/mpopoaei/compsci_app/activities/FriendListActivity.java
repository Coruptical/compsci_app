package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mpopoaei.compsci_app.R;
import com.mpopoaei.compsci_app.client.ClientHandler;
import com.mpopoaei.compsci_app.client.models.Response;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        // TODO: Display friends
        // TODO: Add friend interactions
        Button requestsButton = findViewById(R.id.buttonRequests);
        requestsButton.setOnClickListener(v -> handleButtonRequests(v));


        // TODO: Add friends to layout
        ArrayList<String> response = ClientHandler.getInstance().getFriends();
        Log.d("client-debug", response.toString());


    }

    private void handleButtonRequests(View v){
        Intent intent = new Intent(FriendListActivity.this, RequestsActivity.class);
        startActivity(intent);
        this.finish();
    }
}