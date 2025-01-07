package com.mpopoaei.compsci_app.client.models.requests;

public class UserMessageRequest extends ServerRequest {
    private String message;
    private String targetUsername;

    public UserMessageRequest(String message, String targetUsername) {
        super(RequestType.USER_MESSAGE);
        this.message = message;
        this.targetUsername = targetUsername;
    }
}
