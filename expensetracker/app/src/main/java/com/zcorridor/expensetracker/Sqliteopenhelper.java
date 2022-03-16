package com.zcorridor.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Sqliteopenhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Expenstracker.db";
    public static final String TABLE_NAME = "Categries";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "CATEGORY_NAME";
    public static final String COL_3 = "AMOUNT_SPENT";
     public static final String COL_4 = "AMOUNT";
    public Sqliteopenhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORY_NAME TEXT,AMOUNT_SPENT INTEGER,AMOUNT INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public Boolean insertDAta(String catgry,String amount,String spentamount){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,catgry);
        contentValues.put(COL_3,spentamount);
        contentValues.put(COL_4,amount);
        long res = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if (res==-1){
            return false;
        }
        else return true;
    }

    public ArrayList<Categoriesmodalclass> getAllData(){
        ArrayList<Categoriesmodalclass> data=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME,null);
        StringBuffer stringBuffer = new StringBuffer();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                 data.add(new Categoriesmodalclass(cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }
        }
        return data;
    }
    public void getspent(String cname,int spamount) {
        int x = 0;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(" UPDATE " + TABLE_NAME + " SET " + COL_3 + "= " + spamount + " Where " + COL_2 + " = '" + cname + "'");
        db.close();
    }


    public void delData(int id){
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+COL_1+"="+id);
        db.close();

    } public void editData(int id){
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL("UPDATE FROM " + TABLE_NAME+ " WHERE "+COL_1+"="+id);
        db.close();
    }
}
