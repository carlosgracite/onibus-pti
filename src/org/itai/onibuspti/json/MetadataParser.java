package org.itai.onibuspti.json;

import org.itai.onibuspti.model.Metadata;
import org.json.JSONException;
import org.json.JSONObject;

public class MetadataParser {
	
	public static final String VERSION = "versao";
	
	public static Metadata jsonToModel(String json) throws JSONException {
		Metadata metadata = new Metadata();
		
		JSONObject obj = new JSONObject(json);
		
		metadata.setVersion(obj.getLong(VERSION));
		
		return metadata;
	}

}
