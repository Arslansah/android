package org.arslansah.android.api.requests.tags;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Hashtag;

public class GetHashtag extends MastodonAPIRequest<Hashtag> {
    public GetHashtag(String name){
        super(HttpMethod.GET, "/tags/"+name, Hashtag.class);
    }
}

