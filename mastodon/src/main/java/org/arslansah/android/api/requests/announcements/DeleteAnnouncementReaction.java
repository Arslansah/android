package org.arslansah.android.api.requests.announcements;

import org.arslansah.android.api.MastodonAPIRequest;

public class DeleteAnnouncementReaction extends MastodonAPIRequest<Object> {
	public DeleteAnnouncementReaction(String id, String emoji) {
		super(HttpMethod.DELETE, "/announcements/" + id + "/reactions/" + emoji, Object.class);
	}
}
