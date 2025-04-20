package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Translation;

import java.util.Map;

public class TranslateStatus extends MastodonAPIRequest<Translation>{
	public TranslateStatus(String id, String lang){
		super(HttpMethod.POST, "/statuses/"+id+"/translate", Translation.class);
		setRequestBody(Map.of("lang", lang));
	}
}
