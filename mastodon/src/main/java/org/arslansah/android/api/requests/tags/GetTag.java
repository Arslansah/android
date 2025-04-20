package org.arslansah.android.api.requests.tags;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Hashtag;

public class GetTag extends MastodonAPIRequest<Hashtag>{
	public GetTag(String tag){
		super(HttpMethod.GET, "/tags/"+tag, Hashtag.class);
	}
}
