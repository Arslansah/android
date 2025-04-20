package org.arslansah.android.api.requests.accounts;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.FollowSuggestion;

import java.util.List;

public class GetFollowSuggestions extends MastodonAPIRequest<List<FollowSuggestion>>{
	public GetFollowSuggestions(int limit){
		super(HttpMethod.GET, "/suggestions", new TypeToken<>(){});
		addQueryParameter("limit", limit+"");
	}

	@Override
	protected String getPathPrefix(){
		return "/api/v2";
	}
}
