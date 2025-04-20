package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Status;

public class SetStatusMuted extends MastodonAPIRequest<Status>{
	public SetStatusMuted(String id, boolean muted){
		super(HttpMethod.POST, "/statuses/"+id+"/"+(muted ? "mute" : "unmute"), Status.class);
		setRequestBody(new Object());
	}
}
