package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Status;

public class SetStatusFavorited extends MastodonAPIRequest<Status>{
	public SetStatusFavorited(String id, boolean favorited){
		super(HttpMethod.POST, "/statuses/"+id+"/"+(favorited ? "favourite" : "unfavourite"), Status.class);
		setRequestBody(new Object());
	}
}
