package org.itai.onibuspti.widget;

import org.itai.onibuspti.R;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class BusTimeWidgetConfigure extends Activity {
	Integer mAppWidgetId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(null);
			RemoteViews views = new RemoteViews(null, R.layout.bus_widget);
			appWidgetManager.updateAppWidget(mAppWidgetId, views);
			
			Intent resultValue = new Intent();
			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
			setResult(RESULT_OK, resultValue);
			finish();
		}
	}
}