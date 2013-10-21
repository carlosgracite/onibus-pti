package org.itai.onibuspti.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.itai.onibuspti.model.BusTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BusScheduleParser {
	
	public static final String BASE = "baseOnibus";
	public static final String SCHEDULE = "horarios";
	public static final String DEPARTURE_TIME = "partida";
	public static final String BUS = "onibus";
	public static final String TYPE = "tipo";
	public static final String LOCAL = "local";
	
	public static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
	
	public static BusTime jsonToModel(String json) throws JSONException {
		BusTime busTime = new BusTime();
		
		JSONObject obj = new JSONObject(json);
			
		busTime.setDepartureTime(obj.getString(DEPARTURE_TIME));
		busTime.setBus(obj.getString(BUS));
		busTime.setType(obj.getString(TYPE));
		busTime.setLocal(obj.getString(LOCAL));
		
		return busTime;
	}
	
	public static List<BusTime> jsonToList(String json) throws JSONException {
		List<BusTime> list = new ArrayList<BusTime>();
		
		JSONObject obj = new JSONObject(json).getJSONObject(BASE);
		
		JSONArray array = obj.getJSONArray(SCHEDULE);
		for (int i = 0; i < array.length(); i++) {
			list.add(jsonToModel(array.getString(i)));
		}
		
		return list;
	}

}