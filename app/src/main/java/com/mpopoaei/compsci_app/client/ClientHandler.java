package com.mpopoaei.compsci_app.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public final class ClientHandler {
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
}
