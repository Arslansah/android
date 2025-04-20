package org.arslansah.android.api.requests.timelines;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Status;

import java.util.List;

public class GetListTimeline extends MastodonAPIRequest<List<Status>> {
    public GetListTimeline(String listID, String maxID, String minID, int limit, String sinceID, String replyVisibility) {
        super(HttpMethod.GET, "/timelines/list/"+listID, new TypeToken<>(){});
        if(maxID!=null)
            addQueryParameter("max_id", maxID);
        if(minID!=null)
            addQueryParameter("min_id", minID);
        if(limit>0)
            addQueryParameter("limit", ""+limit);
        if(sinceID!=null)
            addQueryParameter("since_id", sinceID);
        if(replyVisibility != null)
            addQueryParameter("reply_visibility", replyVisibility);
    }
}
