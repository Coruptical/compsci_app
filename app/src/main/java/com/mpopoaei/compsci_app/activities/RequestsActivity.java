package com.mpopoaei.compsci_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mpopoaei.compsci_app.R;

public class RequestsActivity extends AppCompatActivity {

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

        // Visuals

        ScrollView scrollView = findViewById(R.id.reqScrollView);
        LinearLayout layout = (LinearLayout) scrollView.getChildAt(0);

        // Add friend requests to layout
    }
}