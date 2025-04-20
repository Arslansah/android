package org.arslansah.android.api.requests.instance;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Instance;

public class GetInstance extends MastodonAPIRequest<Instance>{
	public GetInstance(){
		super(HttpMethod.GET, "/instance", Instance.class);
	}

	public static class V2 extends MastodonAPIRequest<Instance.V2>{
		public V2(){
			super(HttpMethod.GET, "/instance", Instance.V2.class);
		}

		@Override
		protected String getPathPrefix() {
			return "/api/v2";
		}
	}
}
