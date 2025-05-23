package org.arslansah.android.api.requests.lists;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.FollowList;

import java.util.List;

public class GetLists extends MastodonAPIRequest<List<FollowList>>{
    public GetLists() {
        super(HttpMethod.GET, "/lists", new TypeToken<>(){});
    }
    public GetLists(String accountID) {
        super(HttpMethod.GET, "/accounts/"+accountID+"/lists", new TypeToken<>(){});
    }
}
