package org.arslansah.android.api.requests.lists;

import org.arslansah.android.api.ResultlessMastodonAPIRequest;

public class DeleteList extends ResultlessMastodonAPIRequest{
	public DeleteList(String id){
		super(HttpMethod.DELETE, "/lists/"+id);
	}
}
