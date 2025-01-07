package com.mpopoaei.compsci_app.client.models.requests;

public class LoginRequest extends ServerRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password){
        super(RequestType.LOGIN);
        this.username = username;
        this.password = password;
    }
}
