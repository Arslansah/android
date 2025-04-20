package org.arslansah.android.fragments.settings;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;

import org.arslansah.android.R;
import org.arslansah.android.fragments.HasAccountID;
import org.arslansah.android.fragments.MastodonRecyclerFragment;
import org.arslansah.android.model.viewmodel.CheckableListItem;
import org.arslansah.android.model.viewmodel.ListItem;
import org.arslansah.android.ui.BetterItemAnimator;
import org.arslansah.android.ui.DividerItemDecoration;
import org.arslansah.android.ui.adapters.GenericListItemsAdapter;
import org.arslansah.android.ui.viewholders.ListItemViewHolder;
import org.arslansah.android.utils.ProvidesAssistContent;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseSettingsFragment<T> extends MastodonRecyclerFragment<ListItem<T>> implements HasAccountID, ProvidesAssistContent.ProvidesWebUri{
	protected GenericListItemsAdapter<T> itemsAdapter;
	protected String accountID;

	public BaseSettingsFragment(){
		super(20);
	}

	public BaseSettingsFragment(int perPage){
		super(perPage);
	}

	public BaseSettingsFragment(int layout, int perPage){
		super(layout, perPage);
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		accountID=getArguments().getString("account");
		setRefreshEnabled(false);
	}

	@Override
	protected RecyclerView.Adapter<?> getAdapter(){
		return itemsAdapter=new GenericListItemsAdapter<T>(imgLoader, data);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		list.addItemDecoration(new DividerItemDecoration(getActivity(), R.attr.colorM3OutlineVariant, 1, 0, 0, vh->vh instanceof ListItemViewHolder<?> ivh && ivh.getItem().dividerAfter));
		list.setItemAnimator(new BetterItemAnimator());
	}

	protected int indexOfItemsAdapter(){
		return 0;
	}

	protected void toggleCheckableItem(ListItem<?> item){
		if(item instanceof CheckableListItem<?> checkable)
			checkable.toggle();
		rebindItem(item);
	}

	protected void rebindItem(ListItem<?> item){
		if(list==null)
			return;
		if(list.findViewHolderForAdapterPosition(indexOfItemsAdapter()+data.indexOf(item)) instanceof ListItemViewHolder<?> holder){
			holder.rebind();
		}
	}

	@Override
	public void onApplyWindowInsets(WindowInsets insets){
		if(Build.VERSION.SDK_INT>=29 && insets.getTappableElementInsets().bottom==0){
			list.setPadding(0, 0, 0, insets.getSystemWindowInsetBottom());
			emptyView.setPadding(0, 0, 0, insets.getSystemWindowInsetBottom());
			progress.setPadding(0, 0, 0, insets.getSystemWindowInsetBottom());
			insets=insets.inset(0, 0, 0, insets.getSystemWindowInsetBottom());
		}else{
			list.setPadding(0, 0, 0, 0);
		}
		super.onApplyWindowInsets(insets);
	}

	@Override
	public String getAccountID() {
		return accountID;
	}

	@Override
	public Uri getWebUri(Uri.Builder base) {
		return base.path("/settings").build();
	}
}
