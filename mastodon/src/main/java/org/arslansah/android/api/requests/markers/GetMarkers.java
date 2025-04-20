package org.arslansah.android.api.requests.markers;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.TimelineMarkers;

public class GetMarkers extends MastodonAPIRequest<TimelineMarkers>{
	public GetMarkers(){
		super(HttpMethod.GET, "/markers", TimelineMarkers.class);
		addQueryParameter("timeline[]", "home");
		addQueryParameter("timeline[]", "notifications");
	}
}
