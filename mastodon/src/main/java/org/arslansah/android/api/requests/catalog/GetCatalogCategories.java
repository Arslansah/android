package org.arslansah.android.api.requests.catalog;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.catalog.CatalogCategory;

import java.util.List;

public class GetCatalogCategories extends MastodonAPIRequest<List<CatalogCategory>>{
	private String lang;

	public GetCatalogCategories(String lang){
		super(HttpMethod.GET, null, new TypeToken<>(){});
		this.lang=lang;
	}

	@Override
	public Uri getURL(){
		Uri.Builder builder=new Uri.Builder()
				.scheme("https")
				.authority("api.joinmastodon.org")
				.path("/categories");
		if(!TextUtils.isEmpty(lang))
			builder.appendQueryParameter("language", lang);
		return builder.build();
	}
}
