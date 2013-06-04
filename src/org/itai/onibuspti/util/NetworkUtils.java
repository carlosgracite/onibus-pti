package org.itai.onibuspti.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

	public static String httpGET(String url) throws Exception {
		String result = null;

		HttpGet request = new HttpGet(url);
		request.addHeader("accept", "application/json");
		request.addHeader("content-type", "application/json");

		HttpClient client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		//HttpConnectionParams.setSoTimeout(params, 5000);

		HttpResponse response;
		try {
			response = client.execute(request);

			if(response.getStatusLine().getStatusCode() != 200) {
				throw new Exception("Erro ao efetuar requisição: " + response.getStatusLine().getStatusCode());
			}

			result = EntityUtils.toString(response.getEntity());

		} catch (IOException e) {
			throw new Exception("Erro ao efetuar requisição.");	
		} catch (Exception e) {
			throw new Exception("Erro ao efetuar requisição.");
		}

		return result;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) 
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	} 

}
