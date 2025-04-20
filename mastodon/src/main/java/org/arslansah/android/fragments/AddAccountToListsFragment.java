package org.arslansah.android.fragments;

import android.os.Bundle;
import android.widget.TextView;

import org.arslansah.android.E;
import org.arslansah.android.R;
import org.arslansah.android.api.ResultlessMastodonAPIRequest;
import org.arslansah.android.api.requests.accounts.GetAccountLists;
import org.arslansah.android.api.requests.lists.AddAccountsToList;
import org.arslansah.android.api.requests.lists.RemoveAccountsFromList;
import org.arslansah.android.api.session.AccountSessionManager;
import org.arslansah.android.events.AccountAddedToListEvent;
import org.arslansah.android.events.AccountRemovedFromListEvent;
import org.arslansah.android.fragments.settings.BaseSettingsFragment;
import org.arslansah.android.model.Account;
import org.arslansah.android.model.FollowList;
import org.arslansah.android.model.viewmodel.CheckableListItem;
import org.arslansah.android.ui.utils.UiUtils;
import org.parceler.Parcels;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import androidx.recyclerview.widget.RecyclerView;
import me.grishka.appkit.api.Callback;
import me.grishka.appkit.api.ErrorResponse;
import me.grishka.appkit.api.SimpleCallback;
import me.grishka.appkit.utils.MergeRecyclerAdapter;
import me.grishka.appkit.utils.SingleViewRecyclerAdapter;
import me.grishka.appkit.utils.V;

public class AddAccountToListsFragment extends BaseSettingsFragment<FollowList>{
	private Account account;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTitle(R.string.add_user_to_list_title);
		account=Parcels.unwrap(getArguments().getParcelable("targetAccount"));
		loadData();
	}

	@Override
	protected void doLoadData(int offset, int count){
		AccountSessionManager.get(accountID).getCacheController().getLists(new SimpleCallback<>(this){
			@Override
			public void onSuccess(List<FollowList> allLists){
				if(getActivity()==null)
					return;
				loadAccountLists(allLists);
			}
		});
	}

	private void loadAccountLists(final List<FollowList> allLists){
		currentRequest=new GetAccountLists(account.id)
				.setCallback(new SimpleCallback<>(this){
					@Override
					public void onSuccess(List<FollowList> result){
						Set<String> lists=result.stream().map(l->l.id).collect(Collectors.toSet());
						onDataLoaded(allLists.stream()
								.map(l->new CheckableListItem<>(l.title, null, CheckableListItem.Style.CHECKBOX, lists.contains(l.id),
										R.drawable.ic_list_alt_24px, AddAccountToListsFragment.this::onItemClick, l))
								.collect(Collectors.toList()), false);
					}
				})
				.exec(accountID);
	}

	@Override
	protected int indexOfItemsAdapter(){
		return 1;
	}

	@Override
	protected RecyclerView.Adapter<?> getAdapter(){
		TextView topText=new TextView(getActivity());
		topText.setTextAppearance(R.style.m3_body_medium);
		topText.setTextColor(UiUtils.getThemeColor(getActivity(), R.attr.colorM3OnSurface));
		topText.setPadding(V.dp(16), V.dp(8), V.dp(16), V.dp(8));
		topText.setText(getString(R.string.manage_user_lists, account.getDisplayUsername()));

		MergeRecyclerAdapter mergeAdapter=new MergeRecyclerAdapter();
		mergeAdapter.addAdapter(new SingleViewRecyclerAdapter(topText));
		mergeAdapter.addAdapter(super.getAdapter());
		return mergeAdapter;
	}

	private void onItemClick(CheckableListItem<FollowList> item){
		boolean add=!item.checked;
		ResultlessMastodonAPIRequest req=add ? new AddAccountsToList(item.parentObject.id, Set.of(account.id)) : new RemoveAccountsFromList(item.parentObject.id, Set.of(account.id));
		req.setCallback(new Callback<>(){
					@Override
					public void onSuccess(Void result){
						item.checked=add;
						rebindItem(item);
						if(add){
							E.post(new AccountAddedToListEvent(accountID, item.parentObject.id, account));
						}else{
							E.post(new AccountRemovedFromListEvent(accountID, item.parentObject.id, account.id));
						}
					}

					@Override
					public void onError(ErrorResponse error){
						error.showToast(getActivity());
					}
				})
				.wrapProgress(getActivity(), R.string.loading, false)
				.exec(accountID);
	}
}
