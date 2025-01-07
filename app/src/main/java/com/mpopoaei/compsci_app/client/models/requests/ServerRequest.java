package com.mpopoaei.compsci_app.client.models.requests;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;

public class ServerRequest {
    private RequestType requestType;

    public ServerRequest(RequestType requestType){
        this.requestType = requestType;
    }

    public void sendRequest(WebSocket webSocket, Object src){
        Gson gson = new Gson();
        webSocket.sendText(gson.toJson(src));
    }
}
