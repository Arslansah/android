package org.arslansah.android.ui.viewholders;

import android.content.Context;
import android.view.ViewGroup;

import org.arslansah.android.R;
import org.arslansah.android.model.viewmodel.CheckableListItem;
import org.arslansah.android.ui.views.CheckableLinearLayout;

public abstract class CheckableListItemViewHolder extends ListItemViewHolder<CheckableListItem<?>>{
	protected final CheckableLinearLayout checkableLayout;

	public CheckableListItemViewHolder(Context context, ViewGroup parent){
		super(context, R.layout.item_generic_list_checkable, parent);
		checkableLayout=(CheckableLinearLayout) itemView;
	}

	@Override
	public void onBind(CheckableListItem<?> item){
		super.onBind(item);
		checkableLayout.setChecked(item.checked);
	}
}
