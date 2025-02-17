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

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {
    private final ArrayList<String> friendRequests;
    private final OnFriendRequestActionListener listener;

    public interface OnFriendRequestActionListener {
        void onAccept(String friendName);
        void onDecline(String friendName);
    }

    public FriendRequestAdapter(ArrayList<String> friendRequests, OnFriendRequestActionListener listener) {
        this.friendRequests = friendRequests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_request_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String friendName = friendRequests.get(position);
        holder.nameTextView.setText(friendName);
        holder.acceptButton.setOnClickListener(v -> listener.onAccept(friendName));
        holder.declineButton.setOnClickListener(v -> listener.onDecline(friendName));
    }

    @Override
    public int getItemCount() {
        return friendRequests.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        Button acceptButton, declineButton;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.friend_name);
            acceptButton = itemView.findViewById(R.id.accept_button);
            declineButton = itemView.findViewById(R.id.decline_button);
        }
    }


}
