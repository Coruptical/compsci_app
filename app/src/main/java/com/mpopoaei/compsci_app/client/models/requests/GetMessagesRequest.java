package com.mpopoaei.compsci_app.client.models.requests;

public class GetMessagesRequest extends ServerRequest{
    private String friendName;
    private int page;

    public GetMessagesRequest(String friendName, int page){
        super(RequestType.GET_MESSAGES);
        this.friendName = friendName;
        this.page = page;
    }
}
