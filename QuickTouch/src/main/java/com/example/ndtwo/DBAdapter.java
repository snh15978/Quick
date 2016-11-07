package com.example.ndtwo;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper
{
	private static final String DB_NAME = "mydb.db";
    private static final int VERSION = 1;
    private static final String ID = "_id";
    private static final String APPLICATIONNAME = "name";
    private static final String APPNAME = "appname";
    private static final String TIME = "time";

    
    private static final String TABLE_NAME = "application";
    private static final String CREATE_TABLE =
        "CREATE TABLE " + TABLE_NAME + " (" +
        ID + " integer primary key autoincrement, " +
        APPLICATIONNAME + " text not null, " +
        APPNAME + " text not null, " +
        TIME + " INTEGER not null )";

    public SQLiteDatabase db;
    
    int tempint;
	
    String ttime;
    String testmsg;
	
	

	public DBAdapter(Context context) {
		super(context, DB_NAME, null, VERSION);
		db = this.getWritableDatabase();
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

	}
	
	//insert
	public boolean insertInfo(String applicationname, String name , int time) {
        ContentValues cv = new ContentValues();
        cv.put(APPLICATIONNAME, applicationname);
        cv.put(APPNAME, name);
        cv.put(TIME, time);
        return db.insert(TABLE_NAME, null, cv) != -1;
    }
	
	public boolean selectsame(String sname)
	{
		//String[] columns={"ID", "inventory"};
		
		
		String[] parms = new String[] { sname };
		boolean result;
		Cursor c=db.query(TABLE_NAME, new String[] {ID, APPLICATIONNAME, APPNAME, TIME}, APPLICATIONNAME + "=?", parms, null, null, null);
		if (c.moveToFirst())
		{
			result=true;
		}
		else
		{
			result=false;
		}

		return result;
	}
	
	public String selectsame2(String sname)
	{
		String[] parms = new String[] { sname };
		String result;
		Cursor c=db.query(TABLE_NAME, new String[] {ID, APPLICATIONNAME, APPNAME, TIME}, APPLICATIONNAME + "=?", parms, null, null, null);
		if (!c.moveToFirst())
		{
			result=sname;
		}
		return sname;
		
	}
	
	
	//drop
	public boolean deleteAll() {
        int result=db.delete(TABLE_NAME, null, null);
        return result > 0;
     }
	
	// update
    public boolean updateInfo(Info i) {
    	
        ContentValues cv = new ContentValues();

        tempint=Integer.valueOf(i.getTime())+1;

      
        cv.put(APPLICATIONNAME, i.getApplicationname());
        cv.put(APPNAME, i.getName());
        cv.put(TIME, tempint);
        
        String[] params = new String[] { Integer.toString(i.getId()) };
        
        int result= db.update(TABLE_NAME, cv, ID + "=?", params);
      
        return result >0;
        
    }
    
public boolean timeclear(Info i) {
    	
        ContentValues cv = new ContentValues();

        tempint=0;

    
        cv.put(APPLICATIONNAME, i.getApplicationname());
        cv.put(APPNAME, i.getName());
        cv.put(TIME, tempint);
        
        String[] params = new String[] { Integer.toString(i.getId()) };
        
        int result= db.update(TABLE_NAME, cv, ID + "=?", params);
     
        return result >0;
        
    }

    public ArrayList<Info> Sorting(){
    	ArrayList<Info> sinfo = new ArrayList<Info>();
    	Cursor c = db.query(TABLE_NAME, new String[] {ID, APPLICATIONNAME, APPNAME, TIME}, null, null, null, null, TIME + " DESC");
    	
        if (c.moveToFirst()) {
            final int indexId = c.getColumnIndex(ID);
            final int indexApplicationname = c.getColumnIndex(APPLICATIONNAME);
            final int indexAppname = c.getColumnIndex(APPNAME);
            final int indexTime = c.getColumnIndex(TIME);

            do {
                int id = c.getInt(indexId);
                String applicationname = c.getString(indexApplicationname);
                int time = c.getInt(indexTime);
                String name =  c.getString(indexAppname);
                sinfo.add(new Info(id, applicationname, name ,time));
            } while (c.moveToNext());
        }
        c.close();

        return sinfo; 	
    }
	
	//read
	public ArrayList<Info> getAllInfo() {
        ArrayList<Info> info = new ArrayList<Info>();
        Cursor c = db.query(TABLE_NAME, new String[] {ID, APPLICATIONNAME, APPNAME, TIME}, null, null, null, null, TIME + " ASC");

        if (c.moveToFirst()) {
            final int indexId = c.getColumnIndex(ID);
            final int indexApplicationname = c.getColumnIndex(APPLICATIONNAME);
            final int indexAppname = c.getColumnIndex(APPNAME);
            final int indexTime = c.getColumnIndex(TIME);

            do {
                int id = c.getInt(indexId);
                String applicationname = c.getString(indexApplicationname);
                int time = c.getInt(indexTime);
                String name =  c.getString(indexAppname);
                info.add(new Info(id, applicationname, name ,time));
            } while (c.moveToNext());
        }
        c.close();

        return info;
    }
	
	 // delete
    public boolean deleteInfo(int id) {
        String[] params = new String[] { Integer.toString(id) };
        int result = db.delete(TABLE_NAME, ID + "=?", params);
        return result > 0;
    }


	
}
