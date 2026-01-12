package com.example.exploresafely;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatbotActivity extends AppCompatActivity {

    // Declare UI components
    private ListView chatListView;
    private EditText userInput;
    private Button sendButton;
    private ArrayAdapter<String> chatAdapter;
    private ArrayList<String> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        // Initialize UI components
        chatListView = findViewById(R.id.chatListView);
        userInput = findViewById(R.id.userInput);
        sendButton = findViewById(R.id.sendButton);

        chatMessages = new ArrayList<>();
        chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        chatListView.setAdapter(chatAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = userInput.getText().toString().trim();
                if (!userMessage.isEmpty()) {
                    chatMessages.add("You: " + userMessage);

                    // Call the chatbot and get a reply
                    String botReply = Chatbot.getReply(userMessage);

                    chatMessages.add("Bot: " + botReply);
                    chatAdapter.notifyDataSetChanged();
                    userInput.setText("");
                }
            }
        });
    }
}
