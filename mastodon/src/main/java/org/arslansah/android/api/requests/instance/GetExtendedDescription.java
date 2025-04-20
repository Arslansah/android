package org.arslansah.android.api.requests.instance;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.ExtendedDescription;

public class GetExtendedDescription extends MastodonAPIRequest<ExtendedDescription>{
	public GetExtendedDescription(){
		super(HttpMethod.GET, "/instance/extended_description", ExtendedDescription.class);
	}

}
