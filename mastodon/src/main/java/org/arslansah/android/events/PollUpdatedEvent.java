package org.arslansah.android.events;

import org.arslansah.android.model.Poll;

public class PollUpdatedEvent{
	public String accountID;
	public Poll poll;

	public PollUpdatedEvent(String accountID, Poll poll){
		this.accountID=accountID;
		this.poll=poll;
	}
}
