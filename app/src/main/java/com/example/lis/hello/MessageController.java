package com.example.lis.hello;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MessageController extends RecyclerView.Adapter {

    public List<Message> messageList = new ArrayList<>();

    private static final int USER_MESSAGE = 0;
    private static final int ASSISTANT_MESSAGE = 1;

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        return message.is_sent ? USER_MESSAGE : ASSISTANT_MESSAGE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(i == USER_MESSAGE ? R.layout.user_message: R.layout.assistant_message,viewGroup,false);
        return new MessageView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int message_number) {
        Message message = messageList.get(message_number);
        ((MessageView)viewHolder).bind(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
