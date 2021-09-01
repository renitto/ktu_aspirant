package com.example.renitto.ktu_master.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.renitto.ktu_master.Models.CheckList;
import com.example.renitto.ktu_master.Models.Important_University_Contacts;
import com.example.renitto.ktu_master.Models.University_mails;


import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Renitto on 3/3/2016.
 */
public class DbManager  {

    //checklist
    public static String Table_Checklist = "Checklist_Table";

    public static String _id = "_id";
    public static String COLUMN_header = "header";
    public static String COLUMN_desc = "description";
    public static String COLUMN_date = "date";
    public static String COLUMN_check_status = "status";



    public void insertToCheckList(Context context, CheckList checkList)
    {
        ArrayList<CheckList> checkLists=new ArrayList<>();
        AssetesDBHelper dbhelper = new AssetesDBHelper(context);
        SQLiteDatabase db = dbhelper.openDataBase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COLUMN_header, checkList.getHeader());
        contentValues.put(COLUMN_desc, checkList.getDescription());
        contentValues.put(COLUMN_date, checkList.getDate());
        contentValues.put(COLUMN_check_status, checkList.isCheck_status());
        db.insert(Table_Checklist, null, contentValues);
        Cursor c = db.query(Table_Checklist,null,null,null,null,null,null);
        if (c.getCount() > 0)
        {
            c.moveToFirst();
            if(c.getInt(3)==1)
                checkLists.add(new CheckList(c.getString(1),c.getString(2),true,c.getString(4)));
            else
                checkLists.add(new CheckList(c.getString(1),c.getString(2),false,c.getString(4)));

        }
    }

    public void updateCheckList(Context context, CheckList checkList)
    {

        AssetesDBHelper dbhelper = new AssetesDBHelper(context);
        SQLiteDatabase db = dbhelper.openDataBase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COLUMN_check_status, checkList.isCheck_status());
        db.update(Table_Checklist,contentValues, COLUMN_header +"='"+checkList.getHeader()+"'",null);


    }

    public void removeCheckList(Context context, CheckList checkList)
    {
        AssetesDBHelper dbhelper = new AssetesDBHelper(context);
        SQLiteDatabase db = dbhelper.openDataBase();
        db.delete(Table_Checklist, COLUMN_header + "='" + checkList.getHeader() + "'", null);
        db.close();

    }

    public void removeAllCheckLists(Context context)
    {
        AssetesDBHelper dbhelper = new AssetesDBHelper(context);
        SQLiteDatabase db = dbhelper.openDataBase();
        db.delete(Table_Checklist, null, null);
        db.close();

    }

    public ArrayList<CheckList> getMyCheckLists(Context context)
    {
        ArrayList<CheckList> checkLists=new ArrayList<>();
        AssetesDBHelper dbhelper = new AssetesDBHelper(context);
        SQLiteDatabase db = dbhelper.openDataBase();
        Cursor c = db.query(Table_Checklist,null,null,null,null,null,null);
        if (c.getCount() > 0)
        {
            c.moveToFirst();
            for (int i=0;i<c.getCount();i++) {

                if (c.getInt(3) == 1)
                    checkLists.add(new CheckList(c.getString(1), c.getString(2), true , c.getString(4)));
                else
                    checkLists.add(new CheckList(c.getString(1), c.getString(2), false , c.getString(4)));

                c.moveToNext();

            }

        }
        c.close();
        db.close();
        return checkLists;
    }

    public static String Table_University_Contacts = "important_contacts_university";

    public ArrayList<Important_University_Contacts> getImportantContacts(Context context)
    {
        ArrayList<Important_University_Contacts> important_university_contactses=new ArrayList<>();
        AssetesDBHelper dbhelper = new AssetesDBHelper(context);
        SQLiteDatabase db = dbhelper.openDataBase();
        Cursor c = db.query(Table_University_Contacts,null,null,null,null,null,null);
        c.moveToFirst();
        for (int i=0;i<c.getCount();i++)
        {
            important_university_contactses.add(new Important_University_Contacts(c.getString(1),c.getString(2),c.getString(3)));
            c.moveToNext();
        }
        c.close();
        db.close();
        return important_university_contactses;
    }

    public static String Table_University_Emails = "university_mails";

    public ArrayList<University_mails> getUniversitymails(Context context)
    {
        ArrayList<University_mails> university_mailses=new ArrayList<>();
        AssetesDBHelper dbhelper = new AssetesDBHelper(context);
        SQLiteDatabase db = dbhelper.openDataBase();
        Cursor c = db.query(Table_University_Emails,null,null,null,null,null,null);
        c.moveToFirst();
        for (int i=0;i<c.getCount();i++)
        {
            university_mailses.add(new University_mails(c.getString(1),c.getString(2)));
            c.moveToNext();
        }
        c.close();
        db.close();
        return university_mailses;
    }







}
