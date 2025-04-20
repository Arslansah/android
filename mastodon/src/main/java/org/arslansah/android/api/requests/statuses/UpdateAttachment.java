package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Attachment;

public class UpdateAttachment extends MastodonAPIRequest<Attachment>{
	public UpdateAttachment(String id, String description){
		super(HttpMethod.PUT, "/media/"+id, Attachment.class);
		setRequestBody(new Body(description));
	}

	private static class Body{
		public String description;

		public Body(String description){
			this.description=description;
		}
	}
}
