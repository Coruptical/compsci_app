package com.mpopoaei.compsci_app.client;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.mpopoaei.compsci_app.client.models.Message;
import com.mpopoaei.compsci_app.client.models.Response;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.mpopoaei.compsci_app.client.models.requests.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The client endpoint for this application
 * This class handles all outgoing requests, as well as incoming requests from the server.
 * It is designed to be ran in a separate thread, so that it doesn't interfere with the other aspects of the app.
 */
public class ClientEndpoint implements Runnable{
    private WebSocket webSocket;
    private final Gson gson = new Gson();
    private final String SERVER_ADDRESS = "10.14.32.106";

    /*
    Override for the runnable class.
     */
    @Override
    public void run() {
        createSocket();
    }

    /*
    Creates the WebSocket connection to the server,
    and establishes the listener that handles incoming responses.
    It will detect a response and call the response handler to deal with it accordingly.
     */
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
                    } else if(type.equals(RequestType.USER_ADD_FRIEND)){
                        Log.d("client-debug", "Add friend response detected");
                        ResponseHandler.getInstance().addResponse(RequestType.USER_ADD_FRIEND, response);
                    } else if (type.equals(RequestType.GET_FRIEND_REQUESTS)){
                        Log.d("client-debug", "Get friend requests response detected");
                        ResponseHandler.getInstance().addResponse(RequestType.GET_FRIEND_REQUESTS, response);
                    } else if (type.equals(RequestType.USER_ACCEPT_REQUEST)){
                        Log.d("client-debug", "User accept response detected");
                        ResponseHandler.getInstance().addResponse(RequestType.USER_ACCEPT_REQUEST, response);
                    } else if (type.equals(RequestType.USER_DENY_REQUEST)){
                        Log.d("client-debug", "User deny response detected");
                        ResponseHandler.getInstance().addResponse(RequestType.USER_DENY_REQUEST, response);
                    } else if(type.equals(RequestType.USER_REMOVE_FRIEND)){
                        Log.d("client-debug", "User remove friend response detected");
                        ResponseHandler.getInstance().addResponse(RequestType.USER_REMOVE_FRIEND, response);
                    } else if (type.equals(RequestType.USER_MESSAGE)){
                        Log.d("client-debug", "User message response detected");
                        ResponseHandler.getInstance().addResponse(RequestType.USER_MESSAGE, response);
                    } else if (type.equals(RequestType.GET_MESSAGES)){
                        Log.d("client-debug", "User get message response detected");
                        ResponseHandler.getInstance().addResponse(RequestType.GET_MESSAGES, response);
                    } else if (type.equals(RequestType.INCOMING_MESSAGE)){
                        Log.d("client-debug", String.format("Incoming message detected: %s", response));
                        handleIncomingMessage(response);
                    }
                }});
            this.webSocket.connect();
        } catch (WebSocketException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Requests to send to server

    /*
    Attempts to send a register request to the server, given the username and password.
    Waits for the response and returns it.
     */
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

    /*
    Attempts to send a login request to the server, given the username and password.
    Waits for the response and returns it.
     */
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

    /*
    Attempts to send a message request to the server, given the message and target username.
    Waits for the response and returns it.
     */
    public Response sendUserMessage(String message, String targetUsername){
        UserMessageRequest request = new UserMessageRequest(message, targetUsername);
        request.sendRequest(this.webSocket, request);

        Thread thread = getThread(RequestType.USER_MESSAGE);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(RequestType.USER_MESSAGE);
        Log.d("client-debug", response.toString());
        return response;
    }

    /*
    Attempts to send a friend request to another user, given the target username.
    Waits for the response and returns it.
     */
    public Response sendUserAddFriend(String targetUsername){
        UserAddFriendRequest request = new UserAddFriendRequest(targetUsername);
        request.sendRequest(this.webSocket, request);

        Thread thread = getThread(RequestType.USER_ADD_FRIEND);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(RequestType.USER_ADD_FRIEND);
        Log.d("client-debug", response.toString());
        return response;
    }

    /*
    Attempts to send a user remove friend request to the server, given the target username.
    Waits for the response and returns it.
     */
    public Response sendUserRemoveFriend(String targetUsername){
        UserRemoveFriendRequest request = new UserRemoveFriendRequest(targetUsername);
        request.sendRequest(this.webSocket, request);

        Thread thread = getThread(RequestType.USER_REMOVE_FRIEND);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(RequestType.USER_REMOVE_FRIEND);
        Log.d("client-debug", response.toString());
        return response;
    }


    /*
    Attempts to accept an incoming friend request, given the target username.
    Waits for the response and returns it.
     */
    public Response sendUserAcceptFriend(String targetUsername){
        UserAcceptRequest request = new UserAcceptRequest(targetUsername);
        request.sendRequest(this.webSocket, request);

        Thread thread = getThread(RequestType.USER_ACCEPT_REQUEST);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(RequestType.USER_ACCEPT_REQUEST);
        Log.d("client-debug", response.toString());
        return response;
    }

    /*
    Attempts to deny an incoming friend request, given the target username.
    Waits for the response and returns it.
     */
    public Response sendUserDenyFriend(String targetUsername){
        UserDenyRequest request = new UserDenyRequest(targetUsername);
        request.sendRequest(this.webSocket, request);

        Thread thread = getThread(RequestType.USER_DENY_REQUEST);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(RequestType.USER_DENY_REQUEST);
        Log.d("client-debug", response.toString());
        return response;
    }


    /*
    Attempts to retrieve the friend list associated to this WebSocket connection.
    Waits for the response and returns it.
     */
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

    /*
    Attempts to retrieve a list of incoming friend requests associated to this client's user.
    Waits for the response and returns it.
     */
    public Response sendGetFriendRequests(){
        RequestType type = RequestType.GET_FRIEND_REQUESTS;
        ServerRequest request = new ServerRequest(type);
        request.sendRequest(this.webSocket, request);

        Thread thread = getThread(type);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(type);
        Log.d("client-debug", response.toString());
        return response;
    }

    /*
    Attempts to retrieve all of the messages on the specific page, associated to this client's user.
    Waits for the response and returns it.
     */
    public Response sendGetMessages(String friendName, int page){
        GetMessagesRequest request = new GetMessagesRequest(friendName, page);
        request.sendRequest(this.webSocket, request);

        Thread thread = getThread(RequestType.GET_MESSAGES);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Response response = ResponseHandler.getInstance().consumeResponse(RequestType.GET_MESSAGES);
        Log.d("client-debug", response.toString());
        return response;
    }

    /*
    Attempts to handle an incoming message response from the server.
    It gets the username of the client's currently open chat (if not null)
    If the incoming message username is the same as the username from the server, it updates the UI on the user's screen.
     */
    private void handleIncomingMessage(Response response){
        Message message = gson.fromJson(response.getMessage(), Message.class);
        Log.d("client-debug", message.toString());

        String targetUsername = message.getUsername();
        String activeChatUsername = ClientHandler.getInstance().getActiveChatUsername();
        if (activeChatUsername == null){
            return;
        }

        if (targetUsername.equals(activeChatUsername)){
            ClientHandler.getInstance().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Message> messages = ClientHandler.getInstance().getChatMessages();
                    ClientHandler.getInstance().getChatMessages().add(message);
                    ClientHandler.getInstance().getChatMessagesAdapter().notifyItemInserted(messages.size() - 1);
                    ClientHandler.getInstance().getChatRecyclerView().smoothScrollToPosition(messages.size() - 1);
                    Log.d("client-debug", "Performed UI update in chat activity");
                }
            });
        }
    }

    /*
    Utility method for all requests.
    Runs until the response handler has the given server response of the given the request type, then returns it.
     */
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
