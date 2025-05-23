package org.arslansah.android.fragments.onboarding;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.assist.AssistContent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.TextView;

import org.arslansah.android.R;
import org.arslansah.android.model.Instance;
import org.arslansah.android.ui.DividerItemDecoration;
import org.arslansah.android.ui.text.HtmlParser;
import org.arslansah.android.ui.utils.UiUtils;
import org.arslansah.android.utils.ElevationOnScrollListener;
import org.arslansah.android.utils.ProvidesAssistContent;
import org.parceler.Parcels;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.grishka.appkit.Nav;
import me.grishka.appkit.fragments.ToolbarFragment;
import me.grishka.appkit.utils.BindableViewHolder;
import me.grishka.appkit.utils.MergeRecyclerAdapter;
import me.grishka.appkit.utils.SingleViewRecyclerAdapter;
import me.grishka.appkit.utils.V;
import me.grishka.appkit.views.FragmentRootLinearLayout;
import me.grishka.appkit.views.UsableRecyclerView;

public class InstanceRulesFragment extends ToolbarFragment implements ProvidesAssistContent {
	private UsableRecyclerView list;
	private MergeRecyclerAdapter adapter;
	private Button btn;
	private View buttonBar;
	private Instance instance;
	private ElevationOnScrollListener onScrollListener;

	private static final int RULES_REQUEST=376;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		setNavigationBarColor(UiUtils.getThemeColor(activity, R.attr.colorM3Surface));
		instance=Parcels.unwrap(getArguments().getParcelable("instance"));
		setTitle(R.string.instance_rules_title);
	}

	@Override
	public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view=inflater.inflate(R.layout.fragment_onboarding_rules, container, false);

		list=view.findViewById(R.id.list);
		list.setLayoutManager(new LinearLayoutManager(getActivity()));
		View headerView=inflater.inflate(R.layout.item_list_header_simple, list, false);
		TextView text=headerView.findViewById(R.id.text);
		text.setText(Html.fromHtml(getString(R.string.instance_rules_subtitle, "<b>"+Html.escapeHtml(instance.uri)+"</b>")));

		adapter=new MergeRecyclerAdapter();
		adapter.addAdapter(new SingleViewRecyclerAdapter(headerView));
		adapter.addAdapter(new ItemsAdapter());
		list.setAdapter(adapter);
		list.addItemDecoration(new DividerItemDecoration(getActivity(), R.attr.colorM3OutlineVariant, 1, 56, 0, DividerItemDecoration.NOT_FIRST));

		btn=view.findViewById(R.id.btn_next);
		btn.setOnClickListener(v->onButtonClick());
		buttonBar=view.findViewById(R.id.button_bar);

		view.findViewById(R.id.btn_back).setOnClickListener(v->Nav.finish(this));

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
//		setStatusBarColor(UiUtils.getThemeColor(getActivity(), R.attr.colorM3Background));
//		view.setBackgroundColor(UiUtils.getThemeColor(getActivity(), R.attr.colorM3Background));
		list.addOnScrollListener(onScrollListener=new ElevationOnScrollListener((FragmentRootLinearLayout) view, buttonBar, getToolbar()));
	}

	@Override
	protected void onUpdateToolbar(){
		super.onUpdateToolbar();
		getToolbar().setBackgroundResource(R.drawable.bg_onboarding_panel);
		getToolbar().setElevation(0);
		if(onScrollListener!=null){
			onScrollListener.setViews(buttonBar, getToolbar());
		}
	}

	protected void onButtonClick(){
		Bundle args=new Bundle();
		args.putParcelable("instance", Parcels.wrap(instance));
		if(getArguments().containsKey("inviteCode")){
			args.putString("inviteCode", getArguments().getString("inviteCode"));
		}
		Nav.goForResult(getActivity(), GoogleMadeMeAddThisFragment.class, args, RULES_REQUEST, this);
	}

	@Override
	public void onFragmentResult(int reqCode, boolean success, Bundle result){
		super.onFragmentResult(reqCode, success, result);
		if(reqCode==RULES_REQUEST && !success){
			Nav.finish(this);
		}
	}

	@Override
	public void onApplyWindowInsets(WindowInsets insets){
		if(Build.VERSION.SDK_INT>=27){
			int inset=insets.getSystemWindowInsetBottom();
			buttonBar.setPadding(0, 0, 0, inset>0 ? Math.max(inset, V.dp(36)) : 0);
			super.onApplyWindowInsets(insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(),  insets.getSystemWindowInsetBottom()));
		}else{
			super.onApplyWindowInsets(insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom()));
		}
	}

	@Override
	public void onProvideAssistContent(AssistContent assistContent) {
		assistContent.setWebUri(new Uri.Builder()
				.scheme("https")
				.authority(instance.normalizedUri)
				.path("/about")
				.build());
	}

	private class ItemsAdapter extends RecyclerView.Adapter<ItemViewHolder>{

		@NonNull
		@Override
		public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
			return new ItemViewHolder();
		}

		@Override
		public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
			holder.bind(instance.rules.get(position));
		}

		@Override
		public int getItemCount(){
			return instance.rules.size();
		}
	}

	private class ItemViewHolder extends BindableViewHolder<Instance.Rule>{
		private final TextView text, number;

		public ItemViewHolder(){
			super(getActivity(), R.layout.item_server_rule, list);
			text=findViewById(R.id.text);
			number=findViewById(R.id.number);
		}

		@SuppressLint("DefaultLocale")
		@Override
		public void onBind(Instance.Rule item){
			if(item.parsedText==null){
				item.parsedText=HtmlParser.parseLinks(item.text);
			}
			text.setText(item.parsedText);
			number.setText(String.format("%d", getAbsoluteAdapterPosition()));
		}
	}
}
