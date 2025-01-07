package com.mpopoaei.compsci_app.client.models.requests;

public class UserRemoveFriendRequest extends ServerRequest{
    private String targetUsername;

    public UserRemoveFriendRequest(String userUUID) {
        super(RequestType.USER_REMOVE_FRIEND);
        this.targetUsername = userUUID;
    }
}
