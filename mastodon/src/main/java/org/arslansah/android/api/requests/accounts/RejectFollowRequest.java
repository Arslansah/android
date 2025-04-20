package org.arslansah.android.api.requests.accounts;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Relationship;

public class RejectFollowRequest extends MastodonAPIRequest<Relationship>{
    public RejectFollowRequest(String id){
        super(HttpMethod.POST, "/follow_requests/"+id+"/reject", Relationship.class);
        setRequestBody(new Object());
    }
}
