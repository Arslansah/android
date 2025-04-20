package org.arslansah.android.events;

import org.arslansah.android.updater.GithubSelfUpdater;

public class SelfUpdateStateChangedEvent{
	public final GithubSelfUpdater.UpdateState state;

	public SelfUpdateStateChangedEvent(GithubSelfUpdater.UpdateState state){
		this.state=state;
	}
}
