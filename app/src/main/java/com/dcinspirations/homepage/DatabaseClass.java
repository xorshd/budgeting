package com.dcinspirations.homepage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseClass extends SQLiteOpenHelper {

    public DatabaseClass(Context context) {
        super(context, "Budget", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table expenses  (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, category TEXT, amount INTEGER, time TEXT, date TEXT  )");
        db.execSQL("create table income  (id INTEGER PRIMARY KEY AUTOINCREMENT,date TEXT,amount TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  expenses");
        db.execSQL("DROP TABLE IF EXISTS  income");
        onCreate(db);
    }
    public boolean insertIntoMeds(String name, String cat, int am, String time, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("category", cat);
        cv.put("amount", am);
        cv.put("time", time);
        cv.put("date", date);
        long result = db.insert("expenses",null,cv);
        if(result==-1){
            return true;
        }else{
            return false;
        }
   }
    public Cursor getToday(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from expenses where date=?", new String[]{date} );
    }
    public Cursor getAmount(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select date,amount from expenses", null );
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from expenses", null );
    }
    public int deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("expenses","id=?",new String[]{String.valueOf(id)});
    }


    public boolean insertIntoIncome(String date, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("date", date);
        cv.put("amount", amount);
        long result = db.insert("income",null,cv);
        if(result==-1){
            return true;
        }else{
            return false;
        }
    }
    public boolean updateIncome(String date, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("amount", amount);
        long result = db.update("income",cv,"date=?",new String[]{String.valueOf(date)});
        if(result==-1){
            return true;
        }else{
            return false;
        }
    }
    public Cursor getIncomeData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from income", null );
    }

    public int resolveAmount(String month){
        int count = 0;
        Cursor cs = getAmount();
        while (cs.moveToNext()){
            String date[] = cs.getString(cs.getColumnIndex("date")).split("/");
            String m = date[1]+"/"+date[2];
            if(m.equalsIgnoreCase(month)) {
                count = count + cs.getInt(cs.getColumnIndex("amount"));
            }
        }
        return count;
    }

    public double[] resolveParticularAmount(String month){
        double count = 0,count1 =0,count2=0,count3=0,count4=0,count5=0,count6=0,count7=0;
        Cursor cs = getData();
        while (cs.moveToNext()){
            String date[] = cs.getString(cs.getColumnIndex("date")).split("/");
            String m = date[1]+"/"+date[2];
            if(m.equalsIgnoreCase(month)) {
                String cat = cs.getString(cs.getColumnIndex("category"));
                count =  cs.getInt(cs.getColumnIndex("amount"));
                count7 = count7 +  cs.getInt(cs.getColumnIndex("amount"));
                switch(cat){
                    case "Food":
                        count1 = count1+count;
                        break;
                    case "Transport":
                        count2 = count2+count;
                        break;
                    case "Shopping":
                        count3 = count3+count;
                        break;
                    case "Health":
                        count4 = count4+count;
                        break;
                    case "School":
                        count5 = count5+count;
                        break;
                    case "Miscellaneous":
                        count6 = count6+count;
                        break;

                }

            }
        }
        double[] result = {count7,count1,count2,count3,count4,count5,count6};
        return result;
    }
    public int resolveIncomeAmount(String month){
        int count = 0;
        Cursor cs = getIncomeData();
        while (cs.moveToNext()){
            String date[] = cs.getString(cs.getColumnIndex("date")).split("/");
            String m = date[0]+"/"+date[1];
            if(m.equalsIgnoreCase(month)) {
                count = count + cs.getInt(cs.getColumnIndex("amount"));
            }
        }
        return count;
    }
    public int deleteIncomeData(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("income","date=?",new String[]{String.valueOf(date)});
    }


}
