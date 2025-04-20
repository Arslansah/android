package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Status;

public class PleromaAddStatusReaction extends MastodonAPIRequest<Status> {
    public PleromaAddStatusReaction(String id, String emoji) {
        super(HttpMethod.PUT, "/pleroma/statuses/" + id + "/reactions/" + emoji, Status.class);
		setRequestBody(new Object());
    }
}
