package org.arslansah.android.api.requests.announcements;

import org.arslansah.android.api.MastodonAPIRequest;

public class DismissAnnouncement extends MastodonAPIRequest<Object>{
	public DismissAnnouncement(String id){
		super(HttpMethod.POST, "/announcements/" + id + "/dismiss", Object.class);
		setRequestBody(new Object());
	}
}
