package org.arslansah.android.api.session;

public class AccountActivationInfo{
	public String email;
	public long lastEmailConfirmationResend;

	public AccountActivationInfo(String email, long lastEmailConfirmationResend){
		this.email=email;
		this.lastEmailConfirmationResend=lastEmailConfirmationResend;
	}
}
