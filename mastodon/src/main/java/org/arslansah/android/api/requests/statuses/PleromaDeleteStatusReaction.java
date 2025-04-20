package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Status;

public class PleromaDeleteStatusReaction extends MastodonAPIRequest<Status> {
    public PleromaDeleteStatusReaction(String id, String emoji) {
        super(HttpMethod.DELETE, "/pleroma/statuses/" + id + "/reactions/" + emoji, Status.class);
    }
}
