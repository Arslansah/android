package org.arslansah.android.fragments.onboarding;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonElement;

import org.arslansah.android.GlobalUserPreferences;
import org.arslansah.android.R;
import org.arslansah.android.api.session.AccountSessionManager;
import org.arslansah.android.model.Instance;
import org.arslansah.android.model.catalog.CatalogInstance;
import org.arslansah.android.model.viewmodel.ListItem;
import org.arslansah.android.ui.BetterItemAnimator;
import org.arslansah.android.ui.M3AlertDialogBuilder;
import org.arslansah.android.ui.utils.UiUtils;
import org.arslansah.android.ui.viewcontrollers.ComposeLanguageAlertViewController;
import org.arslansah.android.utils.MastodonLanguage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import me.grishka.appkit.FragmentStackActivity;
import me.grishka.appkit.utils.BindableViewHolder;
import me.grishka.appkit.utils.MergeRecyclerAdapter;
import me.grishka.appkit.utils.SingleViewRecyclerAdapter;
import me.grishka.appkit.utils.V;
import me.grishka.appkit.views.UsableRecyclerView;
import okhttp3.internal.http2.Header;

public class CustomWelcomeFragment extends InstanceCatalogFragment {
	private View headerView;

	public CustomWelcomeFragment() {
		super(R.layout.fragment_welcome_custom, 1);
	}

	@Override
	public void onAttach(Context context){
		super.onAttach(context);
		setRefreshEnabled(false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		dataLoaded();
	}

	@Override
	protected void onUpdateToolbar(){
		super.onUpdateToolbar();

		if (!canGoBack()) {
			ImageView toolbarLogo=new ImageView(getActivity());
			toolbarLogo.setScaleType(ImageView.ScaleType.CENTER);
//			toolbarLogo.setImageResource(R.drawable.logo);
			toolbarLogo.setImageTintList(ColorStateList.valueOf(UiUtils.getThemeColor(getActivity(), android.R.attr.textColorPrimary)));

			FrameLayout logoWrap=new FrameLayout(getActivity());
			FrameLayout.LayoutParams logoParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
			logoParams.setMargins(0, V.dp(2), 0, 0);
			logoWrap.addView(toolbarLogo, logoParams);

			getToolbar().addView(logoWrap, new Toolbar.LayoutParams(Gravity.CENTER));
		} else {
			setTitle(R.string.add_account);
		}
	}

	@Override
	protected void proceedWithAuthOrSignup(Instance instance) {
		AccountSessionManager.getInstance().authenticate(getActivity(), instance);
	}

	@Override
	protected void updateFilteredList(){
		String query=getCurrentSearchQuery();
		boolean addFakeInstance=query.length()>0 && query.matches("^\\S+\\.[^\\.]+$");
		if(addFakeInstance){
			fakeInstance.domain=fakeInstance.normalizedDomain=query;
			fakeInstance.description=getString(R.string.loading_instance);
			if(filteredData.size()>0 && filteredData.get(0)==fakeInstance){
				if(list.findViewHolderForAdapterPosition(1) instanceof InstanceViewHolder ivh){
					ivh.rebind();
				}
			}
			if(filteredData.isEmpty()){
				filteredData.add(fakeInstance);
				adapter.notifyItemInserted(0);
			}
		}
		ArrayList<CatalogInstance> prevData=new ArrayList<>(filteredData);
		filteredData.clear();
		if(query.length()>0){
			boolean foundExactMatch=false;
			for(CatalogInstance inst:data){
				if(inst.normalizedDomain.contains(query)){
					filteredData.add(inst);
					if(inst.normalizedDomain.equals(query))
						foundExactMatch=true;
				}
			}
			if(!foundExactMatch && addFakeInstance) {
				filteredData.add(0, fakeInstance);
				adapter.notifyItemChanged(0);
			}
		}
		UiUtils.updateList(prevData, filteredData, list, adapter, Objects::equals);
		for(int i=0;i<list.getChildCount();i++){
			list.getChildAt(i).invalidateOutline();
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.setBackgroundColor(UiUtils.getThemeColor(getActivity(), R.attr.colorM3Surface));
		list.setItemAnimator(new BetterItemAnimator());
		((UsableRecyclerView) list).setSelector(null);
	}

	@Override
	protected void doLoadData(int offset, int count) {}

	@Override
	protected RecyclerView.Adapter<?> getAdapter(){
		headerView=getActivity().getLayoutInflater().inflate(R.layout.header_welcome_custom, list, false);

		headerView.findViewById(R.id.more).setVisibility(View.GONE);
		headerView.findViewById(R.id.visibility).setVisibility(View.GONE);
		headerView.findViewById(R.id.unread_indicator).setVisibility(View.GONE);
		headerView.findViewById(R.id.separator).setVisibility(View.GONE);
		headerView.findViewById(R.id.time).setVisibility(View.GONE);
		((TextView) headerView.findViewById(R.id.username)).setText(R.string.mo_app_username);
		((TextView) headerView.findViewById(R.id.name)).setText(R.string.mo_app_name);
		((ImageView) headerView.findViewById(R.id.avatar)).setImageDrawable(getActivity().getDrawable(R.mipmap.ic_launcher));
		((FragmentStackActivity) getActivity()).invalidateSystemBarColors(this);

		Button changeLanguageButton = headerView.findViewById(R.id.instance_select_language_button);
		TextView languageText = headerView.findViewById(R.id.instance_select_language);

		ArrayList<String> langs = new ArrayList<>();
		ArrayList<String> langsCode = new ArrayList<>();
		ArrayList<String> langsId = new ArrayList<>();

		var languages = MastodonLanguage.allLanguages;
		int languagesCount = languages.size();

		Map<String, List<Servers>> servers = new HashMap<>();
		for(int i = 0; i < languagesCount; i++){
			String langCode = languages.get(i).getLanguage();
			String langTitle = languages.get(i).getLanguageName();
			String langDisplay = languages.get(i).getDefaultName();

			if(langCode.equals("ar")){
				String title = 	String.format("%s (%s)", langDisplay, langTitle);
				langs.add(title);
				langsId.add(String.valueOf(i));
				langsCode.add(langCode);

				servers.computeIfAbsent(String.valueOf(i), k -> new ArrayList<>()).add(new Servers("bassam.social", true));
				servers.computeIfAbsent(String.valueOf(i), k -> new ArrayList<>()).add(new Servers("seewaan.com", false));
				servers.computeIfAbsent(String.valueOf(i), k -> new ArrayList<>()).add(new Servers("ummah.ps", false));
//				servers.computeIfAbsent("AR", k -> new ArrayList<>()).add(new Servers("ummalife.ps"));
				servers.computeIfAbsent(String.valueOf(i), k -> new ArrayList<>()).add(new Servers("mastodon.tn", false));
			}else if(langCode.equals("tr")){
				String title = 	String.format("%s (%s)", langDisplay, langTitle);
				langs.add(title);
				langsCode.add(langCode);
				langsId.add(String.valueOf(i));

				servers.computeIfAbsent(String.valueOf(i), k -> new ArrayList<>()).add(new Servers("arslansah.com.tr", true));
			};
		};
		String localeLang = Locale.getDefault().getLanguage().toLowerCase();
		if(!langsCode.equals(localeLang)){
			localeLang = "tr";
		}

		String[] langsArray = langs.toArray(new String[0]);
		String[] langsIdArray = langsId.toArray(new String[0]);


		mergeAdapter=new MergeRecyclerAdapter();
		mergeAdapter.addAdapter(new SingleViewRecyclerAdapter(headerView));
		mergeAdapter.addAdapter(adapter=new InstancesAdapter());

		changeLanguageButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				new M3AlertDialogBuilder(getActivity())
						.setTitle(getString(R.string.language))
						.setItems(langsArray, new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialogInterface, int i){
								String selectedLanguageDisplay = langsArray[i];
								String selectedLanguageId = langsIdArray[i];
								String selectedLanguageCode = languages.get(Integer.valueOf(selectedLanguageId)).getLanguage();

								languageText.setText(selectedLanguageDisplay);

//								Toast.makeText(getActivity(), selectedLanguage + " seçildi", Toast.LENGTH_SHORT).show();

								mergeAdapter.removeAdapter(adapter);
								filteredData.clear();

								var serversArray = servers.get(selectedLanguageId);

								for (Servers server : serversArray) {
									headerView.postDelayed(searchDebouncer, 300);
									CatalogInstance newItem = new CatalogInstance();
									newItem.normalizedDomain = server.domain.toString();
									newItem.domain = server.domain.toString();
									newItem.language = selectedLanguageCode.toString();
									newItem.recommend = server.recommend;
									filteredData.add(newItem);
									if(serversArray.size() == 1){
										nextButton.setEnabled(true);
										selectedInstance.setText(server.domain.toString());
										defaultServerButton.setEnabled(true);
									}else{
										nextButton.setEnabled(false);
										selectedInstance.setText(null);
										defaultServerButton.setEnabled(false);
									};
								};
								mergeAdapter.addAdapter(adapter);
							}
						})
						.setNegativeButton(R.string.cancel, null)
						.show();
			};
		});

		String finalLocaleLang=localeLang;
		headerView.post(new Runnable() {
			@Override
			public void run() {
				mergeAdapter.removeAdapter(adapter);
				filteredData.clear();

				int localeLangId = langsCode.indexOf(finalLocaleLang.toString());
				String selectedLangId = langsId.get(localeLangId);

				languageText.setText(langs.get(localeLangId));

				var serversArray = servers.get(selectedLangId);

				for (Servers server : serversArray) {
					headerView.postDelayed(searchDebouncer, 300);
					CatalogInstance newItem = new CatalogInstance();
					newItem.normalizedDomain = server.domain.toString();
					newItem.domain = server.domain.toString();
					newItem.language =langsCode.get(localeLangId);
					newItem.recommend = server.recommend;
					filteredData.add(newItem);
					if(serversArray.size() == 1){
						nextButton.setEnabled(true);
						selectedInstance.setText(server.domain.toString());
						defaultServerButton.setEnabled(true);
					}else{
						nextButton.setEnabled(false);
						selectedInstance.setText(null);
						defaultServerButton.setEnabled(false);
					};
				};
				mergeAdapter.addAdapter(adapter);
			}
		});

		return mergeAdapter;
	};


	private class InstancesAdapter extends UsableRecyclerView.Adapter<InstanceViewHolder> {
		public InstancesAdapter(){
			super(imgLoader);
		}

		@NonNull
		@Override
		public InstanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
			return new InstanceViewHolder();
		}

		@Override
		public void onBindViewHolder(InstanceViewHolder holder, int position){

			holder.bind(filteredData.get(position));
			chosenInstance = filteredData.get(position);
			super.onBindViewHolder(holder, position);
		}

		@Override
		public int getItemCount(){
			return filteredData.size();
		}

		@Override
		public int getItemViewType(int position){
			return -1;
		}
	}

	class Servers {
		String domain;
		boolean recommend;

		Servers(String domain, Boolean recommend){
			this.domain = domain;
			this.recommend = recommend;
		};

		@Override
		public String toString() {
			return domain;
		}
	};

	private class InstanceViewHolder extends BindableViewHolder<CatalogInstance> implements UsableRecyclerView.Clickable{
		private final TextView title, description, userCount, recommend, lang;

		public InstanceViewHolder(){
			super(getActivity(), R.layout.item_instance_custom, list);
			title=findViewById(R.id.title);
			description=findViewById(R.id.description);
			userCount=findViewById(R.id.user_count);
			recommend=findViewById(R.id.recommend);
			lang=findViewById(R.id.lang);
			if(Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
					UiUtils.fixCompoundDrawableTintOnAndroid6(userCount);
					UiUtils.fixCompoundDrawableTintOnAndroid6(lang);
			}
		}

		@Override
		public void onBind(CatalogInstance item){
			title.setText(item.normalizedDomain);
			description.setText(item.description);
			if (item == fakeInstance) {
				userCount.setVisibility(View.GONE);
				lang.setVisibility(View.GONE);
			} else {
//				userCount.setVisibility(View.VISIBLE);
				lang.setVisibility(View.VISIBLE);
				userCount.setText(UiUtils.abbreviateNumber(item.totalUsers));
				lang.setText(item.language.toUpperCase());
			}
			if(item.recommend){
				recommend.setVisibility(View.VISIBLE);
			};
		};

		@Override
		public void onClick(){
			chosenInstance=item;

			nextButton.setEnabled(true);
			selectedInstance.setText(item.domain.toString());
			defaultServerButton.setEnabled(true);

			loadInstanceInfo(item.domain, false);
//			onNextClick(null);
		}
	}

//	private void onJoinDefaultServerClick(View v){
//		chosenDefaultServer = getString(R.string.mo_app_url);
//
//		instanceLoadingProgress=new ProgressDialog(getActivity());
//		instanceLoadingProgress.setCancelable(false);
//		instanceLoadingProgress.setMessage(getString(R.string.loading_instance));
//		instanceLoadingProgress.show();
//
//		proceedWithServerDomain(chosenDefaultServer);
//	}
}
