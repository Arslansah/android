package org.arslansah.android.api.requests.oauth;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.api.session.AccountSessionManager;
import org.arslansah.android.model.Application;

public class CreateOAuthApp extends MastodonAPIRequest<Application>{
	public CreateOAuthApp(){
		super(HttpMethod.POST, "/apps", Application.class);
		setRequestBody(new Request());
	}

	private static class Request{
		public String clientName="Moshidon";
		public String redirectUris=AccountSessionManager.REDIRECT_URI;
		public String scopes=AccountSessionManager.SCOPE;
		public String website="https://github.com/LucasGGamerM/moshidon";
	}
}
