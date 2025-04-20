package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Status;
import org.arslansah.android.model.StatusPrivacy;

public class SetStatusReblogged extends MastodonAPIRequest<Status>{
	public SetStatusReblogged(String id, boolean reblogged, StatusPrivacy visibility){
		super(HttpMethod.POST, "/statuses/"+id+"/"+(reblogged ? "reblog" : "unreblog"), Status.class);
		Request req = new Request();
		req.visibility = visibility;
		setRequestBody(req);
	}

	public static class Request {
		public StatusPrivacy visibility;
	}
}
