package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Status;

public class AddStatusReaction extends MastodonAPIRequest<Status> {
	public AddStatusReaction(String id, String emoji) {
		super(HttpMethod.POST, "/statuses/" + id + "/react/" + emoji, Status.class);
		setRequestBody(new Object());
	}
}
