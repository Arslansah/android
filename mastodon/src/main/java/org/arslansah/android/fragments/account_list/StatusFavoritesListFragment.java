package org.arslansah.android.fragments.account_list;

import android.net.Uri;
import android.os.Bundle;

import org.arslansah.android.R;
import org.arslansah.android.api.requests.HeaderPaginationRequest;
import org.arslansah.android.api.requests.statuses.GetStatusFavorites;
import org.arslansah.android.model.Account;
import org.arslansah.android.model.Status;

public class StatusFavoritesListFragment extends StatusRelatedAccountListFragment{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		updateTitle(status);
	}

	@Override
	protected void updateTitle(Status status) {
		setTitle(getResources().getQuantityString(R.plurals.x_favorites, (int)(status.favouritesCount%1000), status.favouritesCount));
	}

	@Override
	public HeaderPaginationRequest<Account> onCreateRequest(String maxID, int count){
		return new GetStatusFavorites(getCurrentInfo().id, maxID, count);
	}

	@Override
	public Uri getWebUri(Uri.Builder base) {
		Uri statusUri = super.getWebUri(base);
		return isInstanceAkkoma()
				? statusUri
				: statusUri.buildUpon().appendPath("favourites").build();
	}
}
