package org.itai.onibuspti.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.itai.onibuspti.model.BusTime;
import org.itai.onibuspti.model.Metadata;
import org.itai.onibuspti.util.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public class BusTimeDao {
	
	public static final String TABLE_NAME = "bus_time";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DEPARTURE_TIME = "departure_time";
	public static final String COLUMN_BUS = "bus";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_LOCAL = "local";
	
	public static final String CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME + "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_DEPARTURE_TIME + " LONG, "
			+ COLUMN_BUS + " TEXT, "
			+ COLUMN_TYPE + " TEXT, "
			+ COLUMN_LOCAL + " TEXT);";
	
	public static final String LOCAL_PTI = "pti";
	public static final String LOCAL_BARREIRA = "barreira";
	
	private Context context;
	private DatabaseHelper dbHelper;
	
	public BusTimeDao(Context context) {
		this.context = context;
		dbHelper = DatabaseHelper.getInstance(context);
	}
	
	public void insert(BusTime schedule) {
		ContentValues values = modelToContentValues(schedule);
		dbHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
	}
	
	public void insertAll(List<BusTime> list) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		db.beginTransaction();
		try {
			for (int i = 0; i < list.size(); i++) {
				insert(list.get(i));
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
		
		} 
		finally {
			db.endTransaction();
		}
	}
	
	public BusTime queryById(long id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		String where = COLUMN_ID + "=?";
		String[] whereArgs = new String[] {String.valueOf(id)};
		
		Cursor cursor = db.query(TABLE_NAME, null, where, whereArgs, null, null, null);
		
		BusTime schedule = null;
		if (cursor.moveToFirst()) {
			schedule = cursorToModel(cursor);
		}
		
		cursor.close();
		
		return schedule;
	}
	
	public List<BusTime> queryByLocal(String local) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		String where = COLUMN_LOCAL + "=?";
		String[] whereArgs = new String[] {local};
		
		Cursor cursor = db.query(TABLE_NAME, null, where, whereArgs, null, null, null);
		
		List<BusTime> list = new ArrayList<BusTime>();
		while (cursor.moveToNext()) {
			BusTime schedule = cursorToModel(cursor);
			list.add(schedule);
		}
		cursor.close();
		
		return list;
	}
	
	private String dateToType(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		String type = "normal";
		
		switch (c.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SATURDAY:
			type = "sabado";
			break;
		case Calendar.SUNDAY:
			type = "domingo";
			break;
		}
		
		return type;
	}
	
	public List<BusTime> queryNext(int n, Date currentDate, String local) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		long currentTime = 0;
		
		
		String type = dateToType(currentDate);
		
		try {
			currentTime = BusTime.dateFormat.parse(BusTime.dateFormat.format(currentDate)).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String where = COLUMN_LOCAL + "=? AND " + COLUMN_DEPARTURE_TIME + ">=? AND " + COLUMN_TYPE + "=?";
		String[] whereArgs = new String[] {local, String.valueOf(currentTime), type};
		
		Cursor cursor = db.query(TABLE_NAME, null, where, whereArgs, null, null, COLUMN_DEPARTURE_TIME);
		
		int i = 0;
		List<BusTime> list = new ArrayList<BusTime>();
		while (cursor.moveToNext() && i < n) {
			BusTime time = cursorToModel(cursor);
			list.add(time);
		}
		
		cursor.close();
		
		return list;
	}
	
	public List<BusTime> queryAll() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		
		List<BusTime> list = new ArrayList<BusTime>();
		while (cursor.moveToNext()) {
			BusTime schedule = cursorToModel(cursor);
			list.add(schedule);
		}
		cursor.close();
		
		return list;
	}
	
	public void updateDatabase(List<BusTime> data, Metadata metadata) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		db.beginTransaction();
		try {
			
			MetadataDao metadataDao = new MetadataDao(context);
			
			metadataDao.deleteAll();
			metadataDao.insert(metadata);
			
			deleteAll();
			insertAll(data);
			
			db.setTransactionSuccessful();
		} catch (Exception e) {
			//Log.e("TIME", "TRANSACTION_EXCEPTION");
		} 
		finally {
			db.endTransaction();
		}
	}
	
	public void deleteAll() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
	}
	
	public long queryNumEntries() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		return DatabaseUtils.queryNumEntries(db, TABLE_NAME);
	}

	public ContentValues modelToContentValues(BusTime schedule) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_DEPARTURE_TIME, schedule.getDepartureTime().getTime());
		values.put(COLUMN_BUS, schedule.getBus());
		values.put(COLUMN_TYPE, schedule.getType());
		values.put(COLUMN_LOCAL, schedule.getLocal());
		
		return values;
	}
	
	public BusTime cursorToModel(Cursor cursor) {
		BusTime schedule = new BusTime();
		schedule.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
		schedule.setDepartureTime(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_DEPARTURE_TIME))));
		schedule.setBus(cursor.getString(cursor.getColumnIndex(COLUMN_BUS)));
		schedule.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
		schedule.setLocal(cursor.getString(cursor.getColumnIndex(COLUMN_LOCAL)));
		
		return schedule;
	}

}