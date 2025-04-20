package org.arslansah.android.model.catalog;

import org.arslansah.android.api.AllFieldsAreRequired;
import org.arslansah.android.model.BaseModel;

@AllFieldsAreRequired
public class CatalogCategory extends BaseModel{
	public String category;
	public int serversCount;

	@Override
	public String toString(){
		return "CatalogCategory{"+
				"category='"+category+'\''+
				", serversCount="+serversCount+
				'}';
	}
}
