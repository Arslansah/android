package org.arslansah.android.fragments.report;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.arslansah.android.E;
import org.arslansah.android.R;
import org.arslansah.android.api.requests.accounts.SetAccountFollowed;
import org.arslansah.android.events.RemoveAccountPostsEvent;
import org.arslansah.android.fragments.MastodonToolbarFragment;
import org.arslansah.android.model.Account;
import org.arslansah.android.model.Relationship;
import org.arslansah.android.model.ReportReason;
import org.arslansah.android.ui.OutlineProviders;
import org.arslansah.android.ui.utils.UiUtils;
import org.parceler.Parcels;

import androidx.annotation.DrawableRes;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import me.grishka.appkit.Nav;
import me.grishka.appkit.api.Callback;
import me.grishka.appkit.api.ErrorResponse;
import me.grishka.appkit.imageloader.ViewImageLoader;
import me.grishka.appkit.imageloader.requests.UrlImageLoaderRequest;
import me.grishka.appkit.utils.V;

public class ReportDoneFragment extends MastodonToolbarFragment{
	private String accountID;
	private Account reportAccount;
	private Button btn;
	private View buttonBar;
	private ReportReason reason;
	private TextView unfollowTitle;
	private TextView muteTitle;
	private TextView blockTitle;
	private View unfollowBtn;
	private View muteBtn;
	private View blockBtn;
	private Relationship relationship;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		setNavigationBarColor(UiUtils.getThemeColor(activity, R.attr.colorM3Surface));
		accountID=getArguments().getString("account");
		reportAccount=Parcels.unwrap(getArguments().getParcelable("reportAccount"));
		reason=ReportReason.valueOf(getArguments().getString("reason"));
		relationship=Parcels.unwrap(getArguments().getParcelable("relationship"));
		if(getArguments().getBoolean("fromPost", false))
			setTitle(R.string.report_title_post);
		else
			setTitle(getString(R.string.report_title, reportAccount.acct));
	}

	@Override
	public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view=inflater.inflate(R.layout.fragment_report_done, container, false);

		TextView title=view.findViewById(R.id.title);
		TextView subtitle=view.findViewById(R.id.subtitle);
		if(reason==ReportReason.PERSONAL){
			title.setText(R.string.report_personal_title);
			if(relationship!=null && relationship.blocking){
				subtitle.setText(R.string.report_personal_already_blocked);
			}else{
				subtitle.setText(R.string.report_personal_subtitle);
			}
		}else{
			title.setText(R.string.report_sent_title);
			if(relationship!=null && relationship.blocking){
				subtitle.setText(R.string.report_sent_already_blocked);
			}else{
				subtitle.setText(getString(R.string.report_sent_subtitle, '@'+reportAccount.acct));
			}
		}

		btn=view.findViewById(R.id.btn_next);
		btn.setOnClickListener(this::onButtonClick);
		buttonBar=view.findViewById(R.id.button_bar);
		btn.setText(R.string.done);

		if(reason!=ReportReason.PERSONAL){
			TextView stamp=view.findViewById(R.id.reported_stamp);
			ImageView ava=view.findViewById(R.id.avatar);
			ava.setOutlineProvider(OutlineProviders.roundedRect(24));
			ava.setClipToOutline(true);
			ViewImageLoader.load(ava, null, new UrlImageLoaderRequest(reportAccount.avatar));
			stamp.setAlpha(0f);
			stamp.setTranslationX(V.dp(148));
			stamp.setTranslationY(V.dp(-296));
			stamp.setScaleX(3.5f);
			stamp.setScaleY(3.5f);
			ObjectAnimator alpha=ObjectAnimator.ofFloat(stamp, View.ALPHA, 1f).setDuration(400);
			alpha.setInterpolator(new PathInterpolator(0.16f, 1, 0.3f, 1));
			alpha.start();
			setupSpringAnimation(new SpringAnimation(stamp, DynamicAnimation.TRANSLATION_X, 0f));
			setupSpringAnimation(new SpringAnimation(stamp, DynamicAnimation.TRANSLATION_Y, 0f));
			setupSpringAnimation(new SpringAnimation(stamp, DynamicAnimation.SCALE_X, 1f));
			setupSpringAnimation(new SpringAnimation(stamp, DynamicAnimation.SCALE_Y, 1f));

		}else{
			view.findViewById(R.id.ava_reported).setVisibility(View.GONE);
		}

		unfollowTitle=view.findViewById(R.id.unfollow_title);
		muteTitle=view.findViewById(R.id.mute_title);
		blockTitle=view.findViewById(R.id.block_title);
		unfollowBtn=view.findViewById(R.id.unfollow_btn);
		muteBtn=view.findViewById(R.id.mute_btn);
		blockBtn=view.findViewById(R.id.block_btn);

		unfollowTitle.setText(getString(R.string.unfollow_user, '@'+reportAccount.acct));
		muteTitle.setText(getString(R.string.mute_user, '@'+reportAccount.acct));
		blockTitle.setText(getString(R.string.block_user, '@'+reportAccount.acct));
		setIconToButton(R.drawable.ic_fluent_person_delete_20_filled, unfollowTitle);
		setIconToButton(R.drawable.ic_fluent_person_prohibited_20_filled, blockTitle);
		setIconToButton(R.drawable.ic_fluent_speaker_0_20_filled, muteTitle);

		unfollowBtn.setOnClickListener(v->onUnfollowClick());
		muteBtn.setOnClickListener(v->onMuteClick());
		blockBtn.setOnClickListener(v->onBlockClick());

		if(relationship!=null){
			if(relationship.blocking){
				muteBtn.setVisibility(View.GONE);
				view.findViewById(R.id.mute_explanation).setVisibility(View.GONE);
				unfollowBtn.setVisibility(View.GONE);
				view.findViewById(R.id.unfollow_explanation).setVisibility(View.GONE);
				blockBtn.setVisibility(View.GONE);
				view.findViewById(R.id.block_explanation).setVisibility(View.GONE);
			}else{
				if(relationship.muting){
					muteBtn.setVisibility(View.GONE);
					view.findViewById(R.id.mute_explanation).setVisibility(View.GONE);
				}
				if(!relationship.following){
					unfollowBtn.setVisibility(View.GONE);
					view.findViewById(R.id.unfollow_explanation).setVisibility(View.GONE);
				}
			}
		}

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onApplyWindowInsets(WindowInsets insets){
		super.onApplyWindowInsets(UiUtils.applyBottomInsetToFixedView(buttonBar, insets));
	}

	private void onButtonClick(View v){
		Nav.finish(this);
	}

	private void onUnfollowClick(){
		new SetAccountFollowed(reportAccount.id, false, false)
				.setCallback(new Callback<>(){
					@Override
					public void onSuccess(Relationship result){
						E.post(new RemoveAccountPostsEvent(accountID, reportAccount.id, true));
						unfollowTitle.setTextColor(UiUtils.getThemeColor(getActivity(), R.attr.colorM3OnSecondaryContainer));
						unfollowTitle.setText(getString(R.string.unfollowed_user, '@'+reportAccount.acct));
						setIconToButton(R.drawable.ic_fluent_checkmark_24_regular, unfollowTitle);
						unfollowBtn.setBackgroundResource(R.drawable.bg_button_m3_tonal);
						unfollowBtn.setClickable(false);
						unfollowBtn.setFocusable(false);
					}

					@Override
					public void onError(ErrorResponse error){
						error.showToast(getActivity());
					}
				})
				.wrapProgress(getActivity(), R.string.loading, false)
				.exec(accountID);
	}

	private void onMuteClick(){
		UiUtils.confirmToggleMuteUser(getActivity(), accountID, reportAccount, false, rel->{
			muteTitle.setTextColor(UiUtils.getThemeColor(getActivity(), R.attr.colorM3OnSecondaryContainer));
			muteTitle.setText(getString(R.string.muted_user, '@'+reportAccount.acct));
			setIconToButton(R.drawable.ic_fluent_checkmark_24_regular, muteTitle);
			muteBtn.setBackgroundResource(R.drawable.bg_button_m3_tonal);
			muteBtn.setClickable(false);
			muteBtn.setFocusable(false);
		});
	}

	private void onBlockClick(){
		UiUtils.confirmToggleBlockUser(getActivity(), accountID, reportAccount, false, rel->{
			blockTitle.setTextColor(UiUtils.getThemeColor(getActivity(), R.attr.colorM3OnSecondaryContainer));
			blockTitle.setText(getString(R.string.blocked_user, '@'+reportAccount.acct));
			setIconToButton(R.drawable.ic_fluent_checkmark_24_regular, blockTitle);
			blockBtn.setBackgroundResource(R.drawable.bg_button_m3_tonal);
			blockBtn.setClickable(false);
			blockBtn.setFocusable(false);
			if(unfollowBtn.isClickable())
				unfollowBtn.setEnabled(false);
			if(muteBtn.isClickable())
				muteBtn.setEnabled(false);
		});
	}

	@Override
	protected int getNavigationIconDrawableResource(){
		return R.drawable.ic_fluent_dismiss_24_regular;
	}

	@Override
	public boolean wantsCustomNavigationIcon(){
		return true;
	}

	private void setIconToButton(@DrawableRes int icon, TextView button){
		Drawable d=getResources().getDrawable(icon, getActivity().getTheme()).mutate();
		d.setBounds(0, 0, V.dp(18), V.dp(18));
		d.setTintList(button.getTextColors());
		button.setCompoundDrawablesRelative(d, null, null, null);
	}

	private void setupSpringAnimation(SpringAnimation anim){
		anim.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY).setStiffness(500);
		anim.start();
	}
}
