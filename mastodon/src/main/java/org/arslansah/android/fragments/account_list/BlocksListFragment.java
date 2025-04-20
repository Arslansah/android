package org.arslansah.android.fragments.account_list;

import android.net.Uri;
import android.os.Bundle;

import org.arslansah.android.R;
import org.arslansah.android.api.requests.HeaderPaginationRequest;
import org.arslansah.android.api.requests.accounts.GetAccountBlocks;
import org.arslansah.android.model.Account;
import org.arslansah.android.ui.viewholders.AccountViewHolder;

public class BlocksListFragment extends AccountRelatedAccountListFragment{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTitle(R.string.mo_blocked_accounts);
	}

	@Override
	public HeaderPaginationRequest<Account> onCreateRequest(String maxID, int count){
		return new GetAccountBlocks(maxID, count);
	}

	@Override
	protected void onConfigureViewHolder(AccountViewHolder holder){
		super.onConfigureViewHolder(holder);
		holder.setStyle(AccountViewHolder.AccessoryType.NONE, false);
	}

	@Override
	public Uri getWebUri(Uri.Builder base) {
		return super.getWebUri(base).buildUpon()
				.appendPath("/blocks").build();
	}
}
