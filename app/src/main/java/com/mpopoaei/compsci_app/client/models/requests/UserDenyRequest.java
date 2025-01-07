package com.mpopoaei.compsci_app.client.models.requests;

public class UserDenyRequest extends ServerRequest{
    private String targetUsername;

    public UserDenyRequest(String targetUsername) {
        super(RequestType.USER_DENY_REQUEST);
        this.targetUsername = targetUsername;
    }
}
