package org.arslansah.android.api.requests.notifications;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Notification;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PleromaMarkNotificationsRead extends MastodonAPIRequest<List<Notification>> {
    private final String maxID;
    public PleromaMarkNotificationsRead(String maxID) {
        super(HttpMethod.POST, "/pleroma/notifications/read", new TypeToken<>(){});
        this.maxID = maxID;
    }

    @Override
    public RequestBody getRequestBody() {
        MultipartBody.Builder builder=new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if(!TextUtils.isEmpty(maxID))
            builder.addFormDataPart("max_id", maxID);
        return builder.build();
    }
}
