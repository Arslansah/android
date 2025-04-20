package org.arslansah.android.model;

/**
 * A model object from which {@link org.arslansah.android.ui.displayitems.StatusDisplayItem}s can be generated.
 */
public interface DisplayItemsParent{
	String getID();

	default String getAccountID(){
		return null;
	}
}
