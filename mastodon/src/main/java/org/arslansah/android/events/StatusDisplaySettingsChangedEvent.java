package org.arslansah.android.events;

public class StatusDisplaySettingsChangedEvent{
	public final String accountID;

	public StatusDisplaySettingsChangedEvent(String accountID){
		this.accountID=accountID;
	}
}
