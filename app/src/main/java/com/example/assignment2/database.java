package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {
    private Context context;
    private static final String DBNAME = "locations.db";
    private static final int DBVERSION =1;

    //FROM THE ASSIGNMENT DATABASE SCHEMA
    private static final String TABLE_NAME = "Locations";
    private static final String ID = "_id";
    private static final String ADDRESS = "address";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lon";

    database(@Nullable Context context)
    {
        super(context, DBNAME, null, DBVERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ADDRESS + " TEXT, " +
                        LATITUDE + " REAL, " +
                        LONGITUDE + " REAL); ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void add(String address, double lat, double lon){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(ADDRESS,address);
        cv.put(LATITUDE,lat);
        cv.put(LONGITUDE, lon);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1)
        {
            Toast.makeText(context,"Add failed.",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context,"Location Added.",Toast.LENGTH_SHORT).show();
        }
    }

    long getCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return count;

    }

    void delete(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?", new String[]{row_id});

        if(result == -1)
        {
            Toast.makeText(context,"Deletion Failed.",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context,"Deletion Successful.",Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }

    Cursor queryData(String address) {
        String query = "SELECT * FROM Locations where address like '%"+address+"%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor readData() {
        String query = "SELECT * FROM Locations";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String row_id, String address, Double lat, Double lon){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ADDRESS,address);
        cv.put(LATITUDE,lat);
        cv.put(LONGITUDE, lon);

        long result = db.update(TABLE_NAME,cv,"_id=?", new String[]{row_id});
        if(result == -1)
        {
            Toast.makeText(context,"Update Failed.",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context,"Update Added.",Toast.LENGTH_SHORT).show();
        }
    }
}
