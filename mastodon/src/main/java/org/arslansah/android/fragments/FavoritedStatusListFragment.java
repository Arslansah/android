package org.arslansah.android.fragments;

import android.app.Activity;
import android.net.Uri;

import org.arslansah.android.R;
import org.arslansah.android.api.requests.statuses.GetFavoritedStatuses;
import org.arslansah.android.model.FilterContext;
import org.arslansah.android.model.HeaderPaginationList;
import org.arslansah.android.model.Status;

import me.grishka.appkit.api.SimpleCallback;

public class FavoritedStatusListFragment extends StatusListFragment{
	private String nextMaxID;

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		setTitle(R.string.your_favorites);
		loadData();
	}

	@Override
	protected void doLoadData(int offset, int count){
		currentRequest=new GetFavoritedStatuses(offset==0 ? null : nextMaxID, count)
				.setCallback(new SimpleCallback<>(this){
					@Override
					public void onSuccess(HeaderPaginationList<Status> result){
						if(getActivity()==null) return;
						if(result.nextPageUri!=null)
							nextMaxID=result.nextPageUri.getQueryParameter("max_id");
						else
							nextMaxID=null;
						onDataLoaded(result, nextMaxID!=null);
					}
				})
				.exec(accountID);
	}

	@Override
	protected FilterContext getFilterContext() {
		return FilterContext.ACCOUNT;
	}

	@Override
	public Uri getWebUri(Uri.Builder base) {
		return base.encodedPath(isInstanceAkkoma()
				? '/' + getSession().self.username + "#favorites"
				: "/favourites").build();
	}
}
