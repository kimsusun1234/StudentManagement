package com.example.mob1032_assignment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob1032_assignment.datamodel.StudentData;
import com.example.mob1032_assignment.ultilities.ManagementDbHelper;

import java.util.ArrayList;
import java.util.List;

//Đây là class dùng để chứa các câu lệnh SQLite
public class StudentDataDAO
{
    private SQLiteDatabase db;


    public static final String TABLE_NAME_STUDENTS = "students";
    public static final String COLUMN_NAME_STUDENT_ID = "student_id";
    public static final String COLUMN_NAME_STUDENT_NAME = "student_name";
    public static final String COLUMN_NAME_STUDENT_DATEOFBIRTH = "student_date_of_birth";
    public static final String COLUMN_NAME_STUDENT_CLASS = "student_class";

    public static final String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_NAME_STUDENTS
                                                        + "(" + COLUMN_NAME_STUDENT_ID + " VARCHAR PRIMARY KEY,"
                                                        + COLUMN_NAME_STUDENT_NAME + " VARCHAR,"
                                                        + COLUMN_NAME_STUDENT_DATEOFBIRTH + " VARCHAR, "
                                                        + COLUMN_NAME_STUDENT_CLASS + " VARCHAR,"
                                                        + "CONSTRAINT fk_student_class "
                                                        + "FOREIGN KEY (" + COLUMN_NAME_STUDENT_CLASS + ")"
                                                        + "REFERENCES " + ClassDataDAO.TABLE_NAME_CLASSES + "(" + ClassDataDAO.COLUMN_NAME_CLASS_ID +")"
                                                        + ");";



    public StudentDataDAO(Context context)
    {
        //Kết nối với db bằng dbHelper
        ManagementDbHelper dbHelper = new ManagementDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(String id, String name, String dateOfBirth, String classID)
    {
        //dùng ContentValues để tạo một khuôn chứa dữ liệu cho bảng
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_STUDENT_ID, id);
        values.put(COLUMN_NAME_STUDENT_NAME, name);
        values.put(COLUMN_NAME_STUDENT_DATEOFBIRTH, dateOfBirth);
        values.put(COLUMN_NAME_STUDENT_CLASS, classID);

        //hàm insert của SQLDatabase trả về số thứ tự của cột thêm vào kiểu long
        return db.insert(TABLE_NAME_STUDENTS, null, values);
    }


    //query có điều kiện, lọc theo thuộc tính(cột) nào đó
    public List<StudentData> select(String whereColumn, String whereValue)
    {
        List<StudentData> studentList = new ArrayList<StudentData>();
        //tạo mảng projection để chứa thông tin thứ tự các cột của bảng
        String[] projection = {COLUMN_NAME_STUDENT_ID,
                COLUMN_NAME_STUDENT_NAME,
                COLUMN_NAME_STUDENT_DATEOFBIRTH,
                COLUMN_NAME_STUDENT_CLASS};

        //tạo mệnh đề where
        //patern: "WHERE column = ?"
        String whereStatement = whereColumn + " = ?";

        //giá trị muốn truyền vào "?"
        String[] whereArgs = {whereValue};

        //câu lệnh query trả về kiểu Cursor
        Cursor cursor = db.query(TABLE_NAME_STUDENTS, projection, whereStatement, whereArgs, null, null, null);

        //tạo một đối tượng StudentData để set dữ liệu lấy ra được từ mỗi vòng lặp của cursor.moveToNext
        StudentData studentData;
        if (cursor.moveToNext())
        {
            studentData = new StudentData(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_STUDENT_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_STUDENT_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_STUDENT_DATEOFBIRTH)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_STUDENT_CLASS)));
            studentList.add(studentData);
        }

        return studentList;
    }

    public List<StudentData> selectAll()
    {
        List<StudentData> studentList = new ArrayList<StudentData>();
        //tạo mảng projection để chứa thông tin thứ tự các cột của bảng
        String[] projection = {COLUMN_NAME_STUDENT_ID, COLUMN_NAME_STUDENT_NAME, COLUMN_NAME_STUDENT_DATEOFBIRTH,COLUMN_NAME_STUDENT_CLASS};


        Cursor cursor = db.query(TABLE_NAME_STUDENTS, projection, null, null,null,null, null);

        while (cursor.moveToNext())
        {
            studentList.add(new StudentData(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_STUDENT_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_STUDENT_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_STUDENT_DATEOFBIRTH)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_STUDENT_CLASS))));
        }

        return studentList;
    }

    public int update(String oldID, String newName, String newDateOfBirth, String newClass)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_STUDENT_NAME, newName);
        values.put(COLUMN_NAME_STUDENT_DATEOFBIRTH, newDateOfBirth);
        values.put(COLUMN_NAME_STUDENT_CLASS, newClass);

        String whereClause = COLUMN_NAME_STUDENT_ID + " LIKE ?";
        String[] whereArgs = {oldID};

        return db.update(TABLE_NAME_STUDENTS, values, whereClause, whereArgs);
    }

    public int delete(String id)
    {
        String whereClause = COLUMN_NAME_STUDENT_ID + " LIKE ?";
        String[] whereArgs = {id};

        return db.delete(TABLE_NAME_STUDENTS, whereClause, whereArgs);
    }

}
