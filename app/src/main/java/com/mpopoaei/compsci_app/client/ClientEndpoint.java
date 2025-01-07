package com.mpopoaei.compsci_app.client;

import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.mpopoaei.compsci_app.client.models.requests.*;

import java.io.IOException;


// TODO: Create rest of methods for client endpoint 
public class ClientEndpoint implements Runnable{
    private WebSocket webSocket;

    @Override
    public void run() {
        createSocket();
    }


    public void SendDisconnect(){
        DisconnectRequest request = new DisconnectRequest("");
        request.sendRequest(this.webSocket, request);
        this.webSocket.disconnect();
    }

    public void SendDisconnect(String reason){
        DisconnectRequest request = new DisconnectRequest(reason);
        request.sendRequest(this.webSocket, request);
        this.webSocket.disconnect();
    }

    // Requests to send to server
    public void sendRegister(String username, String password){
        RegisterRequest request = new RegisterRequest(username, password);
        request.sendRequest(this.webSocket, request);
    }

    public void sendLoginRequest(String username, String password){
        LoginRequest request = new LoginRequest(username, password);
        request.sendRequest(this.webSocket, request);
    }

    public void sendUserMessage(String message, String targetUsername){
        UserMessageRequest request = new UserMessageRequest(message, targetUsername);
        request.sendRequest(this.webSocket, request);
    }

    public void sendUserAddFriend(String targetUsername){
        UserAddFriendRequest request = new UserAddFriendRequest(targetUsername);
        request.sendRequest(this.webSocket, request);
    }

    public void sendUserRemoveFriend(String targetUsername){
        UserRemoveFriendRequest request = new UserRemoveFriendRequest(targetUsername);
        request.sendRequest(this.webSocket, request);
    }

    public void sendUserAcceptFriend(String targetUsername){
        UserAcceptRequest request = new UserAcceptRequest(targetUsername);
        request.sendRequest(this.webSocket, request);
    }

    public void sendUserDenyFriend(String targetUsername){
        UserDenyRequest request = new UserDenyRequest(targetUsername);
        request.sendRequest(this.webSocket, request);
    }

    public void sendMessage(String msg){
        this.webSocket.sendText(msg);
    }

    private void createSocket(){
        try {
            this.webSocket = new WebSocketFactory()
                    .createSocket("ws://130.162.182.203:8080");
            this.webSocket.addListener(new WebSocketAdapter() {
            @Override
            public void onTextMessage(WebSocket ws, String message) {
                Log.d("server-response", message);
            }});
            this.webSocket.connect();
        } catch (WebSocketException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
