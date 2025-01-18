package com.mpopoaei.compsci_app.client;

import android.util.Log;
import android.view.PixelCopy;

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
    public Response sendRegister(String username, String password){
        RegisterRequest request = new RegisterRequest(username, password);
        request.sendRequest(this.webSocket, request);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (!ResponseHandler.getInstance().hasResponse(RequestType.REGISTER)) {
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
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(RequestType.REGISTER);
        Log.d("client-debug", response.toString());
        return response;
    }

    public void sendLoginRequest(String username, String password){
        LoginRequest request = new LoginRequest(username, password);
        request.sendRequest(this.webSocket, request);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (!ResponseHandler.getInstance().hasResponse(RequestType.LOGIN)) {
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
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(RequestType.LOGIN);
        Log.d("client-debug", response.toString());
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
                    .createSocket("ws://10.14.32.106:8080");
            this.webSocket.addListener(new WebSocketAdapter() {
            @Override
            public void onTextMessage(WebSocket ws, String message) {
                Response response = gson.fromJson(message, Response.class);
                Log.d("server-response", response.toString());
                RequestType type = RequestType.valueOf(response.getRequest().strip());
                if (type.equals(RequestType.REGISTER)){
                    Log.d("client-debug", "Register response detected");
                    ResponseHandler.getInstance().addResponse(RequestType.REGISTER, response);
                } else if(type.equals(RequestType.LOGIN)){
                    ResponseHandler.getInstance().addResponse(RequestType.LOGIN, response);
                }
                
            }});
            this.webSocket.connect();
        } catch (WebSocketException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
