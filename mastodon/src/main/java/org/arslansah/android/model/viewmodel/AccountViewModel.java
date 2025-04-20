package org.arslansah.android.model.viewmodel;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import org.arslansah.android.GlobalUserPreferences;
import org.arslansah.android.api.session.AccountSession;
import org.arslansah.android.api.session.AccountSessionManager;
import org.arslansah.android.model.Account;
import org.arslansah.android.model.AccountField;
import org.arslansah.android.ui.text.HtmlParser;
import org.arslansah.android.ui.text.LinkSpan;
import org.arslansah.android.ui.utils.CustomEmojiHelper;

import java.util.Collections;

import me.grishka.appkit.imageloader.requests.ImageLoaderRequest;
import me.grishka.appkit.imageloader.requests.UrlImageLoaderRequest;
import me.grishka.appkit.utils.V;

public class AccountViewModel{
	public final Account account;
	public final ImageLoaderRequest avaRequest;
	public final CustomEmojiHelper emojiHelper;
	public final CharSequence parsedName, parsedBio;
	public final String verifiedLink;

	public AccountViewModel(Account account, String accountID){
		this.account=account;
		AccountSession session = AccountSessionManager.get(accountID);
		avaRequest=new UrlImageLoaderRequest(
				TextUtils.isEmpty(account.avatar) ? session.getDefaultAvatarUrl() :
						GlobalUserPreferences.playGifs ? account.avatar : account.avatarStatic,
				V.dp(50), V.dp(50));
		emojiHelper=new CustomEmojiHelper();
		if(session.getLocalPreferences().customEmojiInNames)
			parsedName=HtmlParser.parseCustomEmoji(account.getDisplayName(), account.emojis);
		else
			parsedName=account.getDisplayName();
		parsedBio=HtmlParser.parse(account.note, account.emojis, Collections.emptyList(), Collections.emptyList(), accountID, account);
		SpannableStringBuilder ssb=new SpannableStringBuilder(parsedName);
		ssb.append(parsedBio);
		emojiHelper.setText(ssb);
		String verifiedLink=null;
		for(AccountField fld:account.fields){
			if(fld.verifiedAt!=null){
				verifiedLink=HtmlParser.stripAndRemoveInvisibleSpans(fld.value);
				break;
			}
		}
		this.verifiedLink=verifiedLink;
	}

	public AccountViewModel stripLinksFromBio(){
		if(parsedBio instanceof Spannable spannable){
			for(LinkSpan span:spannable.getSpans(0, spannable.length(), LinkSpan.class)){
				spannable.removeSpan(span);
			}
		}
		return this;
	}
}
