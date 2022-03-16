package com.zcorridor.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Sqlitetarget extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="targetdb.db";
    public static final String TABLE_NAME = "target";
    public static final String COL_1 = "ID";
    public static final String COL_2= "Des";
    public static final String COL_3= "Amount";
    public static final String COL_4="Picpath";
    public static final String COL_5="Spentamount";
    public static final String COL_6="Days";
     public static final String TABLE_NAMEE = "trans";
    public static final String COLL_1="TRANID";
    public static final String COLL_2="Transamnt";
    public static final String COLL_3="Transdate";
    public static final String COLL_4="Transtype";
    public static final String COLL_5="Goalname";


    public Sqlitetarget(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, Des TEXT," +
                " Amount INTEGER , Picpath TEXT ,Spentamount INTEGER ,Days INTEGER)");
        db.execSQL(" CREATE TABLE "+TABLE_NAMEE+"(TRANID INTEGER PRIMARY KEY AUTOINCREMENT, Transamnt INTEGER," +
                " Transdate TEXT , Transtype TEXT ,Goalname TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAMEE);
      }
    public Boolean insertTargetData(String des,int amnt,String picpath,int spntamnt,long days){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,des);
        contentValues.put(COL_3,amnt);
        contentValues.put(COL_4,picpath);
        contentValues.put(COL_5,spntamnt);
        contentValues.put(COL_6,days);
         long res = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        if (res==-1){
            return false;
        }
        else return true;
    }
    public Boolean insertTransactionData(int amntt, String transtype, String goalname, long date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLL_2,amntt);
        contentValues.put(COLL_3,date);
        contentValues.put(COLL_4,transtype);
        contentValues.put(COLL_5,goalname);
          long res = db.insert(TABLE_NAMEE,null,contentValues);
        db.close();
        if (res==-1){
            return false;
        }
        else return true;
    }
    public ArrayList<Targetmodalclass> getTargetData(){
        ArrayList<Targetmodalclass> data=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME,null);
        StringBuffer stringBuffer = new StringBuffer();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                data.add(new Targetmodalclass(cursor.getString(cursor.getColumnIndex(COL_4))
                        ,cursor.getString(cursor.getColumnIndex(COL_2)),
                        cursor.getInt(cursor.getColumnIndex(COL_3)),
                        cursor.getInt(cursor.getColumnIndex(COL_5)),
                        cursor.getInt(cursor.getColumnIndex(COL_6))));
            }
        }
        return data;
    }
    public ArrayList<TransactionModalClass> getTrnasactionData(){
        ArrayList<TransactionModalClass> data=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAMEE,null);
        StringBuffer stringBuffer = new StringBuffer();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                data.add(new TransactionModalClass(cursor.getInt(cursor.getColumnIndex(COLL_2))
                        ,cursor.getString(cursor.getColumnIndex(COLL_4)),
                        cursor.getString(cursor.getColumnIndex(COLL_5)),
                         cursor.getString(cursor.getColumnIndex(COLL_3))));
            }
        }
         return data;
    }
//    public ArrayList<Targetmodalclass> TargetdData(String des){
//        ArrayList<Targetmodalclass> data = new ArrayList<>();
//        SQLiteDatabase db = getWritableDatabase();
//        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME+" Where "+COL_2+"='"+des+"'",null);
//        StringBuffer stringBuffer = new StringBuffer();
////        int x= cursor.getInt(cursor.getColumnIndex(COL_5));
//        if (cursor != null && cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                data.add(new Targetmodalclass(
//                        cursor.getString(cursor.getColumnIndex(COL_4)),
//                        cursor.getString(cursor.getColumnIndex(COL_2)),
//                        cursor.getInt(cursor.getColumnIndex(COL_3)),
//                        cursor.getInt(cursor.getColumnIndex(COL_5))));
//            }
//        }
//        return data;
//    }

    public long updateData(String dess, int spntamntt) {
        SQLiteDatabase db = getWritableDatabase();
         ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5,spntamntt);
        long res = db.update(TABLE_NAME,contentValues,COL_2+"='"+dess+"'",null);
        db.close();
       return  res;
     } public long updateDays(String des, long days) {
        SQLiteDatabase db = getWritableDatabase();
         ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6,days);
        long res = db.update(TABLE_NAME,contentValues,COL_2+"='"+des+"'",null);
        db.close();
       return  res;
     }

}
