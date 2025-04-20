package org.arslansah.android.api.requests.accounts;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.FollowList;

import java.util.List;

public class GetAccountLists extends MastodonAPIRequest<List<FollowList>>{
	public GetAccountLists(String id){
		super(HttpMethod.GET, "/accounts/"+id+"/lists", new TypeToken<>(){});
	}
}
