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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        EditText etUsername = findViewById(R.id.logUsername);
        EditText etPassword = findViewById(R.id.logPassword);

        buttonLogin.setOnClickListener(v -> handleButtonLogin(v, etUsername, etPassword));

        buttonRegister.setOnClickListener(v -> handleButtonRegister(v));

        ClientHandler.getInstance().init();

    }

    public void handleButtonLogin(View view, EditText etUsername, EditText etPassword){
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        // Connect to oracle cloud server WebSocket and try login

        Response response = ClientHandler.getInstance().getClient().sendLoginRequest(username, password);
        showResponse(response.isResponseSuccess(), response.getMessage());

        if (response.isResponseSuccess()){
            ClientHandler.getInstance().setAuthenticated(true);
            Intent intent = new Intent(LoginActivity.this, FriendListActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    public void handleButtonRegister(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void showResponse(boolean success, String response_message){
        TextView regResponseText = findViewById(R.id.logResponseText);
        if (success){
            regResponseText.setText("Successfully logged in.");
            regResponseText.setTextColor(Color.parseColor("#0F9D58"));
        } else {
            if (response_message.equalsIgnoreCase("UNKNOWN_FAILED")){
                regResponseText.setText("Failed due to unknown. Please try again.");
            } else if (response_message.equalsIgnoreCase("PASSWORD_INCORRECT")){
                regResponseText.setText("Your password is incorrect. Please try again.");
            } else if (response_message.equalsIgnoreCase("USERNAME_NOT_EXIST")){
                regResponseText.setText("This username does not exist. Make sure it's correct.");
            }
            regResponseText.setTextColor(Color.parseColor("#FF0000"));
        }
        regResponseText.setVisibility(View.VISIBLE);
    }

}