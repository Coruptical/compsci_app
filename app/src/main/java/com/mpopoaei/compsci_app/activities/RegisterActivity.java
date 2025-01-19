package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mpopoaei.compsci_app.R;
import com.mpopoaei.compsci_app.client.ClientHandler;
import com.mpopoaei.compsci_app.client.models.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button buttonToLogin = findViewById(R.id.tologinbutton);
        Button buttonRegister = findViewById(R.id.regRegisterButton);

        buttonToLogin.setOnClickListener(v -> onButtonLoginClick(v));
        buttonRegister.setOnClickListener(v -> onButtonRegisterClick(v));

    }

    private void onButtonLoginClick(View v){
        Log.d("client-debug", "To login button pressed");
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
    // TODO: Password standards for user to input
    // TODO: Create timeout for responses, so app doesn't crash
    private void onButtonRegisterClick(View v){
        EditText etUsername = findViewById(R.id.logUsername);
        EditText etPassword = findViewById(R.id.logPassword);

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        Log.d("client-debug", username + " " + password);

        Response response = ClientHandler.getInstance().getClient().sendRegister(username, password);
        showResponse(response.isResponseSuccess(), response.getMessage());
    }

    private void showResponse(boolean success, String response_message){
        TextView regResponseText = findViewById(R.id.regResponseText);
        if (success){
            regResponseText.setText("Successfully registered. Go back to login screen to login.");
            regResponseText.setTextColor(Color.parseColor("#0F9D58"));
        } else {
            if (response_message.equalsIgnoreCase("UNKNOWN_FAILED")){
                regResponseText.setText("Failed due to unknown. Please try again.");
            } else if (response_message.equalsIgnoreCase("USERNAME_EXISTS")){
                regResponseText.setText("This username already exists. Please try another.");
            }
            regResponseText.setTextColor(Color.parseColor("#FF0000"));
        }
        regResponseText.setVisibility(View.VISIBLE);
    }

    private void hideResponse(){
        TextView regResponseText = findViewById(R.id.regResponseText);
        regResponseText.setVisibility(View.GONE);
    }


}
