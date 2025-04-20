package org.arslansah.android.api.requests.statuses;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.EmojiReaction;

import java.util.List;

public class PleromaGetStatusReactions extends MastodonAPIRequest<List<EmojiReaction>> {
    public PleromaGetStatusReactions(String id, String emoji) {
        super(HttpMethod.GET, "/pleroma/statuses/" + id + "/reactions/" + (emoji != null ? emoji : ""), new TypeToken<>(){});
    }
}
