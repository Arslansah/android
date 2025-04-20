package org.arslansah.android.fragments;

import android.net.Uri;
import android.os.Bundle;

import org.arslansah.android.R;
import org.arslansah.android.api.requests.accounts.GetAccountStatuses;
import org.arslansah.android.api.session.AccountSessionManager;
import org.arslansah.android.model.Account;
import org.arslansah.android.model.FilterContext;
import org.arslansah.android.model.Status;
import org.parceler.Parcels;

import java.util.List;

import me.grishka.appkit.api.SimpleCallback;

public class PinnedPostsListFragment extends StatusListFragment{
	private Account account;

	public PinnedPostsListFragment() {
		setListLayoutId(R.layout.recycler_fragment_no_refresh);
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		account=Parcels.unwrap(getArguments().getParcelable("profileAccount"));
		setTitle(R.string.posts);
		loadData();
	}

	@Override
	protected void doLoadData(int offset, int count){
		new GetAccountStatuses(account.id, null, null, 100, GetAccountStatuses.Filter.PINNED)
				.setCallback(new SimpleCallback<>(this){
					@Override
					public void onSuccess(List<Status> result){
						if(getActivity()==null) return;
						AccountSessionManager.get(accountID).filterStatuses(result, getFilterContext());
						onDataLoaded(result, false);
					}
				}).exec(accountID);
	}

	@Override
	protected FilterContext getFilterContext() {
		return FilterContext.ACCOUNT;
	}

	@Override
	public Uri getWebUri(Uri.Builder base) {
		return Uri.parse(account.url);
	}
}
