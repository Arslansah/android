package org.arslansah.android.fragments.settings;

import android.os.Bundle;
import android.widget.Toast;

import org.arslansah.android.api.session.AccountActivationInfo;
import org.arslansah.android.api.session.AccountSession;
import org.arslansah.android.api.session.AccountSessionManager;
import org.arslansah.android.fragments.HomeFragment;
import org.arslansah.android.fragments.onboarding.AccountActivationFragment;
import org.arslansah.android.model.viewmodel.ListItem;
import org.arslansah.android.ui.utils.DiscoverInfoBannerHelper;
import org.arslansah.android.updater.GithubSelfUpdater;

import java.util.List;

import me.grishka.appkit.Nav;

public class SettingsDebugFragment extends BaseSettingsFragment<Void>{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTitle("Debug settings");
		ListItem<Void> selfUpdateItem, resetUpdateItem;
		onDataLoaded(List.of(
				new ListItem<>("Test email confirmation flow", null, this::onTestEmailConfirmClick),
				selfUpdateItem=new ListItem<>("Force self-update", null, this::onForceSelfUpdateClick),
				resetUpdateItem=new ListItem<>("Reset self-updater", null, this::onResetUpdaterClick),
				new ListItem<>("Reset search info banners", null, this::onResetDiscoverBannersClick),
				new ListItem<>("Reset pre-reply sheets", null, this::onResetPreReplySheetsClick)
		));
		if(!GithubSelfUpdater.needSelfUpdating()){
			resetUpdateItem.isEnabled=selfUpdateItem.isEnabled=false;
			selfUpdateItem.subtitle="Self-updater is unavailable in this build flavor";
		}
	}

	@Override
	protected void doLoadData(int offset, int count){}

	private void onTestEmailConfirmClick(ListItem<?> item){
		AccountSession sess=AccountSessionManager.getInstance().getAccount(accountID);
		sess.activated=false;
		sess.activationInfo=new AccountActivationInfo("test@email", System.currentTimeMillis());
		Bundle args=new Bundle();
		args.putString("account", accountID);
		args.putBoolean("debug", true);
		Nav.goClearingStack(getActivity(), AccountActivationFragment.class, args);
	}

	private void onForceSelfUpdateClick(ListItem<?> item){
		GithubSelfUpdater.forceUpdate=true;
		GithubSelfUpdater.getInstance().maybeCheckForUpdates();
		restartUI();
	}

	private void onResetUpdaterClick(ListItem<?> item){
		GithubSelfUpdater.getInstance().reset();
		restartUI();
	}

	private void onResetDiscoverBannersClick(ListItem<?> item){
		DiscoverInfoBannerHelper.reset();
		restartUI();
	}

	private void onResetPreReplySheetsClick(ListItem<?> item){
		// TODO fix this
//		GlobalUserPreferences.resetPreReplySheets();
		Toast.makeText(getActivity(), "Pre-reply sheets were reset", Toast.LENGTH_SHORT).show();
	}

	private void restartUI(){
		Bundle args=new Bundle();
		args.putString("account", accountID);
		Nav.goClearingStack(getActivity(), HomeFragment.class, args);
	}
}
