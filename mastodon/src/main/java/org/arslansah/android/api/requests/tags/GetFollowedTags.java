package org.arslansah.android.api.requests.tags;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.requests.HeaderPaginationRequest;
import org.arslansah.android.model.Hashtag;

public class GetFollowedTags extends HeaderPaginationRequest<Hashtag>{
	public GetFollowedTags(String maxID, int limit){
		super(HttpMethod.GET, "/followed_tags", new TypeToken<>(){});
		if(maxID!=null)
			addQueryParameter("max_id", maxID);
		if(limit>0)
			addQueryParameter("limit", limit+"");
	}
}
