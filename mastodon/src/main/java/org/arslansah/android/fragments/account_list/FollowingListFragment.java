package org.arslansah.android.fragments.account_list;

import android.net.Uri;
import android.os.Bundle;

import org.arslansah.android.R;
import org.arslansah.android.api.requests.HeaderPaginationRequest;
import org.arslansah.android.api.requests.accounts.GetAccountFollowing;
import org.arslansah.android.model.Account;

public class FollowingListFragment extends AccountRelatedAccountListFragment{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setSubtitle(initialSubtitle = getResources().getQuantityString(R.plurals.x_following, (int)(account.followingCount%1000), account.followingCount));
	}

	@Override
	public HeaderPaginationRequest<Account> onCreateRequest(String maxID, int count){
		return new GetAccountFollowing(getCurrentInfo().id, maxID, count);
	}

	@Override
	public Uri getWebUri(Uri.Builder base) {
		return super.getWebUri(base).buildUpon()
				.appendPath(isInstanceAkkoma() ? "#followees" : "/following").build();
	}
}
