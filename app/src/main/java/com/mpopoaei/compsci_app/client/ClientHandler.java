package com.mpopoaei.compsci_app.client;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.mpopoaei.compsci_app.client.models.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        try {
            JSONArray jsonArray = new JSONArray(response.getMessage());
            for (int i = 0; i< jsonArray.length(); i++){
                friends.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return friends;
    }
}
