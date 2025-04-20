package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.AkkomaTranslation;

public class AkkomaTranslateStatus extends MastodonAPIRequest<AkkomaTranslation>{
	public AkkomaTranslateStatus(String id, String lang){
		super(HttpMethod.GET, "/statuses/"+id+"/translations/"+lang.toLowerCase(), AkkomaTranslation.class);
	}
}
