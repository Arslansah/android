package org.arslansah.android.api.requests.accounts;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.requests.HeaderPaginationRequest;
import org.arslansah.android.model.Account;

public class GetAccountMutes extends HeaderPaginationRequest<Account>{
	public GetAccountMutes(String maxID, int limit){
		super(HttpMethod.GET, "/mutes/", new TypeToken<>(){});
		if(maxID!=null)
			addQueryParameter("max_id", maxID);
		if(limit>0)
			addQueryParameter("limit", limit+"");
	}
}
