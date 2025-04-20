package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.api.RequiredField;
import org.arslansah.android.model.BaseModel;
import org.arslansah.android.model.ContentType;

public class GetStatusSourceText extends MastodonAPIRequest<GetStatusSourceText.Response>{
	public GetStatusSourceText(String id){
		super(HttpMethod.GET, "/statuses/"+id+"/source", Response.class);
	}

	public static class Response extends BaseModel{
		@RequiredField
		public String id;
		@RequiredField
		public String text;
		@RequiredField
		public String spoilerText;
		public ContentType contentType;
	}
}
