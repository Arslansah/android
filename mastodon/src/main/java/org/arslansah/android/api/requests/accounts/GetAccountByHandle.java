package org.arslansah.android.api.requests.accounts;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Account;

public class GetAccountByHandle extends MastodonAPIRequest<Account>{
    /**
     * note that this method usually only returns a result if the instance already knows about an
     * account - so it makes sense for looking up local users, search might be preferred otherwise
     */
    public GetAccountByHandle(String acct){
        super(HttpMethod.GET, "/accounts/lookup", Account.class);
        addQueryParameter("acct", acct);
    }
}
