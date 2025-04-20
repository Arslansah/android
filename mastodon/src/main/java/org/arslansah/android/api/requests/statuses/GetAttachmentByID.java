package org.arslansah.android.api.requests.statuses;

import org.arslansah.android.api.MastodonAPIRequest;
import org.arslansah.android.model.Attachment;

import java.io.IOException;

import okhttp3.Response;

public class GetAttachmentByID extends MastodonAPIRequest<Attachment>{
	public GetAttachmentByID(String id){
		super(HttpMethod.GET, "/media/"+id, Attachment.class);
	}

	@Override
	public void validateAndPostprocessResponse(Attachment respObj, Response httpResponse) throws IOException{
		if(httpResponse.code()==206)
			respObj.url="";
		super.validateAndPostprocessResponse(respObj, httpResponse);
	}
}
