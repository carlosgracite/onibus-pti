package org.itai.onibuspti.widget;

import java.util.Date;
import java.util.List;

import org.itai.onibuspti.R;
import org.itai.onibuspti.dao.BusTimeDao;
import org.itai.onibuspti.model.BusTime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class BusTimeWidgetProvider extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		Date currentTime = new Date(System.currentTimeMillis());
		
		BusTimeDao dao = new BusTimeDao(context);
		List<BusTime> times = dao.queryNext(1, currentTime, "barreira");
		List<BusTime> timesTwo = dao.queryNext(1, currentTime, "pti");
		
		ComponentName thisWidget = new ComponentName(context, BusTimeWidgetProvider.class);
		int[] allWidgets = appWidgetManager.getAppWidgetIds(thisWidget);
		
		for (int widgetId : allWidgets) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.bus_widget);
			if (times != null && times.size() > 0)
				remoteViews.setTextViewText(R.id.hour1, BusTime.dateFormat.format(times.get(0).getDepartureTime()));
			if (timesTwo != null && timesTwo.size() > 0)
				remoteViews.setTextViewText(R.id.hour2, BusTime.dateFormat.format(timesTwo.get(0).getDepartureTime()));
			
			Intent intent = new Intent(context, BusTimeWidgetProvider.class);
			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.update_btn, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}
}
