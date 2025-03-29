package com.mpopoaei.compsci_app.client.models.requests;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;

/*
Request object to send to the server as a JSON payload.
All requests inherit this class.
 */
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
