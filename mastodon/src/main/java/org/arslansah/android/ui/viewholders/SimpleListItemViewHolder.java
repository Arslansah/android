package org.arslansah.android.ui.viewholders;

import android.content.Context;
import android.view.ViewGroup;

import org.arslansah.android.R;
import org.arslansah.android.model.viewmodel.ListItem;

public class SimpleListItemViewHolder extends ListItemViewHolder<ListItem<?>>{
	public SimpleListItemViewHolder(Context context, ViewGroup parent){
		super(context, R.layout.item_generic_list, parent);
	}
}
