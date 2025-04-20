package org.arslansah.android.model;

import com.google.gson.annotations.SerializedName;

public enum Severity {
	@SerializedName("silence")
	SILENCE,
	@SerializedName("suspend")
	SUSPEND
}