package org.arslansah.android.api.requests.instance;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Emoji;

import java.util.List;

public class GetCustomEmojis extends MastodonAPIRequest<List<Emoji>>{
	public GetCustomEmojis(){
		super(HttpMethod.GET, "/custom_emojis", new TypeToken<>(){});
	}
}
