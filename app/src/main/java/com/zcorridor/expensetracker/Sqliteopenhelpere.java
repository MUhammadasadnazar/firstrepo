package com.zcorridor.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Sqliteopenhelpere extends Sqliteopenhelper {
    public static final String DATABASE_NAME = "Expensmanage.db";
    public static final String TABLE_NAME = "Expenses";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Category_name";
    public static final String COL_3 = "Description";
    public static final String COL_4 = "Amount";
    public static final String COL_5 = "date";
    public Sqliteopenhelpere(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, Description TEXT, Amount INTEGER" +
                ",Category_name TEXT,date TEXT )");
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    public Boolean insertData(String catgry, String des, String amount,String date) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
         contentValues.put(COL_2,catgry);
        contentValues.put(COL_3,des);
        contentValues.put(COL_4,amount);
        contentValues.put(COL_5,date);
        long res = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if (res==-1){
            return false;
        }
        else return true;
     }

    public ArrayList<Expensemodalclass> getalldata(String catname) {
                ArrayList<Expensemodalclass> data=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME+" Where "+COL_2+"='"+catname+"'",null);

        StringBuffer stringBuffer = new StringBuffer();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                  data.add(new Expensemodalclass(
                         cursor.getString(cursor.getColumnIndex(COL_2)),
                         cursor.getString(cursor.getColumnIndex(COL_3)),
                         cursor.getInt(cursor.getColumnIndex(COL_4)),
                         cursor.getString(cursor.getColumnIndex(COL_5))));
            }
        }
        return data;
     }
// public ArrayList<exaddmodalclass> getalldata() {
//                ArrayList<exaddmodalclass> data=new ArrayList<>();
//        SQLiteDatabase db = getWritableDatabase();
//        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME,null);
//        StringBuffer stringBuffer = new StringBuffer();
//        if (cursor != null && cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                data.add(new exaddmodalclass(cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getString(5)));
//            }
//        }
//        return data;
//     }

 }
