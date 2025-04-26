package org.arslansah.android.fragments.onboarding;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import me.grishka.appkit.FragmentStackActivity;
import me.grishka.appkit.utils.BindableViewHolder;
import me.grishka.appkit.utils.MergeRecyclerAdapter;
import me.grishka.appkit.utils.SingleViewRecyclerAdapter;
import me.grishka.appkit.utils.V;
import me.grishka.appkit.views.UsableRecyclerView;

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

		ArrayList<String> langs = new ArrayList<>();
		langs.add("ar");
		langs.add("tr");

		langs.remove(Locale.getDefault().getLanguage());
		langs.add(0, Locale.getDefault().getLanguage());

		Spinner spin = headerView.findViewById(R.id.select_lang);

		mergeAdapter=new MergeRecyclerAdapter();
		mergeAdapter.addAdapter(new SingleViewRecyclerAdapter(headerView));
		mergeAdapter.addAdapter(adapter=new InstancesAdapter());


		Map<String, List<Servers>> servers = new HashMap<>();
		servers.computeIfAbsent("ar", k -> new ArrayList<>()).add(new Servers("bassam.social", true));
		servers.computeIfAbsent("ar", k -> new ArrayList<>()).add(new Servers("seewaan.com", false));
		servers.computeIfAbsent("ar", k -> new ArrayList<>()).add(new Servers("ummah.ps", false));
//		servers.computeIfAbsent("ar", k -> new ArrayList<>()).add(new Servers("ummalife.ps"));
		servers.computeIfAbsent("ar", k -> new ArrayList<>()).add(new Servers("mastodon.tn", false));
		servers.computeIfAbsent("tr", k -> new ArrayList<>()).add(new Servers("arslansah.com.tr", true));

		if(spin != null){
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, langs);
			spin.setAdapter(arrayAdapter);
		}

		spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
//				mergeAdapter=new MergeRecyclerAdapter();
//				mergeAdapter.addAdapter(new SingleViewRecyclerAdapter(headerView));
				mergeAdapter.removeAdapter(adapter);
				filteredData.clear();
				String selectedValue = adapterView.getItemAtPosition(i).toString();

				String lowerSelectedValue = selectedValue.toLowerCase();
				var serversArray = servers.get(lowerSelectedValue);



				for (Servers server : serversArray) {
					headerView.postDelayed(searchDebouncer, 300);
					CatalogInstance newItem = new CatalogInstance();
					newItem.normalizedDomain = server.domain.toString();
					newItem.domain = server.domain.toString();
					newItem.language = selectedValue.toUpperCase();
					newItem.recommend = server.recommend;
					filteredData.add(newItem);
					if(serversArray.size() == 1){
						nextButton.setEnabled(true);
						selectedInstance.setText(server.domain.toString());
						defaultServerButton.setEnabled(true);
					}else{
						nextButton.setEnabled(false);
						selectedInstance.setText("");
						defaultServerButton.setEnabled(false);
					};
				};
				mergeAdapter.addAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView){
			}
		});

		return mergeAdapter;
	}


	private class InstancesAdapter extends UsableRecyclerView.Adapter<InstanceViewHolder> {
		public InstancesAdapter(){
			super(imgLoader);
		}

		@NonNull
		@Override
		public InstanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
			System.out.println(3.0);
			return new InstanceViewHolder();
		}

		@Override
		public void onBindViewHolder(InstanceViewHolder holder, int position){

			holder.bind(filteredData.get(position));
			chosenInstance = filteredData.get(position);
			System.out.println(2.1);
			System.out.println(position);
			System.out.println(chosenInstance);
//			if (chosenInstance != fakeInstance) nextButton.setEnabled(true);
			super.onBindViewHolder(holder, position);
		}

		@Override
		public int getItemCount(){
			System.out.println(3.2);
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
			System.out.println(title.getText());
			description.setText(item.description);
			if (item == fakeInstance) {
				userCount.setVisibility(View.GONE);
				lang.setVisibility(View.GONE);
			} else {
				userCount.setVisibility(View.VISIBLE);
				lang.setVisibility(View.VISIBLE);
				userCount.setText(UiUtils.abbreviateNumber(item.totalUsers));
				lang.setText(item.language.toUpperCase());
			}
			System.out.println(item);
			if(item.recommend){
				recommend.setVisibility(View.VISIBLE);
				recommend.setText("Önerilen");
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
