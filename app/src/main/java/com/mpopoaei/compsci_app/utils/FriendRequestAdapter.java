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

/*
Friend requests adapter.
Used to dynamically work with our friend requests screen,
and is how we populate it with the user's friend requests.
 */
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

    /*
    This inflates (adds to) the layout.
    Creates a new ViewHolder for each list item.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_request_item, parent, false);
        return new ViewHolder(view);
    }

    /*
    Binds the data of the friend request with its buttons to the corresponding ViewHolder.
     */
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

    /*
    ViewHolder class to hold a single friend request item for our list.
     */
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
