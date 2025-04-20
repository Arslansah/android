package org.arslansah.android.api.requests.lists;

import org.arslansah.android.api.MastodonAPIRequest;

public class EditListName extends MastodonAPIRequest<Object> {
    public EditListName(String newListName, String listId){
        super(HttpMethod.PUT, "/lists/"+listId, Object.class);
        Request req = new Request();
        req.title = newListName;
        setRequestBody(req);
    }

    public static class Request{
        public String title;
    }
}
