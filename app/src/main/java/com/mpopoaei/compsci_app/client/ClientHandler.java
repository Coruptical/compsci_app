package com.mpopoaei.compsci_app.client;

public final class ClientHandler {
    private static ClientEndpoint client;
    private boolean isRunning = false;

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
