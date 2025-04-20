package org.arslansah.android.api.requests.statuses;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.requests.HeaderPaginationRequest;
import org.arslansah.android.model.Status;

public class GetBookmarkedStatuses extends HeaderPaginationRequest<Status>{
	public GetBookmarkedStatuses(String maxID, int limit){
		super(HttpMethod.GET, "/bookmarks", new TypeToken<>(){});
		if(maxID!=null)
			addQueryParameter("max_id", maxID);
		if(limit>0)
			addQueryParameter("limit", limit+"");
	}
}
