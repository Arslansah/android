package org.arslansah.android.events;

import org.arslansah.android.model.Status;

public class StatusCreatedEvent{
	public final Status status;
	public final String accountID;

	public StatusCreatedEvent(Status status, String accountID){
		this.status=status;
		this.accountID=accountID;
		status.fromStatusCreated=true;
	}
}
