package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Status;

public class DeleteStatus extends MastodonAPIRequest<Status>{
	public DeleteStatus(String id){
		super(HttpMethod.DELETE, "/statuses/"+id, Status.class);
	}

	public static class Scheduled extends MastodonAPIRequest<Object> {
		public Scheduled(String id) {
			super(HttpMethod.DELETE, "/scheduled_statuses/"+id, Object.class);
		}
	}
}
