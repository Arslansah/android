package org.arslansah.android.api.requests.notifications;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Notification;

public class GetNotificationByID extends MastodonAPIRequest<Notification>{
	public GetNotificationByID(String id){
		super(HttpMethod.GET, "/notifications/"+id, Notification.class);
	}
}
