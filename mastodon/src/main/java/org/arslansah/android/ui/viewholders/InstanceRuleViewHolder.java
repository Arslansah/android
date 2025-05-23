package org.arslansah.android.ui.viewholders;

import android.annotation.SuppressLint;
import android.view.ViewGroup;
import android.widget.TextView;

import org.arslansah.android.R;
import org.arslansah.android.model.Instance;
import org.arslansah.android.ui.text.HtmlParser;

import me.grishka.appkit.utils.BindableViewHolder;

public class InstanceRuleViewHolder extends BindableViewHolder<Instance.Rule>{
	private final TextView text, number;
	private int position;

	public InstanceRuleViewHolder(ViewGroup parent){
		super(parent.getContext(), R.layout.item_server_rule, parent);
		text=findViewById(R.id.text);
		number=findViewById(R.id.number);
	}

	public void setPosition(int position){
		this.position=position;
	}

	@SuppressLint("DefaultLocale")
	@Override
	public void onBind(Instance.Rule item){
		if(item.parsedText==null){
			item.parsedText=HtmlParser.parseLinks(item.text);
		}
		text.setText(item.parsedText);
		number.setText(String.format("%d", position+1));
	}
}
