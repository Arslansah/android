package org.arslansah.android.fragments.account_list;

import android.os.Bundle;

import org.arslansah.android.R;
import org.arslansah.android.api.requests.accounts.SearchAccounts;
import org.arslansah.android.model.Account;

import java.util.List;

import me.grishka.appkit.api.SimpleCallback;

public class AddListMembersFragment extends AccountSearchFragment{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		dataLoaded();
	}

	@Override
	protected void doLoadData(int offset, int count){
		refreshing=true;
		currentRequest=new SearchAccounts(currentQuery, 0, 0, false, true)
				.setCallback(new SimpleCallback<>(this){
					@Override
					public void onSuccess(List<Account> result){
						AddListMembersFragment.this.onSuccess(result);
					}
				})
				.exec(accountID);
	}

	@Override
	protected String getSearchViewPlaceholder(){
		return getString(R.string.search_among_people_you_follow);
	}
}
