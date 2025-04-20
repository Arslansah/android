package org.arslansah.android.api.requests.lists;

import org.arslansah.android.api.MastodonAPIRequest;

public class AddList extends MastodonAPIRequest<Object> {
    public AddList(String listName){
        super(HttpMethod.POST, "/lists", Object.class);
        Request req = new Request();
        req.title = listName;
        setRequestBody(req);
    }

    public static class Request{
        public String title;
    }
}
