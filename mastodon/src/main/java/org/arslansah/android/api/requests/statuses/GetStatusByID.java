package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Status;

public class GetStatusByID extends MastodonAPIRequest<Status>{
	public GetStatusByID(String id){
		super(HttpMethod.GET, "/statuses/"+id, Status.class);
	}
}
