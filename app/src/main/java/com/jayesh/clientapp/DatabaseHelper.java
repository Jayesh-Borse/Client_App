package com.jayesh.clientapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.ClientInfoStatus;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ClientInfo.db";
    public static final String TABLE_NAME = "ClientInfo_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "FATHER_NAME";
    public static final String COL_5 = "DOB";
    public static final String COL_6 = "MOTHER_NAME";
    public static final String COL_7 = "CONTACT_NO";
    public static final String COL_8 = "EMAIL";
    public static final String COL_9 = "ADDRESS";



    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 2);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,FATHER_NAME TEXT,DOB TEXT,MOTHER_NAME TEXT,CONTACT_NO TEXT,EMAIL TEXT,ADDRESS TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String name,String surname,String father,String dob,String mother,String contact,String email,String address){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,father);
        contentValues.put(COL_5,dob);
        contentValues.put(COL_6,mother);
        contentValues.put(COL_7,contact);
        contentValues.put(COL_8,email);
        contentValues.put(COL_9,address);



        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {
            return false;
        }
        return true;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;

    }

    public boolean updateData(String id,String name,String surname,String father,String dob,String mother,String contact,String email,String address){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,father);
        contentValues.put(COL_5,dob);
        contentValues.put(COL_6,mother);
        contentValues.put(COL_7,contact);
        contentValues.put(COL_8,email);
        contentValues.put(COL_9,address);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[]{ id });
        return true;
    }

    public String searchData(String Name){

        SQLiteDatabase db =this.getWritableDatabase();
        String[] columns={DatabaseHelper.COL_1,DatabaseHelper.COL_2,DatabaseHelper.COL_3,DatabaseHelper.COL_4,DatabaseHelper.COL_5,DatabaseHelper.COL_6,DatabaseHelper.COL_7,DatabaseHelper.COL_8,DatabaseHelper.COL_9};
        Cursor res = db.query(DatabaseHelper.TABLE_NAME,columns, DatabaseHelper.COL_2+"= '"+Name+"'",null,null,null,null,null);
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){

                        int index0 = res.getColumnIndex(COL_1);
                        int index1 = res.getColumnIndex(COL_2);
                        int index2 = res.getColumnIndex(COL_3);
                        int index3 = res.getColumnIndex(COL_4);
                        int index4 = res.getColumnIndex(COL_5);
                        int index5 = res.getColumnIndex(COL_6);
                        int index6 = res.getColumnIndex(COL_7);
                        int index7 = res.getColumnIndex(COL_8);
                        int index8 = res.getColumnIndex(COL_9);

                        String id = res.getString(index0);
                        String fullName = res.getString(index1);
                        String surName = res.getString(index2);
                        String father = res.getString(index3);
                        String dob = res.getString(index4);
                        String mother = res.getString(index5);
                        String contact = res.getString(index6);
                        String email = res.getString(index7);
                        String address = res.getString(index8);

                        buffer.append("\nID : "+id+"\nName : "+fullName +"\n" +"Middle Name : "+father+"\n"+"Last Name : "+surName+"\n"+"DOB : "+dob+"\n"+"Mother's Name : "+mother+"\n"+"Contact No. : "+contact+"\n"+"Email : "+email+"\n"+"Address : "+address+"\n\n");
                    }
                if(buffer!=null){
                    return buffer.toString();
                }
                else
                    return "null";


            }

            public int deleteData(String id){
                SQLiteDatabase db = this.getWritableDatabase();
                return db.delete(TABLE_NAME,"ID=?",new String[] {id});
            }


    }



