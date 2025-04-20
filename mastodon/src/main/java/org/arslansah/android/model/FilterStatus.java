package org.arslansah.android.model;

import org.arslansah.android.api.AllFieldsAreRequired;
import org.parceler.Parcel;

@AllFieldsAreRequired
@Parcel
public class FilterStatus extends BaseModel{
	public String id;
	public String statusId;
}
