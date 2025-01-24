package com.mpopoaei.compsci_app.client;

import android.util.Log;
import android.view.PixelCopy;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mpopoaei.compsci_app.client.models.Response;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.mpopoaei.compsci_app.client.models.requests.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


// TODO: Create rest of methods for client endpoint 
public class ClientEndpoint implements Runnable{
    private WebSocket webSocket;
    private final Gson gson = new Gson();
    private final String SERVER_ADDRESS = "192.168.0.17";

    @Override
    public void run() {
        createSocket();
    }

    private void createSocket(){
        try {
            this.webSocket = new WebSocketFactory()
                    .createSocket(String.format("ws://%s:8080", SERVER_ADDRESS));
            this.webSocket.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket ws, String message) {
                    Log.d("client-debug", message);
                    Response response = gson.fromJson(message, Response.class);
                    RequestType type = RequestType.valueOf(response.getRequest().strip());
                    if (type.equals(RequestType.REGISTER)){
                        Log.d("client-debug", "Register response detected");
                        ResponseHandler.getInstance().addResponse(RequestType.REGISTER, response);
                    } else if(type.equals(RequestType.LOGIN)){
                        Log.d("client-debug", "Login response detected");
                        ResponseHandler.getInstance().addResponse(RequestType.LOGIN, response);
                    } else if (type.equals(RequestType.GET_FRIEND_LIST)){
                        Log.d("client-debug", "Get friend list response detected");
                        ResponseHandler.getInstance().addResponse(RequestType.GET_FRIEND_LIST, response);
                    }

                }});
            this.webSocket.connect();
        } catch (WebSocketException | IOException e) {
            throw new RuntimeException(e);
        }
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
    public Response sendRegister(String username, String password){
        RegisterRequest request = new RegisterRequest(username, password);
        request.sendRequest(this.webSocket, request);

        Thread thread = getThread(RequestType.REGISTER);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(RequestType.REGISTER);
        Log.d("client-debug", response.toString());
        return response;
    }

    public Response sendLoginRequest(String username, String password){
        LoginRequest request = new LoginRequest(username, password);
        request.sendRequest(this.webSocket, request);

        Thread thread = getThread(RequestType.LOGIN);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(RequestType.LOGIN);
        Log.d("client-debug", response.toString());
        return response;
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
    
    public Response sendGetFriendList(){
        ServerRequest request = new ServerRequest(RequestType.GET_FRIEND_LIST);
        request.sendRequest(this.webSocket, request);

        Thread thread = getThread(RequestType.GET_FRIEND_LIST);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(RequestType.GET_FRIEND_LIST);
        Log.d("client-debug", response.toString());
        return response;
    }

    @NonNull
    private static Thread getThread(RequestType requestType) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (!ResponseHandler.getInstance().hasResponse(requestType)) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }
}
