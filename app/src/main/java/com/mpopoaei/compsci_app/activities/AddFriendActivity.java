package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mpopoaei.compsci_app.R;
import com.mpopoaei.compsci_app.client.ClientHandler;
import com.mpopoaei.compsci_app.client.models.Response;

/*
Add a friend screen
 */
public class AddFriendActivity extends AppCompatActivity {

    /*
    Gets called when the activity (screen) is created.
    Sets up the buttons on the screen and registers their click listeners for functionality.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        Button backButton = findViewById(R.id.addFrBackBut);
        Button sendRequestButton = findViewById(R.id.addFrSendBut);

        backButton.setOnClickListener(v -> backIntent(v));
        sendRequestButton.setOnClickListener(v -> handleSendClick(v));
    }

    /*
    An intent for the back button, to take us back to the previous screen.
     */
    private void backIntent(View v){
        Intent intent = new Intent(AddFriendActivity.this, RequestsActivity.class);
        startActivity(intent);
        this.finish();
    }

    /*
    Attempts to send an add friend request to the server with the username that the user typed on screen.
     */
    private void handleSendClick(View v){
        EditText etUsername = findViewById(R.id.addFrUsername);
        String targetUsername = etUsername.getText().toString().strip();

        Response response = ClientHandler.getInstance().getClient().sendUserAddFriend(targetUsername);
        showResponse(response.isResponseSuccess(), response.getMessage(), targetUsername);
        etUsername.setText("");
    }

    /*
    An on-screen visual of whether the request went through, or given errors accordingly.
     */
    private void showResponse(boolean success, String response_message, String targetUsername){
        TextView frAddResponseText = findViewById(R.id.addFrResponseText);
        if (success){
            frAddResponseText.setText(String.format("Sent %s a friend request.", targetUsername));
            frAddResponseText.setTextColor(Color.parseColor("#0F9D58"));
        } else {
            if (response_message.equalsIgnoreCase("USERNAME_NOT_EXISTS")){
                frAddResponseText.setText("This user does not exist. Please try again.");
            } else if (response_message.equalsIgnoreCase("FRIEND_CONNECTION_EXISTS")){
                frAddResponseText.setText("You are already friends with this user.");
            } else if (response_message.equalsIgnoreCase("FRIEND_REQUEST_EXISTS")){
                frAddResponseText.setText("This user already has an outgoing request.");
            }
            frAddResponseText.setTextColor(Color.parseColor("#FF0000"));
        }
        frAddResponseText.setVisibility(View.VISIBLE);
    }
}