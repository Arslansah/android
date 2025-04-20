package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Status;

public class DeleteStatusReaction extends MastodonAPIRequest<Status> {
    public DeleteStatusReaction(String id, String emoji) {
        super(HttpMethod.POST, "/statuses/" + id + "/unreact/" + emoji, Status.class);
		setRequestBody(new Object());
    }
}
