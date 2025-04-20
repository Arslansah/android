package org.arslansah.android.api.requests.statuses;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.requests.HeaderPaginationRequest;
import org.arslansah.android.model.Account;

public class GetStatusReblogs extends HeaderPaginationRequest<Account>{
	public GetStatusReblogs(String id, String maxID, int limit){
		super(HttpMethod.GET, "/statuses/"+id+"/reblogged_by", new TypeToken<>(){});
		if(maxID!=null)
			addQueryParameter("max_id", maxID);
		if(limit>0)
			addQueryParameter("limit", limit+"");
	}
}
