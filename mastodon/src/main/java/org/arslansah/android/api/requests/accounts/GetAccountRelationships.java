package org.arslansah.android.api.requests.accounts;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Relationship;

import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;

public class GetAccountRelationships extends MastodonAPIRequest<List<Relationship>>{
	public GetAccountRelationships(@NonNull Collection<String> ids){
		super(HttpMethod.GET, "/accounts/relationships", new TypeToken<>(){});
		for(String id:ids)
			addQueryParameter("id[]", id);
	}
}
