package com.mpopoaei.compsci_app.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mpopoaei.compsci_app.R;
import com.mpopoaei.compsci_app.client.models.Message;

import java.util.List;

/*
The chat messages adapter.
This allows us to dynamically work with our chat screen,
and is basically the way that we create a chat screen.
 */
public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.MessageViewHolder> {

    private static final int VIEW_TYPE_RIGHT = 1; // User message
    private static final int VIEW_TYPE_LEFT = 2; // Friend message

    private List<Message> messages;
    private String username;

    public ChatMessagesAdapter(List<Message> messages, String username) {
        this.messages = messages;
        this.username = username;
    }

    /*
    Compare the message username to the current user's username.
    This determines which side the message goes on based on the sender.
     */
    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);

        if (message.getUsername().equals(username)) {
            return VIEW_TYPE_RIGHT;
        } else {
            return VIEW_TYPE_LEFT;
        }
    }

    /*
    Creates a new ViewHolder for chat messages.
    This inflates (adds to) the layout based on the message type.
     */
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_RIGHT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_right,
                    parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_left,
                    parent, false);
        }
        return new MessageViewHolder(view);
    }

    /*
    Binds the message data to the corresponding TextView on the screen.
     */
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.messageTextView.setText(message.getBody());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    /*
    ViewHolder class to hold message views.
    Each view represents a single message.
     */
    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message_text);
        }
    }
}
