package com.mpopoaei.compsci_app.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mpopoaei.compsci_app.client.models.Response;

import java.util.ArrayList;
import java.util.Arrays;

public final class ClientHandler {
    private boolean isAuthenticated = false;
    private static ClientEndpoint client;
    private boolean isRunning = false;

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

        friends = (ArrayList<String>)
                Arrays.asList(new Gson().fromJson(response.getMessage(), String[].class));
        return friends;
    }
}
