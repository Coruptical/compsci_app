package com.mpopoaei.compsci_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mpopoaei.compsci_app.client.ClientEndpoint;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);

        buttonLogin.setOnClickListener(v -> handleButtonLogin(v, etUsername, etPassword));

        buttonRegister.setOnClickListener(v -> handleButtonRegister(v));
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
            Thread.sleep(1000);
            clientEndpoint.SendDisconnect();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleButtonRegister(View view){

    }
}