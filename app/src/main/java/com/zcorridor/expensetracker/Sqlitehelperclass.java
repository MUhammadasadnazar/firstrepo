package com.zcorridor.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Sqlitehelperclass extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Incomedb.db";
    public static final String TABLE_NAME = "Income";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DESCRIPTION";
    public static final String COL_3 = "AMOUNT";
    public Sqlitehelperclass(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,DESCRIPTION TEXT,AMOUNT INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public Boolean insertIncome(String amount,int spentamount){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
         contentValues.put(COL_2,spentamount);
        contentValues.put(COL_3,amount);
        long res = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if (res==-1){
            return false;
        }
        else return true;
    }

    public ArrayList<Incomemodalclass> getAllIncome(){
        ArrayList<Incomemodalclass> data=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME,null);
        StringBuffer stringBuffer = new StringBuffer();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                data.add(new Incomemodalclass(cursor.getString(cursor.getColumnIndex(COL_3)),cursor.getInt(cursor.getColumnIndex(COL_2))));

            }
        }
        return data;
    }
}
