package org.arslansah.android.api.requests.instance;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.WeeklyActivity;

import java.util.List;

public class GetWeeklyActivity extends MastodonAPIRequest<List<WeeklyActivity>>{
	public GetWeeklyActivity(){
		super(HttpMethod.GET, "/instance/activity", new TypeToken<>(){});
	}

}
