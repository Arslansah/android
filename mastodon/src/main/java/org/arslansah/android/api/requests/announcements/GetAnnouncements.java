package org.arslansah.android.api.requests.announcements;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Announcement;

import java.util.List;

public class GetAnnouncements extends MastodonAPIRequest<List<Announcement>> {
    public GetAnnouncements(boolean withDismissed) {
        super(MastodonAPIRequest.HttpMethod.GET, "/announcements", new TypeToken<>(){});
        addQueryParameter("with_dismissed", withDismissed ? "true" : "false");
    }
}
