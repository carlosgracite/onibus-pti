package org.itai.onibuspti.dao;

import org.itai.onibuspti.model.Metadata;
import org.itai.onibuspti.util.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MetadataDao {
	
	public static final String TABLE_NAME = "metadata";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_VERSION = "version";
	
	public static final String CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME + "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_VERSION + " LONG NOT NULL);";
	
	private DatabaseHelper dbHelper;
	
	public MetadataDao(Context context) {
		dbHelper = DatabaseHelper.getInstance(context);
	}
	
	public Metadata query() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
			
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_VERSION);
		
		Metadata metadata = null;
		if (cursor.moveToLast()) {
			metadata = cursorToModel(cursor);
		}
		
		cursor.close();
		
		return metadata;
	}
	
	public void insert(Metadata metadata) {
		ContentValues values = modelToContentValues(metadata);
		dbHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
	}
	
	public void update(Metadata metadata) {
		ContentValues values = modelToContentValues(metadata);
		String whereClause = COLUMN_ID + "=?";
		String[] whereArgs = new String[] {String.valueOf(metadata.getId())};
		
		dbHelper.getWritableDatabase().update(TABLE_NAME, values, whereClause, whereArgs);
	}
	
	public void deleteAll() {
		dbHelper.getWritableDatabase().delete(TABLE_NAME, null, null);
	}

	private ContentValues modelToContentValues(Metadata metadata) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_VERSION, metadata.getVersion());
		return values;
	}

	private Metadata cursorToModel(Cursor cursor) {
		Metadata metadata = new Metadata();
		metadata.setVersion(cursor.getLong(cursor.getColumnIndex(COLUMN_VERSION)));
		return metadata;
	}

	

}
