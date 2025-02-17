package com.mpopoaei.compsci_app.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mpopoaei.compsci_app.R;

import java.util.ArrayList;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private final ArrayList<String> friends;
    private final onFriendListActionListener listener;

    public interface onFriendListActionListener {
        void onChat(String friendName);
        void onRemove(String friendName);
    }

    public FriendListAdapter(ArrayList<String> friends, onFriendListActionListener listener) {
        this.friends = friends;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String friendName = friends.get(position);
        holder.nameTextView.setText(friendName);
        holder.chatButton.setOnClickListener(v -> listener.onChat(friendName));
        holder.removeButton.setOnClickListener(v -> listener.onRemove(friendName));
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        Button chatButton, removeButton;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.friend_name);
            chatButton = itemView.findViewById(R.id.accept_button);
            removeButton = itemView.findViewById(R.id.decline_button);
        }
    }


}
