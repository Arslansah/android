package org.arslansah.android.api.requests.lists;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.FollowList;

public class GetList extends MastodonAPIRequest<FollowList> {
	public GetList(String id) {
		super(HttpMethod.GET, "/lists/" + id, FollowList.class);
	}
}
