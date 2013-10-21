package org.itai.onibuspti.util;

import org.itai.onibuspti.dao.BusTimeDao;
import org.itai.onibuspti.dao.MetadataDao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static DatabaseHelper instance = null;
	
	private static final String DATABASE_NAME = "bus_db";
	private static final int DATABASE_VERSION = 2;

	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// Colocar como synchronised se houver várias threads acessando o BD
	public static DatabaseHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseHelper(context.getApplicationContext());
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MetadataDao.CREATE_TABLE);
		db.execSQL(BusTimeDao.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion == 1 && newVersion == 2) {
			db.execSQL("DROP TABLE IF EXISTS " + MetadataDao.TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + BusTimeDao.TABLE_NAME);
			
			onCreate(db);
		}
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

}