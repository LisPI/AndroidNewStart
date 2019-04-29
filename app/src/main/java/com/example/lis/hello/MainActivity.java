package com.example.lis.hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected Button sendButton;
    protected TextView chatWindow;
    protected EditText userMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        userMessage = findViewById(R.id.userMessage);
        chatWindow = findViewById(R.id.chatWindow);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener();
            }
        });
    }

    protected void onClickListener(){
        String message = userMessage.getText().toString();

        userMessage.setText("");

        chatWindow.append("\n" + ">> " + message);
        chatWindow.append("\n" + AI.getAnswer(message));
    }
}
