package com.mpopoaei.compsci_app.client.models.requests;

public class RegisterRequest extends ServerRequest {
    private String username;
    private String password;

    public RegisterRequest(String username, String password) {
        super(RequestType.REGISTER);
        this.username = username;
        this.password = password;
    }
}
