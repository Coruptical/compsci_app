package com.mpopoaei.compsci_app.client;

import com.mpopoaei.compsci_app.client.models.Response;
import com.mpopoaei.compsci_app.client.models.requests.RequestType;

import java.util.HashMap;

public class ResponseHandler {
    private final HashMap<RequestType, Response> responseMap = new HashMap<>();

    private static final ResponseHandler instance = new ResponseHandler();

    public static ResponseHandler getInstance() {
        return instance;
    }

    private ResponseHandler(){}

    public boolean hasResponse(RequestType key){
        return responseMap.containsKey(key);
    }

    public void addResponse(RequestType requestType, Response response){
        responseMap.put(requestType, response);
    }

    public Response consumeResponse(RequestType requestType){
        Response response = responseMap.get(requestType);
        responseMap.remove(requestType);
        return response;
    }
}
