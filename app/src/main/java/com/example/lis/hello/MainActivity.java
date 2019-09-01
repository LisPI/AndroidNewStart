package com.example.lis.hello;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Locale;

//b6da771ca9034d0b94a70813190105

public class MainActivity extends AppCompatActivity {

    protected Button sendButton;
    protected ImageView voiceButton;
    protected RecyclerView chatWindow;
    protected EditText userMessage;
    protected MessageController messageController;
    protected TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        userMessage = findViewById(R.id.userMessage);
        chatWindow = findViewById(R.id.chatWindow);
        voiceButton = findViewById(R.id.buttonMic);

        messageController = new MessageController();

        chatWindow.setLayoutManager(new LinearLayoutManager(this));

        chatWindow.setAdapter(messageController);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(new Locale("ru"));
                tts.speak(getString(R.string.init_message), TextToSpeech.QUEUE_ADD, null, null);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener();
            }
        });
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceRecognition();
            }
        });

        messageController.messageList.add(new Message(getString(R.string.init_message),false));
        messageController.notifyDataSetChanged();
        chatWindow.scrollToPosition(messageController.messageList.size() - 1);
    }

    protected void voiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, new Locale("ru"));
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null) {
            userMessage.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
            onClickListener();
        }
    }

    protected void onClickListener(){
        String message = userMessage.getText().toString();

        userMessage.setText("");

        messageController.messageList.add(new Message(message,true));
        AI.getAnswer(message, new MyConsumer() {
                    @Override
                    public void myAccept(String answer) {
                        messageController.messageList.add(new Message(answer,false));
                        tts.speak(answer, TextToSpeech.QUEUE_ADD, null, null);
                        messageController.notifyDataSetChanged();
                        chatWindow.scrollToPosition(messageController.messageList.size() - 1);
                    }
                });









    }
}
