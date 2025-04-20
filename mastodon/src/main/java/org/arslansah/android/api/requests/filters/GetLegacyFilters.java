package org.arslansah.android.api.requests.filters;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.LegacyFilter;

import java.util.List;

public class GetLegacyFilters extends MastodonAPIRequest<List<LegacyFilter>>{
	public GetLegacyFilters(){
		super(HttpMethod.GET, "/filters", new TypeToken<>(){});
	}
}
