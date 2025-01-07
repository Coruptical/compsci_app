package com.mpopoaei.compsci_app.client.models.requests;

public class UserAddFriendRequest extends ServerRequest{
    private String targetUsername;

    public UserAddFriendRequest(String targetUsername) {
        super(RequestType.USER_ADD_FRIEND);
        this.targetUsername = targetUsername;
    }
}
