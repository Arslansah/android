package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Status;

public class SetStatusPinned extends MastodonAPIRequest<Status>{
	public SetStatusPinned(String id, boolean pinned){
		super(HttpMethod.POST, "/statuses/"+id+"/"+(pinned ? "pin" : "unpin"), Status.class);
		setRequestBody(new Object());
	}
}
