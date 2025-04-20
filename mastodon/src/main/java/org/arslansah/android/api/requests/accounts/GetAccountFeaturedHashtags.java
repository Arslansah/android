package org.arslansah.android.api.requests.accounts;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Hashtag;

import java.util.List;

public class GetAccountFeaturedHashtags extends MastodonAPIRequest<List<Hashtag>>{
	public GetAccountFeaturedHashtags(String id){
		super(HttpMethod.GET, "/accounts/"+id+"/featured_tags", new TypeToken<>(){});
	}
}
