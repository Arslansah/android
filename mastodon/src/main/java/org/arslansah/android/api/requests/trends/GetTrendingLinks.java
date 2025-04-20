package org.arslansah.android.api.requests.trends;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Card;

import java.util.List;

public class GetTrendingLinks extends MastodonAPIRequest<List<Card>>{
	public GetTrendingLinks(){
		super(HttpMethod.GET, "/trends/links", new TypeToken<>(){});
	}
}
