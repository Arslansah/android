package org.arslansah.android.model;

import org.arslansah.android.api.RequiredField;
import org.parceler.Parcel;

@Parcel
public class ExtendedDescription extends BaseModel{
	@RequiredField
	public String content;
	public String updatedAt;

	@Override
	public String toString() {
		return "ExtendedDescription{" +
				"content='" + content + '\'' +
				", updatedAt='" + updatedAt + '\'' +
				'}';
	}
}
