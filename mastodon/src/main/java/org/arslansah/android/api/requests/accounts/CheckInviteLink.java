package org.arslansah.android.api.requests.accounts;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.api.RequiredField;
import org.arslansah.android.model.BaseModel;

public class CheckInviteLink extends MastodonAPIRequest<CheckInviteLink.Response>{
	public CheckInviteLink(String path){
		super(HttpMethod.GET, path, Response.class);
		addHeader("Accept", "application/json");
	}

	@Override
	protected String getPathPrefix(){
		return "";
	}

	public static class Response extends BaseModel{
		@RequiredField
		public String inviteCode;
	}
}
