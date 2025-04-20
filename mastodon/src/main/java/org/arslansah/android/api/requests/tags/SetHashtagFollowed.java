package org.arslansah.android.api.requests.tags;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Hashtag;

public class SetHashtagFollowed extends MastodonAPIRequest<Hashtag>{
    public SetHashtagFollowed(String name, boolean followed){
        super(HttpMethod.POST, "/tags/"+name+"/"+(followed ? "follow" : "unfollow"), Hashtag.class);
        setRequestBody(new Object());
    }
}
