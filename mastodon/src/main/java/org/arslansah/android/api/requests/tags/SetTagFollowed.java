package org.arslansah.android.api.requests.tags;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Hashtag;

public class SetTagFollowed extends MastodonAPIRequest<Hashtag>{
	public SetTagFollowed(String tag, boolean followed){
		super(HttpMethod.POST, "/tags/"+tag+(followed ? "/follow" : "/unfollow"), Hashtag.class);
		setRequestBody(new Object());
	}
}
