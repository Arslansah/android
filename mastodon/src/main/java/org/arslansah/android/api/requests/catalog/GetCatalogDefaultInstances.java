package org.arslansah.android.api.requests.catalog;

import android.net.Uri;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.catalog.CatalogDefaultInstance;

import java.util.List;

public class GetCatalogDefaultInstances extends MastodonAPIRequest<List<CatalogDefaultInstance>>{
	public GetCatalogDefaultInstances(){
		super(HttpMethod.GET, null, new TypeToken<>(){});
		setTimeout(500);
	}

	@Override
	public Uri getURL(){
		return Uri.parse("https://api.joinmastodon.org/default-servers");
	}
}
