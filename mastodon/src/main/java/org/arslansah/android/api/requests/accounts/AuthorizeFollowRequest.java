package org.arslansah.android.api.requests.accounts;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Relationship;

public class AuthorizeFollowRequest extends MastodonAPIRequest<Relationship>{
    public AuthorizeFollowRequest(String id){
        super(HttpMethod.POST, "/follow_requests/"+id+"/authorize", Relationship.class);
        setRequestBody(new Object());
    }
}
