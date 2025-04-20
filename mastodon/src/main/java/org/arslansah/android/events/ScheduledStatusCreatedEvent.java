package org.arslansah.android.events;

import org.arslansah.android.model.ScheduledStatus;

public class ScheduledStatusCreatedEvent {
	public final ScheduledStatus scheduledStatus;
	public final String accountID;

	public ScheduledStatusCreatedEvent(ScheduledStatus scheduledStatus, String accountID){
		this.scheduledStatus = scheduledStatus;
		this.accountID=accountID;
	}
}
