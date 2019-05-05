package com.example.lis.hello;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//b6da771ca9034d0b94a70813190105
import java.util.Locale;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    protected Button sendButton;
    protected RecyclerView chatWindow;
    protected EditText userMessage;
    protected MessageController messageController;
    protected TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        userMessage = findViewById(R.id.userMessage);
        chatWindow = findViewById(R.id.chatWindow);

        messageController = new MessageController();

        chatWindow.setLayoutManager(new LinearLayoutManager(this));

        chatWindow.setAdapter(messageController);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(new Locale("ru"));
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                onClickListener();
            }
        });
    }

    protected void onClickListener(){
        String message = userMessage.getText().toString();

        userMessage.setText("");

        //chatWindow.append("\n" + ">> " + message);
        //chatWindow.append("\n<< " + AI.getAnswer(message));

        messageController.messageList.add(new Message(message,true));
        AI.getAnswer(message, new MyConsumer() {
                    @Override
                    public void myAccept(String answer) {
                        messageController.messageList.add(new Message(answer,false));
                        tts.speak(answer, TextToSpeech.QUEUE_FLUSH, null, null);
                        messageController.notifyDataSetChanged();
                        chatWindow.scrollToPosition(messageController.messageList.size() - 1);
                    }
                });









    }
}
