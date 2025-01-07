package com.mpopoaei.compsci_app.client.models.requests;

public class UserAcceptRequest extends ServerRequest{
    private String targetUsername;

    public UserAcceptRequest(String userUUID) {
        super(RequestType.USER_ACCEPT_REQUEST);
        this.targetUsername = userUUID;
    }
}
