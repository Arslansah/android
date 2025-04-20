package org.arslansah.android.events;

import org.arslansah.android.model.FollowList;

public class ListCreatedEvent{
	public final String accountID;
	public final FollowList list;

	public ListCreatedEvent(String accountID, FollowList list){
		this.accountID=accountID;
		this.list=list;
	}
}
