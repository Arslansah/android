package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.StatusContext;

public class GetStatusContext extends MastodonAPIRequest<StatusContext>{
	public GetStatusContext(String id){
		super(HttpMethod.GET, "/statuses/"+id+"/context", StatusContext.class);
	}
}
