package com.example.projet.chat;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.projet.R;

public class GeneralChatActivity extends AppCompatActivity {
    private LinearLayout messageContainer;
    private EditText messageInput;
    private Button sendButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generalechat);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        messageContainer = findViewById(R.id.messageContainer);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        cancelButton = findViewById(R.id.cancelButton);

        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                TextView textView = new TextView(this);
                textView.setText(message);
                textView.setPadding(10, 10, 10, 10);
                textView.setBackgroundResource(R.drawable.message_bubble);
                messageContainer.addView(textView);
                messageInput.setText("");
            }
        });

        cancelButton.setOnClickListener(v -> messageInput.setText(""));
    }
}
