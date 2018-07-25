package com.sstudio.darkchannel.main;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "chatting.db";
	public static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_USER_MESSAGE ="user_message";
	public static final String COLUMN_ID = "id";
	public static final String USERNAME = "username";
	public static final String MESSAGE_TYPE = "message_type";
	public static final String MESSAGE = "message";
	public static final String TIMESTAMP = "timestamp";
	public static final String STATUS = "status";
	
	public static final int STATUS_SENT = 1;
	public static final int STATUS_PENDING = 2;
	public static final int STATUS_READ = 3;
	public static final int STATUS_UNREAD = 4;
	
	protected static final String CREATE_TABLE = String.format("CREATE TABLE %s" +
	     "(%s INTEGER PRIMARY KEY," +
		 " %s TEXT, %s TEXT, %s TEXT," +
		 " %s TEXT, %s INTEGER);",
		 TABLE_USER_MESSAGE, COLUMN_ID,
		 USERNAME, MESSAGE_TYPE, MESSAGE,
		 STATUS, TIMESTAMP);
	
	protected static volatile boolean isChanged = false;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO: Implement this method
	}
	
	public long insert(String tableName, ContentValues values) {
		final SQLiteDatabase db = getWritableDatabase();
		return db.insert(tableName, null, values);
	}
	
	public boolean insertMessage(String username, String msgType, String msg, int status, int timeStamp) {
		final ContentValues values = new ContentValues();
		values.put(USERNAME, username);
		values.put(MESSAGE_TYPE, msgType);
		values.put(MESSAGE, msg);
		values.put(STATUS, status);
		values.put(TIMESTAMP, timeStamp);
		boolean stat = insert(TABLE_USER_MESSAGE, values) > -1;
		if (stat == true) {
			isChanged = true;
		}
		return stat;
	}
	
	public Cursor getCursor(String tableName, String[] resultList, String where, String[] whereArgs) {
		final SQLiteDatabase db = getReadableDatabase();
		String groupBy = null;
		String having = null;
		String order = null;
		Cursor result = db.query(tableName, resultList, where, whereArgs, groupBy, having, order);
		return result;
	}
	
	public Cursor getAllMessage() {
		return getCursor(TABLE_USER_MESSAGE, new String[] {COLUMN_ID, USERNAME, MESSAGE_TYPE, MESSAGE, STATUS, TIMESTAMP}, null, null);
	}
	
	public Cursor getUnreadMessage() {
		return getCursor(TABLE_USER_MESSAGE, new String[] {COLUMN_ID, USERNAME, MESSAGE_TYPE, MESSAGE, STATUS, TIMESTAMP}, null, new String[] {STATUS + " = ?" + Integer.toString(STATUS_UNREAD)});
	}
	
	public boolean readMessage(int id) {
		final SQLiteDatabase db = getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(STATUS, STATUS_READ);
		int stat = db.update(TABLE_USER_MESSAGE, value, COLUMN_ID + " = ?", new String[] {Integer.toString(id)});
		return stat > -1;
	}
	
	public static boolean isChanged() {
		return isChanged;
	}
	
}

