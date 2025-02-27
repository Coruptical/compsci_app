package com.mpopoaei.compsci_app.client;

import android.os.Handler;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.mpopoaei.compsci_app.client.models.Message;
import com.mpopoaei.compsci_app.client.models.Response;
import com.mpopoaei.compsci_app.utils.ChatMessagesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class ClientHandler {
    private boolean isAuthenticated = false;
    private static ClientEndpoint client;
    private boolean isRunning = false;
    private String username = "";

    private ChatMessagesAdapter chatMessagesAdapter;
    private ArrayList<Message> chatMessages;
    private String activeChatUsername = null;
    private Handler handler;
    private RecyclerView chatRecyclerView;

    public RecyclerView getChatRecyclerView() {
        return chatRecyclerView;
    }

    public void setChatRecyclerView(RecyclerView chatRecyclerView) {
        this.chatRecyclerView = chatRecyclerView;
    }

    public ChatMessagesAdapter getChatMessagesAdapter() {
        return chatMessagesAdapter;
    }

    public void setChatMessagesAdapter(ChatMessagesAdapter chatMessagesAdapter) {
        this.chatMessagesAdapter = chatMessagesAdapter;
    }

    public ArrayList<Message> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<Message> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getActiveChatUsername() {
        return activeChatUsername;
    }

    public void setActiveChatUsername(String activeChatUsername) {
        this.activeChatUsername = activeChatUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private ArrayList<JsonObject> serverResponses = new ArrayList<>();

    public ArrayList<JsonObject> getServerResponses() {
        return serverResponses;
    }

    private ClientHandler(){

    }

    private static ClientHandler instance = new ClientHandler();

    public static ClientHandler getInstance() {
        return instance;
    }

    public ClientEndpoint getClient() {
        return client;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public void init(){
        if (!isRunning){
            start();
            isRunning = true;
        }
    }

    private void start(){
        client = new ClientEndpoint();
        Thread thread = new Thread(getClient());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Client methods
    public ArrayList<String> getFriends(){
        ArrayList<String> friends = new ArrayList<>();
        if (!isAuthenticated) {
            return friends;
        }

        Response response = client.sendGetFriendList();
        if (!response.isResponseSuccess()) return friends;

        friends = getStringListFromJson(response.getMessage());

        return friends;
    }

    public ArrayList<String> getFriendRequests(){
        ArrayList<String> requests = new ArrayList<>();

        if(!isAuthenticated){
            return requests;
        }

        Response response = client.sendGetFriendRequests();
        if(!response.isResponseSuccess()) return requests;

        requests = getStringListFromJson(response.getMessage());

        return requests;
    }

    // Utility methods
    private ArrayList<String> getStringListFromJson(String data){
        ArrayList<String> list = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i< jsonArray.length(); i++){
                list.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static ArrayList<Message> getMessagesFromData(String data){
        ArrayList<Message> list = new ArrayList<>();

        Gson gson = new Gson();
        Log.d("client-debug", data);
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i< jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Message message = gson.fromJson(object.toString(), Message.class);
                list.add(message);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
