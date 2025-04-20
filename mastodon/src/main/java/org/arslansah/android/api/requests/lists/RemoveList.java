package org.arslansah.android.api.requests.lists;

import org.arslansah.android.api.MastodonAPIRequest;

public class RemoveList extends MastodonAPIRequest<Object> {
    public RemoveList(String listId){
        super(HttpMethod.DELETE, "/lists/"+listId, Object.class);
    }
}
