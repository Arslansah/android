package org.arslansah.android.api.requests.accounts;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Preferences;

public class GetPreferences extends MastodonAPIRequest<Preferences> {
    public GetPreferences(){
        super(HttpMethod.GET, "/preferences", Preferences.class);
    }
}
