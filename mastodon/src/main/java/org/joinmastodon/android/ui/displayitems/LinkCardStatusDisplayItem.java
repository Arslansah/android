package org.joinmastodon.android.ui.displayitems;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.joinmastodon.android.R;
import org.joinmastodon.android.fragments.BaseStatusListFragment;
import org.joinmastodon.android.model.Card;
import org.joinmastodon.android.model.Status;
import org.joinmastodon.android.ui.OutlineProviders;
import org.joinmastodon.android.ui.drawables.BlurhashCrossfadeDrawable;
import org.joinmastodon.android.ui.utils.UiUtils;

import me.grishka.appkit.imageloader.ImageLoaderViewHolder;
import me.grishka.appkit.imageloader.requests.ImageLoaderRequest;
import me.grishka.appkit.imageloader.requests.UrlImageLoaderRequest;
import me.grishka.appkit.utils.V;

public class LinkCardStatusDisplayItem extends StatusDisplayItem{
	private final Status status;
	private final UrlImageLoaderRequest imgRequest;

	public LinkCardStatusDisplayItem(String parentID, BaseStatusListFragment parentFragment, Status status, boolean showImagePreview){
		super(parentID, parentFragment);
		this.status=status;
		if(status.card.image!=null && showImagePreview)
			imgRequest=new UrlImageLoaderRequest(status.card.image, 1000, 1000);
		else
			imgRequest=null;
	}

	@Override
	public Type getType(){
		return Type.CARD;
	}

	@Override
	public int getImageCount(){
		return imgRequest==null ? 0 : 1;
	}

	@Override
	public ImageLoaderRequest getImageRequest(int index){
		return imgRequest;
	}

	public static class Holder extends StatusDisplayItem.Holder<LinkCardStatusDisplayItem> implements ImageLoaderViewHolder{
		private final TextView title, description, domain;
		private final ImageView photo;
		private final View inner;
		private BlurhashCrossfadeDrawable crossfadeDrawable=new BlurhashCrossfadeDrawable();
		private boolean didClear;

		public Holder(Context context, ViewGroup parent){
			super(context, R.layout.display_item_link_card, parent);
			title=findViewById(R.id.title);
			description=findViewById(R.id.description);
			domain=findViewById(R.id.domain);
			photo=findViewById(R.id.photo);
			inner=findViewById(R.id.inner);
			inner.setOnClickListener(this::onClick);
		}

		@Override
		public void onBind(LinkCardStatusDisplayItem item){
			Card card=item.status.card;
			title.setText(card.title);
			description.setText(card.description);
			description.setVisibility(TextUtils.isEmpty(card.description) ? View.GONE : View.VISIBLE);
			domain.setText(Uri.parse(card.url).getHost());

			photo.setImageDrawable(null);
			if(item.imgRequest!=null){
				crossfadeDrawable.setSize(card.width, card.height);
				if (card.width > 0) {
					// akkoma servers don't provide width and height
					crossfadeDrawable.setSize(card.width, card.height);
				} else {
					crossfadeDrawable.setSize(itemView.getWidth(), itemView.getHeight());
				}
				crossfadeDrawable.setBlurhashDrawable(card.blurhashPlaceholder);
				crossfadeDrawable.setCrossfadeAlpha(item.status.spoilerRevealed ? 0f : 1f);
				photo.setImageDrawable(crossfadeDrawable);
				photo.setVisibility(View.VISIBLE);
				didClear=false;
			} else {
				photo.setVisibility(View.GONE);
			}

			// if there's no image, we don't want to cover the inset borders
			FrameLayout.LayoutParams params=(FrameLayout.LayoutParams) inner.getLayoutParams();
			int margin=item.inset && item.imgRequest == null ? V.dp(1) : 0;
			params.setMargins(margin, 0, margin, margin);

			boolean insetAndLast=item.inset && isLastDisplayItemForStatus();
			inner.setClipToOutline(insetAndLast);
			inner.setOutlineProvider(insetAndLast ? OutlineProviders.bottomRoundedRect(12) : null);
		}

		@Override
		public void setImage(int index, Drawable drawable){
			crossfadeDrawable.setImageDrawable(drawable);
			if(didClear && item.status.spoilerRevealed)
				crossfadeDrawable.animateAlpha(0f);
		}

		@Override
		public void clearImage(int index){
			crossfadeDrawable.setCrossfadeAlpha(1f);
			didClear=true;
		}

		private void onClick(View v){
			UiUtils.openURL(itemView.getContext(), item.parentFragment.getAccountID(), item.status.card.url);
		}
	}
}

