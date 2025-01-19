package com.mpopoaei.compsci_app.client.models;
public class Response {
    private String request;
    private boolean responseSuccess;
    private String message;

    public Response(String request, boolean responseSuccess, String message) {
        this.request = request;
        this.responseSuccess = responseSuccess;
        this.message = message;
    }

    public String getRequest() {
        return request;
    }

    public boolean isResponseSuccess() {
        return responseSuccess;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "request='" + request + '\'' +
                ", responseSuccess=" + responseSuccess +
                ", message='" + message + '\'' +
                '}';
    }
}