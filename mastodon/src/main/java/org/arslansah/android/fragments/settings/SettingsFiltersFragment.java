package org.arslansah.android.fragments.settings;

import android.net.Uri;
import android.os.Bundle;

import com.squareup.otto.Subscribe;

import org.arslansah.android.E;
import org.arslansah.android.R;
import org.arslansah.android.api.requests.filters.GetFilters;
import org.arslansah.android.events.SettingsFilterCreatedOrUpdatedEvent;
import org.arslansah.android.events.SettingsFilterDeletedEvent;
import org.arslansah.android.model.Filter;
import org.arslansah.android.model.viewmodel.ListItem;
import org.arslansah.android.ui.adapters.GenericListItemsAdapter;
import org.parceler.Parcels;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import androidx.recyclerview.widget.RecyclerView;
import me.grishka.appkit.Nav;
import me.grishka.appkit.api.SimpleCallback;
import me.grishka.appkit.utils.MergeRecyclerAdapter;

public class SettingsFiltersFragment extends BaseSettingsFragment<Filter>{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTitle(R.string.settings_filters);
		loadData();
		E.register(this);
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		E.unregister(this);
	}

	@Override
	protected void doLoadData(int offset, int count){
		new GetFilters()
				.setCallback(new SimpleCallback<>(this){
					@Override
					public void onSuccess(List<Filter> result){
						onDataLoaded(result.stream().map(f->makeListItem(f)).collect(Collectors.toList()));
					}
				})
				.exec(accountID);
	}

	@Override
	protected RecyclerView.Adapter<?> getAdapter(){
		MergeRecyclerAdapter adapter=new MergeRecyclerAdapter();
		adapter.addAdapter(super.getAdapter());
		adapter.addAdapter(new GenericListItemsAdapter<>(Collections.singletonList(
				new ListItem<Void>(R.string.settings_add_filter, 0, R.drawable.ic_fluent_add_24_regular, this::onAddFilterClick)
		)));
		return adapter;
	}

	private void onFilterClick(ListItem<Filter> filter){
		Bundle args=new Bundle();
		args.putString("account", accountID);
		args.putParcelable("filter", Parcels.wrap(filter.parentObject));
		Nav.go(getActivity(), EditFilterFragment.class, args);
	}

	private void onAddFilterClick(ListItem<?> item){
		Bundle args=new Bundle();
		args.putString("account", accountID);
		Nav.go(getActivity(), EditFilterFragment.class, args);
	}

	private ListItem<Filter> makeListItem(Filter f){
		ListItem<Filter> item=new ListItem<>(f.title, getString(f.isActive() ? R.string.filter_active : R.string.filter_inactive), this::onFilterClick, f);
		return item;
	}

	@Subscribe
	public void onFilterDeleted(SettingsFilterDeletedEvent ev){
		if(!ev.accountID.equals(accountID))
			return;
		for(int i=0;i<data.size();i++){
			if(data.get(i).parentObject.id.equals(ev.filterID)){
				data.remove(i);
				itemsAdapter.notifyItemRemoved(i);
				break;
			}
		}
	}

	@Subscribe
	public void onFilterCreatedOrUpdated(SettingsFilterCreatedOrUpdatedEvent ev){
		if(!ev.accountID.equals(accountID))
			return;
		for(ListItem<Filter> item:data){
			if(item.parentObject.id.equals(ev.filter.id)){
				item.parentObject=ev.filter;
				item.title=ev.filter.title;
				item.subtitle=getString(ev.filter.isActive() ? R.string.filter_active : R.string.filter_inactive);
				rebindItem(item);
				return;
			}
		}
		data.add(makeListItem(ev.filter));
		itemsAdapter.notifyItemInserted(data.size()-1);
	}

	@Override
	public Uri getWebUri(Uri.Builder base) {
		return base.path("/filters").build();
	}
}
