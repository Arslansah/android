package org.arslansah.android.api.requests.filters;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Filter;
import org.arslansah.android.model.FilterAction;
import org.arslansah.android.model.FilterContext;
import org.arslansah.android.model.FilterKeyword;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UpdateFilter extends MastodonAPIRequest<Filter>{
	public UpdateFilter(String id, String title, EnumSet<FilterContext> context, FilterAction action, int expiresIn, List<FilterKeyword> words, List<String> deletedWords){
		super(HttpMethod.PUT, "/filters/"+id, Filter.class);

		List<KeywordAttribute> attrs=Stream.of(
				words.stream().map(w->new KeywordAttribute(w.id, null, w.keyword, w.wholeWord)),
				deletedWords.stream().map(wid->new KeywordAttribute(wid, true, null, null))
		).flatMap(Function.identity()).collect(Collectors.toList());
		setRequestBody(new FilterRequest(title, context, action, expiresIn==0 ? null : expiresIn, attrs));
	}

	@Override
	protected String getPathPrefix(){
		return "/api/v2";
	}
}
