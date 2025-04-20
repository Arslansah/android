package org.arslansah.android.api.requests.accounts;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Account;

public class GetOwnAccount extends MastodonAPIRequest<Account>{
	public GetOwnAccount(){
		super(HttpMethod.GET, "/accounts/verify_credentials", Account.class);
	}
}
