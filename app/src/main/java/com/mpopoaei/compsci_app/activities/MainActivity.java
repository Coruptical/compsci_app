package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mpopoaei.compsci_app.R;
import com.mpopoaei.compsci_app.client.ClientEndpoint;

public class MainActivity extends AppCompatActivity {
    private static ClientEndpoint client;

    public static ClientEndpoint getClient() {
        return client;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        EditText etUsername = findViewById(R.id.regUsername);
        EditText etPassword = findViewById(R.id.regPassword);

        buttonLogin.setOnClickListener(v -> handleButtonLogin(v, etUsername, etPassword));

        buttonRegister.setOnClickListener(v -> handleButtonRegister(v));

        client = new ClientEndpoint();
    }

    public void handleButtonLogin(View view, EditText etUsername, EditText etPassword){
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        // Connect to oracle cloud server WebSocket and try login
        try {
            ClientEndpoint clientEndpoint = new ClientEndpoint();
            Thread t = new Thread(clientEndpoint);
            t.start();
            t.join();
            Log.d("client-connection", "Successfully connected client endpoint");
            // Send login request with input username + pass
            clientEndpoint.sendLoginRequest(username, password);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleButtonRegister(View view){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        this.finish();
    }
}