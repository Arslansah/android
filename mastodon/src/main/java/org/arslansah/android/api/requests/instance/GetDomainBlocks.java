package org.arslansah.android.api.requests.instance;

import com.google.gson.reflect.TypeToken;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.DomainBlock;

import java.util.List;

public class GetDomainBlocks extends MastodonAPIRequest<List<DomainBlock>>{
	public GetDomainBlocks(){
		super(HttpMethod.GET, "/instance/domain_blocks", new TypeToken<>(){});
	}

}
