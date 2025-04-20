package org.arslansah.android.events;

import org.arslansah.android.model.Status;

public class StatusMuteChangedEvent{
	public String id;
	public boolean muted;
	public Status status;

	public StatusMuteChangedEvent(Status s){
		id=s.id;
		muted=s.muted;
		status=s;
	}
}
