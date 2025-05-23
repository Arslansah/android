package org.arslansah.android.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.arslansah.android.E;
import org.arslansah.android.R;
import org.arslansah.android.api.requests.lists.UpdateList;
import org.arslansah.android.api.session.AccountSessionManager;
import org.arslansah.android.events.ListUpdatedEvent;
import org.arslansah.android.model.FollowList;
import org.arslansah.android.ui.M3AlertDialogBuilder;

import me.grishka.appkit.api.Callback;
import me.grishka.appkit.api.ErrorResponse;

public class EditListFragment extends BaseEditListFragment{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTitle(R.string.edit_list);
		loadMembers();
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		menu.add(R.string.delete_list);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		new M3AlertDialogBuilder(getActivity())
				.setTitle(R.string.delete_list)
				.setMessage(getString(R.string.delete_list_confirm, followList.title))
				.setPositiveButton(R.string.delete, (dlg, which)->doDeleteList())
				.setNegativeButton(R.string.cancel, null)
				.show();
		return true;
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		String newTitle=titleEdit.getText().toString();
		FollowList.RepliesPolicy newRepliesPolicy=getSelectedRepliesPolicy();
		boolean newExclusive=exclusiveItem.checked;
		if(!newTitle.equals(followList.title) || newRepliesPolicy!=followList.repliesPolicy || newExclusive!=followList.exclusive){
			new UpdateList(followList.id, newTitle, newRepliesPolicy, newExclusive)
					.setCallback(new Callback<>(){
						@Override
						public void onSuccess(FollowList result){
							AccountSessionManager.get(accountID).getCacheController().updateList(result);
							E.post(new ListUpdatedEvent(accountID, result));
						}

						@Override
						public void onError(ErrorResponse error){
							// TODO handle errors somehow
						}
					})
					.exec(accountID);
		}
	}
}
