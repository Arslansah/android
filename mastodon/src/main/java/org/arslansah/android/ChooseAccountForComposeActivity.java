package org.arslansah.android;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.arslansah.android.api.session.AccountSession;
import org.arslansah.android.api.session.AccountSessionManager;
import org.arslansah.android.fragments.ComposeFragment;
import org.arslansah.android.ui.sheets.AccountSwitcherSheet;
import org.arslansah.android.ui.utils.UiUtils;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import me.grishka.appkit.FragmentStackActivity;

public class ChooseAccountForComposeActivity extends FragmentStackActivity{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState){
		UiUtils.setUserPreferredTheme(this);
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null && Objects.equals(getIntent().getAction(), Intent.ACTION_CHOOSER)) {
			AccountSessionManager.getInstance().maybeUpdateLocalInfo();
			List<AccountSession> sessions=AccountSessionManager.getInstance().getLoggedInAccounts();
			if (sessions.isEmpty()){
				Toast.makeText(this, R.string.err_not_logged_in, Toast.LENGTH_SHORT).show();
				finish();
			} else if (sessions.size() > 1) {
				AccountSwitcherSheet sheet = new AccountSwitcherSheet(this, null, R.drawable.ic_fluent_compose_28_regular,
						R.string.choose_account, null, false);
				sheet.setOnClick((accountId, open) -> {
					openComposeFragment(accountId);
				});
				sheet.show();
			} else if (sessions.size() == 1) {
				openComposeFragment(sessions.get(0).getID());
			}
		}
	}

	private void openComposeFragment(String accountID){
		getWindow().setBackgroundDrawable(null);
		Bundle args=new Bundle();
		args.putString("account", accountID);
		Fragment fragment=new ComposeFragment();
		fragment.setArguments(args);
		showFragmentClearingBackStack(fragment);
	}
}
