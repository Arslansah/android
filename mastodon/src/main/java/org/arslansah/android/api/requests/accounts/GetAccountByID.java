package org.arslansah.android.api.requests.accounts;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Account;

public class GetAccountByID extends MastodonAPIRequest<Account>{
	public GetAccountByID(String id){
		super(HttpMethod.GET, "/accounts/"+id, Account.class);
	}
}
