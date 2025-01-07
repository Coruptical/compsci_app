package com.mpopoaei.compsci_app.client.models.requests;

public class DisconnectRequest extends ServerRequest{
    private String reason;

    public DisconnectRequest(String reason){
        super(RequestType.DISCONNECT);
        this.reason = reason;
    }
}
