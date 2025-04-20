package org.arslansah.android.events;

import org.arslansah.android.model.Status;

public class StatusUpdatedEvent{
	public Status status;

	public StatusUpdatedEvent(Status status){
		this.status=status;
	}
}
