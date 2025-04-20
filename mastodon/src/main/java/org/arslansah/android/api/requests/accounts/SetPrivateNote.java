package org.arslansah.android.api.requests.accounts;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Relationship;

public class SetPrivateNote extends MastodonAPIRequest<Relationship>{
    public SetPrivateNote(String id, String comment){
        super(MastodonAPIRequest.HttpMethod.POST, "/accounts/"+id+"/note", Relationship.class);
        Request req = new Request(comment);
        setRequestBody(req);
    }

    private static class Request{
        public String comment;
        public Request(String comment){
            this.comment=comment;
        }
    }
}
