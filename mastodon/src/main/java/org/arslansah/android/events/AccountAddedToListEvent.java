package org.arslansah.android.events;

import org.arslansah.android.model.Account;

public class AccountAddedToListEvent{
	public final String accountID;
	public final String listID;
	public final Account account;

	public AccountAddedToListEvent(String accountID, String listID, Account account){
		this.accountID=accountID;
		this.listID=listID;
		this.account=account;
	}
}
