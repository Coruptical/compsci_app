package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mpopoaei.compsci_app.R;

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
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void onButtonRegisterClick(View v){
        EditText etUsername = findViewById(R.id.regUsername);
        EditText etPassword = findViewById(R.id.regPassword);

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        Log.d("client-debug", username + " " + password);
    }


}
