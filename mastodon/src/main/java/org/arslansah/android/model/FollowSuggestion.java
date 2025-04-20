package org.arslansah.android.model;

import org.arslansah.android.api.ObjectValidationException;
import org.arslansah.android.api.RequiredField;

public class FollowSuggestion extends BaseModel{
	@RequiredField
	public Account account;
//	public String source;

	@Override
	public void postprocess() throws ObjectValidationException{
		super.postprocess();
		account.postprocess();
	}
}
