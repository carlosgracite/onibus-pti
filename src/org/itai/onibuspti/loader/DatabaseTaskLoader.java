package org.itai.onibuspti.loader;

import java.util.List;

import org.itai.onibuspti.dao.BusTimeDao;
import org.itai.onibuspti.dao.MetadataDao;
import org.itai.onibuspti.json.BusScheduleParser;
import org.itai.onibuspti.json.MetadataParser;
import org.itai.onibuspti.model.BusTime;
import org.itai.onibuspti.model.Metadata;
import org.itai.onibuspti.util.NetworkUtils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class DatabaseTaskLoader extends AsyncTaskLoader<String> {
	
	private static final String ADRESS = "http://pdi.pti.org.br/";
	private static final String SCHEDULE = "onibus/horarios";
	private static final String METADATA = "onibus/horarios/versao";
	
	private boolean finished = false;
	private String message = null;

	public DatabaseTaskLoader(Context context) {
		super(context);
	}
	
	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		if (finished) {
			deliverResult(message);
		}
	}

	@Override
	public String loadInBackground() {
		BusTimeDao dao = new BusTimeDao(getContext());
		try {
			
			if (!NetworkUtils.isNetworkAvailable(getContext())) {
				throw new RuntimeException("Rede não disponível.");
			}
			
			Metadata newMetadata = fetchMetadata();
			
			MetadataDao metadataDao = new MetadataDao(getContext());
			Metadata currentMetadata = metadataDao.query();
			
			if (currentMetadata == null || 
					currentMetadata.getVersion() < newMetadata.getVersion()) {
				
				List<BusTime> data = fetchData();
				
				if (currentMetadata != null)
					newMetadata.setId(currentMetadata.getId());
				
				dao.updateDatabase(data, newMetadata);
			}
			
		} catch (Exception e) {
			List<BusTime> data = dao.queryAll();
			if (data.size() == 0) {
				message = "Não foi possível carregar os horários.";
			}
		}
		
		finished = true;
		return message;
	}
	
	
	private Metadata fetchMetadata() {
		Metadata metadata = null;
		
		try {
			String json = NetworkUtils.httpGET(ADRESS + METADATA);
			metadata = MetadataParser.jsonToModel(json);
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return metadata;
	}
	
	private List<BusTime> fetchData() {
		List<BusTime> data = null;
		
		try {
			String json = NetworkUtils.httpGET(ADRESS + SCHEDULE);
			data = BusScheduleParser.jsonToList(json);
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return data;
	}
	
}