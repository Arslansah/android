package org.arslansah.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;

import org.arslansah.android.R;
import org.arslansah.android.api.requests.timelines.GetPublicTimeline;
import org.arslansah.android.api.session.AccountSessionManager;
import org.arslansah.android.model.FilterContext;
import org.arslansah.android.model.Status;
import org.arslansah.android.model.TimelineDefinition;
import org.arslansah.android.ui.utils.UiUtils;
import org.arslansah.android.utils.ProvidesAssistContent;

import java.util.List;

import me.grishka.appkit.api.SimpleCallback;

public class CustomLocalTimelineFragment extends PinnableStatusListFragment implements ProvidesAssistContent.ProvidesWebUri{
    //    private String name;
    private String domain;

    private String maxID;
    @Override
    protected boolean wantsComposeButton() {
        return false;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        domain=getArguments().getString("domain");
        updateTitle(domain);

        setHasOptionsMenu(true);
    }

    private void updateTitle(String domain) {
        this.domain = domain;
        setTitle(this.domain);
    }

    @Override
    protected void doLoadData(int offset, int count){
        currentRequest=new GetPublicTimeline(true, false, refreshing ? null : maxID, null, count, null, getLocalPrefs().timelineReplyVisibility)
                .setCallback(new SimpleCallback<>(this){
                    @Override
                    public void onSuccess(List<Status> result){
                        if(!result.isEmpty())
                            maxID=result.get(result.size()-1).id;
                        if (getActivity() == null) return;
						AccountSessionManager.get(accountID).filterStatuses(result, FilterContext.PUBLIC);
                        result.stream().forEach(status -> {
                            status.account.acct += "@"+domain;
                            status.mentions.forEach(mention -> mention.id = null);
                            status.isRemote = true;
                        });

                        onDataLoaded(result, !result.isEmpty());
                    }
                })
                .execNoAuth(domain);
    }

    @Override
    protected void onShown(){
        super.onShown();
        if(!getArguments().getBoolean("noAutoLoad") && !loaded && !dataLoading)
            loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.custom_local_timelines, menu);
        super.onCreateOptionsMenu(menu, inflater);
        UiUtils.enableOptionsMenuIcons(getContext(), menu, R.id.pin);
    }

    @Override
    protected FilterContext getFilterContext() {
        return FilterContext.PUBLIC;
    }

    @Override
    public Uri getWebUri(Uri.Builder base) {
		return new Uri.Builder()
				.scheme("https")
				.authority(domain)
				.build();
    }

    @Override
    protected TimelineDefinition makeTimelineDefinition() {
        return TimelineDefinition.ofCustomLocalTimeline(domain);
    }
}
