package com.example.mob1032_assignment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob1032_assignment.datamodel.ClassData;
import com.example.mob1032_assignment.ultilities.ManagementDbHelper;

import java.util.ArrayList;
import java.util.List;

public class ClassDataDAO
{
    private SQLiteDatabase db;

    public static final String TABLE_NAME_CLASSES = "classes";
    public static final String COLUMN_NAME_CLASS_ID = "class_id";
    public static final String COLUMN_NAME_CLASS_NAME = "class_name";

    public static final String CREATE_CLASSES_TABLE = "CREATE TABLE " + TABLE_NAME_CLASSES
            + "(" + COLUMN_NAME_CLASS_ID + " VARCHAR PRIMARY KEY,"
            + COLUMN_NAME_CLASS_NAME + " VARCHAR);";

    private static final String[] PROJECTION = {COLUMN_NAME_CLASS_ID, COLUMN_NAME_CLASS_NAME};

    public ClassDataDAO(Context context)
    {
        ManagementDbHelper dbHelper = new ManagementDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(String id, String className)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_CLASS_ID, id);
        values.put(COLUMN_NAME_CLASS_NAME, className);

        return db.insert(TABLE_NAME_CLASSES, null, values);
    }

    public List<ClassData> select(String whereColumn, String whereValue)
    {
        List<ClassData> classList = new ArrayList<>();

        String whereClause = whereColumn + " = ?";
        String[] whereArgs =  {whereValue};


        Cursor cursor = db.query(TABLE_NAME_CLASSES, PROJECTION, whereClause, whereArgs, null, null, null);
        ClassData classData;

        if (cursor.moveToNext())
        {
            classData = new ClassData(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CLASS_ID)),
                                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CLASS_NAME)));

            classList.add(classData);
        }
        return classList;
    }

    public List<ClassData> selectAll()
    {
        List<ClassData> classList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_CLASSES,PROJECTION, null,null, null, null, null);

        while (cursor.moveToNext())
        {
            classList.add(new ClassData(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CLASS_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CLASS_NAME))));
        }
        return classList;
    }

    public int update(String oldID, String newName)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_CLASS_NAME, newName);

        String whereClause = COLUMN_NAME_CLASS_ID + " LIKE ?";
        String[] whereArgs = {oldID};

        return db.update(TABLE_NAME_CLASSES, values, whereClause, whereArgs);
    }

    public int delete(String id)
    {
        String whereClause = COLUMN_NAME_CLASS_ID + " LIKE ?";
        String[] whereArgs = {id};

        return db.delete(TABLE_NAME_CLASSES, whereClause, whereArgs);
    }
}
