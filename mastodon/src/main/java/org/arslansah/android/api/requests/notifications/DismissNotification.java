package org.arslansah.android.api.requests.notifications;

import org.arslansah.android.api.MastodonAPIRequest;

public class DismissNotification extends MastodonAPIRequest<Object>{
    public DismissNotification(String id){
        super(HttpMethod.POST, "/notifications/" + (id != null ? id + "/dismiss" : "clear"), Object.class);
        setRequestBody(new Object());
    }
}
