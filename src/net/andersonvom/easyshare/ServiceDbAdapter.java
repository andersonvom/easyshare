package net.andersonvom.easyshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ServiceDbAdapter
{
	
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL = "email";
	
	private static final String TAG = "ServiceDbAdapter";
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "easyshare";
	private static final String DATABASE_TABLE = "services";
	private static final String DATABASE_CREATE = 
		"CREATE TABLE " + DATABASE_TABLE + " ("
			+ KEY_ID + " integer primary key autoincrement, "
			+ KEY_NAME + " text not null, "
			+ KEY_EMAIL + " text not null "
			+ ");"
		;
	private Context context;
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(DATABASE_CREATE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}
	
    /**
     * Takes the context to allow the database to be opened/created
     * 
     * @param ctx the Context within which to work
     */
	public ServiceDbAdapter(Context context)
	{
		this.context = context;
	}
	
    /**
     * Open the database. If it cannot be opened, try to create a new instance
     * of the database. If it cannot be created, throw an exception to signal
     * the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
	public ServiceDbAdapter open() throws SQLException
	{
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		dbHelper.close();
	}
	
	public long create(Service service)
	{
		ContentValues row = new ContentValues();
		row.put(KEY_NAME,  service.getName());
		row.put(KEY_EMAIL, service.getEmail());
		
		return db.insert(DATABASE_TABLE, null, row);
	}

	public boolean delete(long id)
	{
		return
				(db.delete(DATABASE_TABLE, KEY_ID + "=" + id, null) > 0);
	}
	
	public boolean delete(Service service)
	{
		return delete(service.getId());
	}
	
    /**
     * Return a Cursor over the list of all rows in the database, ordered by name
     * 
     * @return Cursor over all rows
     */
	public Cursor fetch()
	{
		return
			db.query(
					DATABASE_TABLE,
					new String[] {KEY_ID, KEY_NAME, KEY_EMAIL},
					null, null, null, null,
					KEY_NAME);
	}
	
    /**
     * Return a Cursor positioned at the matched row
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
	public Cursor fetch(long rowId) throws SQLException
	{
		Cursor result = db.query(
							true,
							DATABASE_TABLE,
							new String[] {KEY_ID, KEY_NAME, KEY_EMAIL},
							KEY_ID + "=" + rowId,
							null, null, null, null, null);
		
		if (result != null) {
			result.moveToFirst();
		}
		
		return result;
	}
	
	public boolean update(Service service)
	{
		ContentValues row = new ContentValues();
		row.put(KEY_NAME, service.getName());
		row.put(KEY_EMAIL, service.getEmail());
		
		return (db.update(DATABASE_TABLE, row, KEY_ID + "=" + service.getId(), null) > 0);
	}
	
}
